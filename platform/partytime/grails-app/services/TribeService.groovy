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
 * Service class to manage Tribe.
 */
class TribeService {

    boolean transactional = true

    def messageService

    /**
     * Create new Tribe.
     *
     * @param creator
     * @param name
     * @param desc
     * @param inviteOnly
     * @param structData Map of name-value for tribe attribute data
     * @return newly created tribe object
     */
    def createNewTribe(User creator, String name, String desc, boolean invite, Map structData) {
        def inviteOnly = invite?: false
        def newTribe = new Tribe(creator:creator, name:name, description:desc, inviteOnly:inviteOnly)
        newTribe.save()
        updateTribeAttribute(newTribe, structData)
        return newTribe
    }

    /**
     * Update given Tribe's attributes.
     *
     * @param tribe
     * @param structData
     */
    def updateTribeAttribute(Tribe tribe, Map structData) {

        structData.each {n,v ->
            tribe.setAttribute(n, v)
        }
    }

    /**
     * Delete the given Tribe.
     * TODO: Inform members of the Tribe close down.
     *
     */
    def deleteTribe(Tribe tribe, String reason="NA") {
        tribe.delete()
        //TODO: Send msg to tribe members
    }

    /**
     * Add member to tribe.
     *
     * @param tribe
     * @param member
     */
    def addMemberToTribe(Tribe tribe, User member) {
        return ( tribe.addToMembers( member )?.save() ? true : false )
    }


    def removeMemberFromTribe(Tribe tribe, User member) {
        return ( tribe.removeFromMembers( member )?.save() ? true : false )
    }

    /**
     * Get member list for the given tribe.
     *
     * @param tribe
     * @param member
     */
    def membersForTribe(Tribe tribe) {
        return tribe.members
    }


    /**
     * Get all the tribes for the given user,
     *
     * @param user
     * @return list of tribes which this user is part of
     */
    def tribesForMember(User user, params=[max:-1, offset:-1]) {
        assert user, "User must not be null"
        
        def offset 
        if (params.offset) {
            offset = params.offset instanceof String ? Integer.parseInt( params.offset ) : params.offset
        } else {
            offset = -1
        }
        
        def max
        if (params.max) {
            max = params.max instanceof String ? Integer.parseInt( params.max ) : params.max 
        } else {
            max = -1
        }

        def c = Tribe.createCriteria()
        def list = c {

            or {
                creator {
                    eq('id', user.id)
                }

                members {
                    eq('id', user.id)
                }

                moderators {
                    eq('id', user.id)
                }

                administrators {
                    eq('id', user.id)
                }
            }

            if (max > 0) maxResults(max)
            if (offset > 0) firstResult(offset)
        

        }

        return list?.unique()
    }

    /**
     * Add a new Tribe message.
     *
     * @param tribe
     * @param author
     * @param msg
     * @param subject
     */
    def addMessage(Tribe tribe, User author, String msg, String subject) {
        def tribeMessage = new TribeMessage(tribe:tribe, author:author, body:msg, subject:subject)
        return ( tribeMessage.save() ? true : false )
    }

    /**
     * Add a new Tribe reply message.
     *
     * @param tribe
     * @param msg
     * @param subject
     */
    def addMessage(TribeMessage original, User author, String msg, String subject) {
        assert original, "Original msg must not be null"

        def tribe = original?.tribe
        def tribeMessage = new TribeMessage(tribe:tribe, author:author, body:msg, subject:subject)
        tribeMessage.save()
        original.addToReplies( tribeMessage )
        String body = """
        There is a new message for you in ${tribe.name}'s board:

        "${msg}"
        
        """
        sendMessageToTribeMember( tribe, "New ${tribe.name} message", body)
    }

    /**
     * Get all the Tribe Messages that belongs to the given user.
     * @param user
     * @return list of messages
     */
    def getMessage(User user) {
        def tribes = tribesForMember( user )
        def msg = []
        tribes.each {
            msg += TribeMessage.findAllByTribe(it)
        }
        return msg
    }

    /**
     * Get latest message for the given Tribe.
     * Returns a list of message sort by most recent update.
     *
     * @param tribe
     * @param max the max number of message to retrieve.
     */
    def getLatestMessage(Tribe tribe, int max=10) {
        def messages = 
            TribeMessage.findAllByTribeAndOriginalMessageIsNull( 
                            tribe, [max:max, sort:'lastUpdated', order:'desc'] )
        return messages
    }

    /**
     * Send message to all member for the given Tribe.
     *
     * @param tribe
     * @param subject
     * @param msg
     */
    def sendMessageToTribeMember(Tribe tribe, String subject, String msg) {
        def creator = tribe.creator
        def members = tribe.members
        members?.each { member ->
            messageService.sendMessage(creator, member, subject, msg)
        }
    }

    /**
     * Delete Tribe message.
     * This will also remove any replies under this message.
     *
     * @param msg
     */
    def deleteMessage(TribeMessage msg) {
        msg?.replies?.each {
            it.delete()
        }
        msg.delete()
    }
}


