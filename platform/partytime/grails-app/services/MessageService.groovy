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
 * Messaging service.
 * @author tcp@favoritemedium.com
 */
class MessageService implements IMessageService {

    boolean transactional = false

    /**
     * Get the latest Messages with the given user.
     * @param user
     */
    def List getLatestMessage(User u) {
        def user = u ?: User.get( session.user.id )
        assert user, "User must not be null"

        def msgs = Message.findAllByToAndStatus( u, Message.UNREAD )
        return msgs
    }


    /**
     * Get all Messages with the given user.
     * @param user
     */
    def List getAllMessages(User u, Map params=[max:0, offset:0]) {
        def user = u ?: User.get( session.user.id )
        assert user, "User must not be null"
        int max = params.max
        int offset = params.offset

        def msgs = Message.findAllByTo( u, [max:max, offset:offset, sort:'lastUpdated', order:'desc'] )
        return msgs
    }
    
    def int getAllMessagesCount(User u) {
        def user = u ?: User.get( session.user.id )
        assert user, "User must not be null"
        def msgs = Message.findAllByTo( u )
        return msgs.size()
    }

    /**
     * Send Message out.
     *
     * @param from
     * @param to
     * @param subject
     * @param msgBody
     */
    def boolean sendMessage(User from, User to, String subject, String msgBody) {
        boolean success = true
        def msg = new Message(from:from, to:to, subject:subject, body:msgBody )
        if (!msg.save()) {
            success = false
            msg.errors.allErrors.each {
                log.error( it )
            }
        }
        return success
    }
    
    /**
     * Send Message out.
     *
     * @param user
     * @param subject
     * @param msgBody
     */
    def boolean sendMessage(User to, String subject, String msgBody) {
        def user = User.get( session.user.id )
        return sendMessage(user, to, subject, msgBody)
    }
    
}


