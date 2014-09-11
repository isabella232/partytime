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


class MessageController {

    def messageService
    def tribeService

    def index = { redirect(action:'inbox') }


    def inbox = {
        def user = User.get( session.user.id )

        def msgs = messageService.getAllMessages( user, params )
        def msgCount = messageService.getAllMessagesCount( user )
 //       def tribeMsgs = tribeService.getMessage( user )

        [messages:msgs, msgCount:msgCount]
    }

    def write = {
        println params

        def message = params.id ? Message.get( params.id ) : new Message()
        [message:message]
    }

    def save = {
        println params

        def user = User.get( session.user.id )
        def to = params.to
        def subject = params.subject
        def msg = params.messageBody
        messageService.sendMessage(user, to, subject, msg ) 
        
        flash.message = "Message sent"
        redirect(action:'inbox', id:user.id)
    }

    def delete = {
        println params
        def user = User.get( session.user.id )
        def msg = Message.get( params.id )
        msg?.delete()

        flash.message = "Message deleted"
        redirect(action:'inbox', id:user.id)
    }

    def read = {
        println params

        def msg = Message.get( params.id )
        if (Message.UNREAD == msg?.status) {
            msg.status = Message.READ
            msg.save()
        }

        [message:msg]

    }

}


