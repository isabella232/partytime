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


/**
 * Collection of useful taglib for Profile
 */
class ProfileTagLib {
    
    static namespace = "profile"
    
    def connectionService


    /**
     * Display relevant information/profile of the user.
     * 
     * Example: <profile:info [userId="<USER_ID>"] [short="true|false"] />
     */
    def info = { attrs, body ->
        def title = attrs.title
        def user = User.get( attrs.userId ?: session.user.id )
        def profile = user.profile
        def shortInfo = (attrs.short == "false") ? false : true


        out << "<div id='profile'>${ title ?: user.toString() + "'s Profile" }<br/>"

        if (profile.getAttribute("photo_file_name") ) {
            out << "<div id='profile_photo'><img src='${request.contextPath}/profile/viewPhoto?path=${user.username}&name=profile.jpg' /></div>"
        } else {
            out << "<div id='profile_photo'><img src='${request.contextPath}/images/profile.jpg' /></div>"
        }

        if (session.user && session.user?.id == user?.id) {
            out << "<div id='profile_edit'>"
            out << link(controller:'profile', action:'edit', id:user.id, Class:"profile_link") { "Edit your profile" }
            out << link(controller:'profile', action:'photo', id:user.id, Class:"profile_link") { "Edit profile photo" }
            out << link(controller:'profile', action:'password', id:user.id, Class:"profile_link") { "Change password" }
            out << "</div>"
        } else if ( session.user && connectionService.areFriend( session.user, user) ) {
             //   out << link(controller:'message', action:'send', params:[to:user], Class:"profile_link") { "Send Message" }
            out << "<div id='profile_edit'>"
            out << "You two are already friends"
            out << "</div>"
        } else if (session.user) {
                out << link(controller:'friend', action:'makeFriend', params:[id:user.id], Class:"profile_link") { "Add as Friend" }
        } else {
            out << link(controller:'login', class:"profile_link") { "Please log in for more options" }
        }

        out << getProfileDetails(user, shortInfo)
        out << "</div>"
    }

    /**
     * Display the profile photo of the user.
     *
     * Example" <profile:photo [userId="<USER_ID>"] />
     */
    def photo = { attrs, body ->
        def user = User.get( attrs.userId ?: session.user.id )
        String username = user.username
        String imgLink
        
        if (user?.profile?.getAttribute("photo_file_name") ) {
            String imgPath = grailsApplication.config.images.location.toString() + "${username}/profile.jpg" 
            
            if ( !(new File(imgPath).exists()) ) {
                imgLink = "<div id='profile_photo'><img src='${request.contextPath}/images/profile.jpg' /></div>"
            } else {
                imgLink = "<div id='profile_photo'><img src='${request.contextPath}/profile/viewPhoto?path=${user.username}&name=profile.jpg' title='${user.toString()}'/></div>"
            }

        } else {
            imgLink = "<div id='profile_photo'><img src='${request.contextPath}/images/profile.jpg' /></div>"
        }
        out << imgLink
    }

    /**
     * Display the thumbnail photo of the user.
     *
     * Example: <profile:photo [userId="<USER_ID>"] />
     */
    def thumbnail = { attrs, body ->
        def user = User.get( attrs.userId ?: session.user.id )
        String username = user.username
        def plain = attrs.plain
        String imgLink = ""

        if (user?.profile?.getAttribute("photo_file_name") ) {
            String imgPath = grailsApplication.config.images.location.toString() + "${username}/thumb.jpg" 
            
            if ( !(new File(imgPath).exists()) ) {
                if (!plain) imgLink += "<div id='profile_photo'>"
                imgLink += "<img src='${request.contextPath}/images/thumb.jpg' />"
                if (!plain) imgLink += "</div>"
            } else {
                if (!plain) imgLink += "<div id='profile_photo'>"
                imgLink += "<img src='${request.contextPath}/profile/viewPhoto?path=${user.username}&name=thumb.jpg' title='${user.toString()}'/>"
                if (!plain) imgLink += "</div>"
            }
        } else {
            imgLink = "<div id='profile_photo'><img src='${request.contextPath}/images/thumb.jpg' /></div>"
        }
        out << imgLink
    }

    /**
     * Shortcut to get the attribute value with the given name.
     * Example: <profile:attribute name="ATTR_NAME" [type="date|number"] /> 
     */
    def attribute = { attrs, body ->
        def user = User.get( attrs.userId ?: session.user.id )
        String name = attrs.name

        println  " ===> ${attrs} "
        println  " ===> ${attrs.type == 'date'} "

        if ( attrs?.type == "date" ) {
            Date date = user.profile.getAttributeAsDate(name)
            out <<  new java.text.SimpleDateFormat("dd MMMM yyyy").format(date)
        } else {
            String output = user.profile.getAttribute(name)
            out << output
        }
    }

    /*
     * Get the profile details of the given user.
     * @param u User
     */
    private def getProfileDetails(u, shortInfo=false) {
        def user = u ?: User.get( session.user.id ) 
        def sb = new StringBuilder()
        
        String location = user.profile.getAttribute('location')
        String about = user.profile.getAttribute('about')

        sb << "<div><ul>"
        sb << "<li><span class='value'>${user.toString()}</span></li>"
        if (shortInfo) {
            
            if (about) sb << "<li><span class='value'>${about}</span></li>"

        } else {

            if (location) sb <<  "<li><span class='field'>Current Location:</span><span class='value'>${location}</span></li>"
            if (about) sb << "<li><span class='field'>About me:</span><span class='value'>${about}</span></li>"

        }
        sb << "</ul></div>"
        sb.toString()
    }

    /**
     * Invokes body if the profile belong the current login user.
     * 
     * Example: <profile:ifOwnProfile userId="<USER_ID>" />
     */
    def ifOwnProfile = { attrs, body ->
        def user = session.user
        def profileUser = User.get( attrs.userId )
        
        if ( user?.id == profileUser?.id ) {
            out << body()
        }
    }

}


