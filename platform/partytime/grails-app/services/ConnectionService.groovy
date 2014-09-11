/**
 * Party Time Project (http://code.google.com/p/party-time)
 *
 * Copyright 2009 Favorite Medium LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.favoritemedium.partytime.domain.*
import com.favoritemedium.partytime.service.*


/**
 * Connection is about the relationship between two person.
 * Once there is a connection between two person then they are friends.
 */
class ConnectionService {

    boolean transactional = true
    def eventLogService
    def messageService

        
    /**
     * Check if the two users are friends.
     * If both user are the same, it will return true.
     * @param user1
     * @param user2
     * @return boolean true if the two users are fiends, false otherwise
     */
    def isConnected(User user1, User user2) {
        assert user1, "user1 must not be null"
        assert user2, "user2 must not be null"

        if (user1 == user2 ) return true

        boolean gotConnection = false
        def connectionFrom = Connection.findByFromFriendAndToFriend(user1, user2)
        def connectionTo = Connection.findByFromFriendAndToFriend(user2, user1)
        //XXX: Check for status to confirm friendship connection status
        if ( (connectionFrom && connectionFrom.status == Connection.ACCEPTED_STATUS)
            || (connectionTo && connectionTo.status == Connection.ACCEPTED_STATUS )) {
                gotConnection = true
            }
        return gotConnection
    }

    /**
     * Check if the two users are friends.
     * @param user1
     * @param user2
     * @return boolean true if the two users are fiends, false otherwise
     */
    def areFriend(user1, user2) {
        assert user1, "user1 must not be null"
        assert user2, "user2 must not be null"
        return isConnected(user1, user2) 
    }
    
    def areFriends(user1, user2) {
        return areFriend(user1, user2)
    }

    /**
     * Get the list of friends for the given user.
     *
     * @param user
     * @return the specified user's friends
     */
    def getFriendsFor(user) {
        /*def friends = Connection.findAllByFromFriendAndStatus(user, Connection.ACCEPTED_STATUS)?.collect { it.toFriend }
        friends = friends.plus( Connection.findAllByToFriendAndStatus(user, Connection.ACCEPTED_STATUS)?.collect { it.fromFriend } )
    */

    //def l = Connection.findAll("from Connection as c where c.toFriend=:user or c.fromFriend=:user", [user:u])
    
        def friends = Connection.findAllByFromFriend(user)?.collect { it.toFriend }
        friends = friends.plus( Connection.findAllByToFriend(user)?.collect { it.fromFriend } )
        return friends.unique()
    }

    /**
     * Get the list of friends for the given user.
     *
     * @param user
     * @return the specified user's friends
     */
    def getAllFriendsFor(user, params = [max:-1, offset:-1]) {
        // PAGINATION TODO: This won't be accurate unless we implement our own pagination that sort by these 2 lists.
        def friends = Connection.findAllByFromFriendOrToFriend(user, user, params)?.collect { it.toFriend == user ? it.fromFriend : it.toFriend }

        return friends.unique()
    }

    def friendsCount(user) {
        def friends = Connection.findAllByFromFriendOrToFriend(user, user)?.collect { it.toFriend == user ? it.fromFriend : it.toFriend }
        return friends.unique()?.size()
    }

    /**
     * Get the list of friends for the given user.
     *
     * @param user
     * @return the specified user's friends
     * @deprecated 
     */
    def getConnectionFor(user) {
        assert user, "user must not be null"
        def friendship = Connection.findAllByFromFriend(user)
        return friendship
    }

    /**
     * Get friends request for the given user.
     *
     * @param user
     * @return Friends request for the given user.
     */
    def getFriendRequests(user) {
        assert user, "user must not be null"
        def pendingFriends = Connection.findAllByToFriend(user)//.collect { it.fromFriend }
        def friends = []
        
        pendingFriends.each {
            if ( it.status !=  Connection.ACCEPTED_STATUS ) {

                if ( it.fromFriend != user ) 
                    friends << it.fromFriend
            }
        }

        return friends
    }

    /**
     * Get the Connection object for the given 2 users.
     * @param user
     * @param otherUser
     * @return Connection object for the given users
     */
    def getConnection(user, otherUser) {
        assert user, "user1 must not be null"
        assert otherUser, "user2 must not be null"

        def friendship = Connection.findByFromFriendAndToFriend(user, otherUser)
        return friendship
    }

    /**
     * Decline friendship request.
     */
    def declineFriendship(User currentUser, User otherUser) {
        def conn = getConnection(otherUser, currentUser) ?: getConnection(currentUser, otherUser)
        boolean success = true
        try {
            conn?.delete()
        } catch (e) {
            success = false
            log.error(e)
        }
        return success
    }

    /**
     * Get the friend status for the given 2 users.
     * @return String representation of the friendship level.
     */
    def friendStatus(User user1, User user2) {
        if (user1 == user2) return ""

        def conn = getConnection(user1, user2)
        if (!conn) {
            conn = getConnection(user2, user1)
        }

        String statMessage = "NA"
        
        if (!conn) {
            statMessage = "No connection yet"
        } else {
            statMessage = conn.status == Connection.ACCEPTED_STATUS ? "" : "Pending"
        }
        return statMessage
    }


    /**
     * Make friends.
     *
     * @param me
     * @param pretty other user
     */
    def makeFriends(User me, User pretty) {
        assert me, "me must not be null"
        assert pretty, "pretty must not be null"

        if (me == pretty) return 

        def conn = new Connection(fromFriend:me, toFriend:pretty)
        if (!conn.save()) {
            conn.errors.allErrors.each {
                log.error(it)
            }

        }
        return conn
    }


    /**
     * Confirm the friendship requested by other user.
     *
     * @param me
     * @param otherUser
     */
    def confirmFriendship(User me, User otherUser) {
        assert me, "me must not be null"
        assert otherUser, "otherUser must not be null"
        boolean success = true

        def conn = getConnection(otherUser, me)
        conn.status = Connection.ACCEPTED_STATUS
        if ( conn.save() ) {
            eventLogService.log(me, 
                    "${me} and ${otherUser} are now friends", 
                    "${me} and ${otherUser} are now friends")
        } else {
            success = false
        }

        return success
    }

}


