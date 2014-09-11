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


class TribeController {

    def eventLogService
    def tribeService


    def index = { 
        println params
        def tribe = Tribe.get( params.id )
        assert tribe, "tribe must not be null"

        def messages = 
            TribeMessage.findAllByTribeAndOriginalMessageIsNull( 
                        tribe, [max:5, sort:'lastUpdated', order:'desc'] )

        [tribe:tribe, messages:messages] 
    }

    def all = {
        println params
        params.max = params.max ?: 10
        def list = Tribe.list(params)

        [tribes:list]
    }
    
    def myTribes = {
        def user = User.get( session.user?.id )
        params.max = params.max ?: 10
        params.offset = params.offset ?: 0

        def list = tribeService.tribesForMember( user, params )

        [tribes:list]
    }

    def save = {
        println params
        String name = params.name
        String description = params.description
        String mantra = params.mantra
        def user = User.get( session.user?.id )
        def tribe = params.id ? Tribe.get( params.id ) : null

        Boolean inviteOnly = params.inviteOnly ? true : false
        
        if (!tribe) {
            tribe = new Tribe(name:name, creator:user, description:description, inviteOnly:inviteOnly) 
            tribe.save(flush:true)
            eventLogService.log("${user} started a new Tribe -- ${name}", 
                                "${user} started a new Tribe -- ${name}")
        } else {
            tribe.name = name
            tribe.creator = user
            tribe.description = description
            tribe.inviteOnly = inviteOnly
            eventLogService.log("${user} updated tribe details -- ${name}", 
                                "${user} updated tribe details -- ${name}")
        }
        flash.message = "Tribe information saved"
        
        tribe.setAttribute("mantra", mantra)
        if (! tribe.save() ) {
            tribe.errors.allErrors.each {
                log.error( it )
            }
        }

        render(view:'create', model:[tribe:tribe])
    }

    def applyMembership = {
        def user = User.get( session.user.id )
        assert user

        def tribe = Tribe.get( params.id )
        tribe.addToPendingList( user )
        flash.message = "Applied for \"${tribe.name}\", awaiting approval."
        redirect(action:'all')
    }

    def approveMembership = {
        println params

        def tribe = Tribe.get( params.id )
        assert tribe, "Tribe must not be null"

        def member = User.get( params.member )
        flash.message = "Approved ${member} as tribe member"

        tribe.removeFromPendingList( member )
        tribe.addToMembers( member )

        redirect(action:'index', params:[id:tribe.id])
    }

    def rejectMembership = {
        println params
        
        def tribe = Tribe.get( params.id )
        def member = User.get( params.member )
        tribe.removeFromPendingList( member )
        flash.message = "Rejected ${member} tribe membership request"
        render(view:'index', model:[tribe:tribe])
    }

    def create = {
        def user = User.get( session.user?.id )
        assert user

        def tribe = params.id ? Tribe.get( params.id ) : null
        if (!tribe) tribe = new Tribe()

        [member:user, tribe:tribe]
    }

    def search = {
        println params 

        String query = params.searchtribe
        def tribes = Tribe.search( query, params )//?.results
       // println Tribe.search( query, params )
        flash.message = " ${tribes.total} search result${tribes.total>0 ? 's' : ''} found"

        render(view:'all', model:[tribes:tribes?.results])
    }

    def join = {
        println params
        def user = User.get( session.user.id )
        def tribe = Tribe.get( params.id )

        if (tribe.inviteOnly) {
            tribe.addToPendingList( user )
            flash.message = "Applied for \"${tribe.name}\", awaiting approval."
        } else if (tribe.creator != user) {
            tribe.addToMembers( user )
            if ( !tribe.save() ) {
                tribe.errors.allErrors.each {
                    log.error(it)
                }
            }
            flash.message = "You are now part of ${tribe.name}"
            eventLogService.log(tribe, "${user} joined ${tribe.name} tribe", "${user} joined ${tribe.name} tribe")
        } else {
            flash.message = "Doing nothing in ${tribe.name}"
        }
        

        render(view:'index', model:[tribe:tribe])
    }

    def leaveTribe = {
        println params

        def user = User.get( session.user.id )
        def tribe = Tribe.get( params.id )

        //XXX:Move this to service
        tribe.removeFromMembers(user)
        
        eventLogService.log(tribe, "${user} left ${tribe.name} tribe", 
                        "${user} left ${tribe.name} tribe")

        flash.message = "You have left this tribe. :-("
        render(view:'index', model:[tribe:tribe])
    }

    def messages = {
        println params
        def msg = TribeMessage.get( params['msg.id'] )
        def tribe = params.id ? Tribe.get( params.id ) : msg.tribe

        [tribe:tribe, message:msg]
    }
    
    def newMessage = {
        println params
        def msg = TribeMessage.get( params['msg.id'] )
        def tribe = params.id ? Tribe.get( params.id ) : msg.tribe

        [tribe:tribe, message:msg]
    }

    def replyMessage = {
        println params

        def author = User.get( session.user.id )
        def msg = TribeMessage.get( params['msg.id'] )


        tribeService.addMessage( msg, author, params.reply, msg?.subject)

        redirect(action:"messages", params:['msg.id':msg?.id])
    }

    def allMessage = {
        def tribe = Tribe.get( params.id )

        def messages = TribeMessage.findAllByTribeAndOriginalMessageIsNull( tribe, [sort:'lastUpdated', order:'desc'])
        [tribe:tribe, messages:messages]
    }
    
    def saveMessage = {
        println params

        def author = User.get( session.user.id )
        def tribe = Tribe.get( params.id )

        tribeService.addMessage(tribe, author, params.body, params.subject)
        redirect(action:"index", params:[id:tribe.id])
    }

    def deleteMessage = {
        println params

        def msg = TribeMessage.get( params.id )
        def tribe = msg?.tribe

        def user = User.get( session.user.id )
        if (tribe?.creator == user) {
            //msg.delete()
            tribeService.deleteMessage( msg )
            flash.message = "Deleted message"
        } else {
            flash.message = "You are not allowed to delete this message"
        }
        redirect( controller:params.controller, action:'allMessage', id:tribe?.id )

    }
}


