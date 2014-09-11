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
import org.codehaus.groovy.grails.plugins.PluginManagerHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder


/**
 * Collection of useful taglibs for profile, connections, and friendship which
 * doesn't belong in other taglib.
 *
 * @author tcp@favoritemedium.com
 */
class SocialTagLib {

    static namespace = "social"


    def connectionService
    def eventLogService
    def tribeService
    def pluginManager = PluginManagerHolder.pluginManager
    def config = ConfigurationHolder.config


    def cssAndJavascripts = {

        out << "<!- -- start social css and javascript ->"
        out << """
            <script type='text/javascript' src='${createLinkTo( dir:"${pluginContextPath}/js", file:'jquery-1.3.1.min.js' )}' ></script>
            <script type='text/javascript' src='${createLinkTo( dir:"${pluginContextPath}/js", file:'DD_roundies_0.0.2a-min.js' )}' ></script>
        """
        
        if (session.theme) {
            out << """
            <script type="text/javascript">
                DD_roundies.addRule('#sitebar', '0px');
                DD_roundies.addRule('#profile', '8px');
                DD_roundies.addRule('#tribes', '8px');
                DD_roundies.addRule('#friends', '8px');
            </script>
            """
        } else {
            out << """
            <script type="text/javascript">
                DD_roundies.addRule('#sitebar', '0px 0px 10px 10px');
                DD_roundies.addRule('#profile', '8px');
                DD_roundies.addRule('#tribes', '8px');
                DD_roundies.addRule('#friends', '8px');
            </script>
            """
        }

        out << """
            <link rel="stylesheet" href='${createLinkTo(dir:"${pluginContextPath}/css", file:'960.css')}' />
            <link rel="stylesheet" href='${createLinkTo(dir:"${pluginContextPath}/css", file:'960.reset.css')}' />
            <link rel="stylesheet" href='${createLinkTo(dir:"${pluginContextPath}/css", file:'960.text.css')}' />
        """


        if (session.theme) {
            String theme = session.theme

            out << """
            <link rel="stylesheet" href='${createLinkTo(dir:"${pluginContextPath}/css", file:"${theme}.site.css")}' />
            <link rel="stylesheet" href='${createLinkTo(dir:"${pluginContextPath}/css", file:"${theme}.style.css")}' />
            """
        } else {
            out << """
            <link rel="stylesheet" href='${createLinkTo(dir:"${pluginContextPath}/css", file:"site.css")}' />
            <link rel="stylesheet" href='${createLinkTo(dir:"${pluginContextPath}/css", file:"style.css")}' />
            """
        }
        
        out << "<!- -- eof social css and javascript ->"

    }
    
    def menu = {
        def user = session.user ?: User.get( session.userId )
        def sb = new StringBuffer()

        sb << "<ul id='menu'>"
        sb << "<li>" + link(controller:"home", action:"start") { "Home" } + "</li>"
        sb << "<li>" + link(controller:"profile", action:"index") { user.toString() } + "</li>"
        sb << "<li>" + link(controller:"friend", action:"myFriends") { "Friends" } + "</li>"
        sb << "<li>" + link(controller:"tribe", action:"myTribes") { "Tribes" } + "</li>"

        def blogPlugin = pluginManager.allPlugins.find { it.name == "pebbleIntegration" }
        if (blogPlugin && config.pebble.enabled) {
            sb << "<li>" + link(controller:"blog", action:"index") { "Blog" } + "</li>"
        }
        
        sb << "<li>" + link(controller:"postItem", action:"list") { "Posted Items" } + "</li>"
        sb << "<li>" + link(controller:"message", action:"inbox") { "Messages" } + "</li>"
        //sb << "<li>" + link(controller:"login", action:"logoff") { "Sign off" } + "</li>"
        sb << "<ul>"

        out << sb.toString()
    }

    def shortcut = {
        def user = session.user ?: User.get( session.userId )
        def sb = new StringBuffer()

        sb << link(controller:"profile", action:"index") { user.toString() } + " | "
        sb << link(controller:"friend", action:"myFriends") { "Friends" } + " | "
        sb << link(controller:"tribe", action:"myTribes") { "Tribes" } + " | "

        def blogPlugin = pluginManager.allPlugins.find { it.name == "pebbleIntegration" }
        if (blogPlugin && config.pebble.enabled) {
            sb << link(controller:"blog", action:"index") { "Blog" } + " | "
        }
        
        sb << link(controller:"postItem", action:"list") { "Posted Items" } + " | "
        sb << link(controller:"message", action:"inbox") { "Messages" } + " | "
        sb << link(controller:"login", action:"logoff") { "Sign off" } 

        out << sb.toString()
    }

    /**
     * Invokes the tag body if the current login user is the connected with the 
     * given user id.
     *
     * Example" <social:isConnected user="<USER_ID>" >Run me</social:isConnected>
     */
    def isConnected = { attrs, body ->
        def currentUser = session.user ?: User.get( session.userId )
        def otherUser = User.get( attrs.userId )

        assert otherUser, "otherUser must not be null"
        assert currentUser, "currentUser must not be null"


        def connection = connectionService.getConnection(currentUser, otherUser)
        def otherConn = connectionService.getConnection(otherUser, currentUser)

        if (connectionService.areFriends(currentUser, otherUser) ) {
            out <<  g.message(code:"friend.status.areFriends")
            out << " "
            out << link(controller:'friend', action:'breakFriend', id:"${otherUser.id}") { g.message(code:"friend.prompt.break") }
        } else if (otherConn && otherConn.status == Connection.DECLINED_STATUS) {
            out <<  g.message(code:"friend.status.declined")
        } else if (otherConn && otherConn.status != Connection.ACCEPTED_STATUS) {

            out << link(controller:'friend', action:'confirmFriend', id:"${otherUser.id}") { g.message(code:"friend.status.confirmed") }
            out << " or "
            out << link(controller:'friend', action:'rejectFriend', id:"${otherUser.id}") { g.message(code:"friend.status.reject") }

        } else if (otherConn && otherConn.status == Connection.ACCEPTED_STATUS) {
            out <<  g.message(code:"friend.status.areFriends")
            out << " "
            out << link(controller:'friend', action:'breakFriend', id:"${otherUser.id}") { g.message(code:"friend.prompt.break") }
        } else if (otherConn && otherConn.status != Connection.ACCEPTED_STATUS) {
            out <<  g.message(code:"friend.status.confirm")
        } else if (connection && connection.status != Connection.ACCEPTED_STATUS) {
            out <<  g.message(code:"friend.status.pending")
        } else if (connection && connection.status == Connection.DECLINED_STATUS ) {
            out <<  g.message(code:"friend.status.declined")
        } else if (connection && connection.status == Connection.ACCEPTED_STATUS) {
            out <<  g.message(code:"friend.status.areFriends")
            out << " "
            out << link(controller:'friend', action:'breakFriend', id:"${otherUser.id}") { g.message(code:"friend.prompt.break") }
        } else {
            out << link(controller:'friend', action:'makeFriend', id:"${otherUser.id}") { g.message(code:"friend.status.addAsFriend") }
        }

    }

    /**
     * Invokes the body is the 2 given users are friends.
     * 
     * Example: <social:areFriends userId="<USER ID>"> Run me </social:areFriends>
     */
    def areFriends = { attrs, body -> 
        def currentUser = session.user
        def otherUser = User.get( attrs.userId )

        assert currentUser, "currentUser must not be null"
        assert otherUser, "otherUser must not be null"

        if (connectionService.areFriends(currentUser, otherUser)) 
            body()
    }

    /**
     * Display list of user's friends.
     *
     * Example: <social:friendsList userId="<USER ID>" />
     */
    def friendsList = { attrs, body ->
        def user = User.get( attrs.userId ?: session.user.id )
        def currentUser = User.get( session.user.id )

        def friends = connectionService.getAllFriendsFor(user, [max:12])
        int n = 0
        out << "<div id='friends'>${attrs.title ?: ""}"
            out << "<table>"
            friends?.each {

                if ( n%3 == 0)  out << "<tr>"
                String name = it.toString()
                String status = connectionService.friendStatus(currentUser, it)

               // out << "<div class=\"friend\" style=\"width:80px;font-size:9pt\">"
                out << "<td>"
                out << profile.thumbnail(userId:it.id, plain:true)
                out << "<br/>${ link(controller:'profile', id:it.id){ name } } ${status}"
                out << "</td>"
              //  out << "</div>"
                n++
                if ( n%3 == 0)  out << "</tr>"
            }

                println " --- > ${n} "
            out << "</table>"
            out << "<div class=\"separator\"> </div>"

            if (friends?.size() < 1 && ( user?.id == currentUser?.id ) ) {
                out << link(controller:'friend', action:'all' ) { g.message(code:"friend.prompt.addFriend") }
            } else if (currentUser) {
                out << link(controller:'friend', action:'myFriends' ) {"See all <br/>"}
                out << link(controller:'friend', action:'all' ) { g.message(code:"friend.prompt.addMoreFriends") }
            }
        out << "</div>"
    }

    /**
     * Display list of user's news.
     *
     * Example: <social:news userId="<USER ID>" />
     */
    def news = { attrs, body ->
        def user = User.get( attrs.userId ?: session.user.id )
        def news = eventLogService.getNewsFor( user )
        String title = attrs.title ?: "Latest Activities"


        def tribes = tribeService.tribesForMember( user )

        tribes.each {
            def l = TribeMessage.findAllByTribe(it, [sort:'id', order:'desc', max:20])
            l?.each { 
                news += new TribeEventLog(tribe:it.tribe, name:it.subject, type:'tribe_comment', body:it.body, dateCreated:it.dateCreated, id:it.id)
            }
        }
        
        out << "<div class='news_title'>${title}</div>"
        if (news.size()>0) {
            out << "<ul id='news'>"
            
            news?.sort { it.dateCreated }?.reverse().each {
                String where
                if (it instanceof TribeEventLog && it.tribe ) {
                    where = it.tribe.toString()
                }

                out << """
                    <li class=\"${it.type}\">
                    <div class="news_content">
                        ${it.body} - ${it.dateCreated?.prettyDate()} ${ where ? "in \"${where}\"" : "" }
                    </div>
                    </li>
                """
            }
            out << "</ul>"
        } else {
            out << "No news yet"
        }

    }
}


