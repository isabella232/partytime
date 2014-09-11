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


class TribeTagLib {

    static namespace = "tribe"

    def connectionServ
    def tribeService



    /**
     * Shortcut to display the login's user tribe link to "My Tribe" page.
     * 
     * Example: <tribe:shortcut />
     */
    def shortcut = { attrs, body ->
        def user = attrs.userId ? User.get( attrs.userId ) : User.get( session.user.id )
        out << link( controller:'tribe', action:'myTribes', id:user.id) { "Tribes" }
    }

    /**
     * Tribe control panel -- actually a div of links to edit, add event, 
     * leave tribe, or join tribe.
     * 
     * Example: <tribe:control [userId="<USER_ID>"] tribeId="<TRIBE_ID>" />
     *
     */
    def control = { attrs, body ->
        def user = attrs.userId ? User.get( attrs.userId ) : User.get( session.user.id )
        def tribe = Tribe.get( attrs.tribeId )
        assert tribe, "tribe must not be null"

        out << "<div id='tribe'>"
        if ( user == tribe.creator ) {
            out << link( controller:'tribe', action:'create', id:tribe.id) {  "Edit tribe details" }
            out << "<br/>"
           // out << link( controller:'tribe', action:'newEvent', id:tribe.id) {  "Create new event" }
            out << "<br/>"
        } else if (tribe.isPartOfTribe(user)) {
            //Because as a creator, you cannot leave the tribe behind.
            out << link( controller:'tribe', action:'leaveTribe', id:tribe.id) {  "Leave this tribe" }
        } else {
            out << link( controller:'tribe', action:'join', id:tribe.id) {  "Join this Tribe" }
        }
        out << "</div>"
    }
    
    /**
     * Display the tribe list for the given user.
     *
     * Example: <tribe:tribeList userId="<user.id>" [title="I'm part of these Tribes"] [max="10"] />
     */
    def tribeList = { attrs, body ->
        def user = User.get( attrs.userId )
        def max = attrs.max ?: 11
        def currentUser = session.user
        def title = attrs.title

        assert user, "user must not be null"
        assert currentUser, "currentUser must not be null"


        def list = tribeService.tribesForMember( user, [max:max] )

        out << "<div id='tribes'>${title ?: "My Tribes"}"
        if (list?.size() > 0) {
            out << "<ul>"
            list?.each {
                String name = it.name
                out << "<li>${ link(controller:'tribe', action:'index', id:it.id){ name } }</li>"
            }
            out << "</ul>"
        }

        out << link(controller:'tribe', action:'myTribes' ) { "See all" }

        if ( currentUser ) {
            if (list?.size() < 1 && ( user?.id == currentUser?.id ) ) {
                out << "<br/>"
                out << link(controller:'tribe', action:'all' ) { "Join some Tribe?" }
            } else {
                out << "<br/>"
                out << link(controller:'tribe', action:'all' ) { "Join other Tribes" }
            }
            out << "<br/>"
            out << link(controller:'tribe', action:'create' ) { "or Start a new Tribe" }
        }

        out << "</div>"
    }

    /**
     * Invokes the body of this tag if user is not member of the given tribe.
     *
     * Example: <tribe:ifNotMember userId="<USER_ID>" tribeId="<TRIBE_ID>" />
     */
    def ifNotMember = { attrs, body ->
        def user = attrs.userId ? User.get( attrs.userId ) : User.get( session.user.id )
        def tribe = Tribe.get( attrs.tribeId )

        if ( user == tribe?.creator ) {
            out << "You are the creator"
        } else if ( !tribe.isPartOfTribe( user ) && !tribe.inviteOnly ) {
            out << body()
        } else if ( tribe.inviteOnly ) {
            out << link(controller:'tribe', action:"applyMembership", id:tribe.id) {"Request membership"}
        } else if ( tribe.isPartOfTribe( user ) ) {
            out << "You are a member already"
        }
    }

    /**
     * Invokes the body of this tag if user is creator of the given tribe.
     *
     * Example: <tribe:ifCreator tribeId="<TRIBE_ID>" />
     */
    def ifCreator = { attrs, body ->
        //def user = attrs.userId ? User.get( attrs.userId ) : User.get( session.user.id )
        def user = User.get( session.user.id )
        def tribe = Tribe.get( attrs.tribeId )

        if (user == tribe.creator) {
            out << body()
        }
    }

    /**
     * Display list Tribe updates which the user is part of
     *
     * Example: <tribe:tribeSummaries [userId="<USER ID>"] />
     * TODO: This should be in TribeTagLib
     */
    def tribeSummaries = { attrs, body ->
        def user = User.get( attrs.userId ?: session?.user?.id )
        def max = attrs.max ?: 3
        assert user, "user must not be null"

        String title = attrs.title ?: "Latest Tribe Activities"

        def msgs = []
        def tribes = tribeService.tribesForMember( user )

        tribes.each {
            def l = TribeMessage.findAllByTribe(it, [sort:'id', order:'desc', max:max])
            msgs += l
        }
        if ( msgs.size() < 1 ) return

        out << "<div class='tribe_news_title'>${title}</div> "

        out << "<table>"
        msgs.each {
            String httpLink
            if (it.originalMessage) {
                httpLink = createLink( controller:'tribe', action:'messages', params:['msg.id':it.originalMessage?.id ] ) 
                out << "<tr><td>${ profile.thumbnail(userId:it.author?.id) }</td>"
                out << "<td><a href='${httpLink}'>Reply: ${it.subject} in ${it.tribe?.name} </a></td>"
                out << "</tr>"
            } else {
                httpLink = createLink( controller:'tribe', action:'messages', params:['msg.id':it.id ] ) 
                out << "<tr><td>${ profile.thumbnail(userId:it.author?.id) }</td>"
                out << "<td><a href='${httpLink}'>${it.subject}</a></td>"
                out << "</tr>"
            }
        }
        out << "</table>"

    }


    /**
     * Includes the tribe stylesheet
     * 
     * Example:
     * 
     * <tribe:css />
     * 
     * Actually imports '/web-app/plugins/party-x-x/css/tribe.css'
     */
    def css = {
        def href = createLinkTo(dir: "${pluginContextPath}/css", file: "tribe.css")
        out << "<link rel=\"stylesheet\" type=\"text/css\" href=\"${href}\" />"
    }
    
}


