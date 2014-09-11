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


class FriendController {

    def messageService
    def connectionService
    def eventLogService

    def index = {
        redirect(action:'all')
    }

    def all = {
        println params
        def user = User.get( session.user.id )

        def max = params.max ?: 10
        def offset = params.offset ?: 0

        def members = User.findAllByActive(true, params) // [max:max, offset:offset])
        def total = User.countByActive(true) ?: User.count()
        members?.remove( user )

        [ members:members, total:total ]
    }

    def search = {
        println params
        String query = params.searchfriend
        def result = User.search(query, params)
        def members = result.results
        def total = result.total

        // TODO: Search result page
        render(view:'all', model:[members:members, total:total])
    }

    def myFriends = {
        println params

        def user = User.get( session.user.id )

        def max = params.max ?: 10
        def offset = params.offset ?: 0

        def friends = connectionService.getAllFriendsFor(user, [max:max, offset:offset])
        def count = connectionService.friendsCount( user )

        [friends:friends, totalCount:count] ///, pendingFriends:pendingFriends]
    }

    def breakFriend = {
        println params
        def friend = User.get( params.id )
        def me = User.get( session.user.id )
        assert friend, "user must not be null"

        //XXX: Move to service
        def conn1 = connectionService.getConnection( me, friend )
        def conn2 = connectionService.getConnection( friend, me )
        if ( conn1 ) conn1.delete()
        if ( conn2 ) conn2.delete()

        flash.message = "You and ${friend} no longer friends"
        redirect(action:'myFriends')
    }

    def makeFriend = {
        def currentUser = session.user ?: User.get( session.userId )
        def otherUser = User.get( params.id )
        
        
        def conn = connectionService.makeFriends(currentUser, otherUser)

        if (conn) {
            String msgTemplate = """

            Hi ${otherUser},

            I'm ${currentUser} and I would like to be friends. :-)

            You should click here to agree. C'mon!

            http://${request.contextPath}/connection/approve/${conn.id}
            <br/> 
            (But if you're really unfrriendly, click here http://${request.contextPath}/connection/reject/${conn.id} )

            <br/> 
            ---
            <br/> 
            ${currentUser}

            """
            messageService.sendMessage(currentUser, otherUser, 
                "I want to be friends -- ${currentUser}", msgTemplate.toString())
            flash.message = "Awaiting other party to confirm you as friend."
        } else {
            flash.message = "Could not make that friendship connection -- Error occured."
        }

        redirect(controller:'friend', action:'myFriends');
    }

    def confirmFriend = {
        def currentUser = session.user ?: User.get( session.userId )
        def otherUser = User.get( params.id )

        def conn = connectionService.getConnection(otherUser, currentUser)
        conn.status = Connection.ACCEPTED_STATUS
        conn.save()
        eventLogService.log(currentUser, "${currentUser} and ${otherUser} are now friends", "${currentUser} and ${otherUser} are now friends")
        flash.message = "Confirmed ${otherUser} as friend"
        redirect(action:'myFriends')
    }

    def rejectFriend = {
        def currentUser = session.user ?: User.get( session.userId )
        def otherUser = User.get( params.id )

        connectionService.declineFriendship(currentUser, otherUser)

        flash.message = "Declined ${otherUser} as friend"
        redirect(action:'myFriends')
        
    }

}

