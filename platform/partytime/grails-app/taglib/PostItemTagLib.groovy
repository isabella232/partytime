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


class PostItemTagLib {

    static namespace = "postItem"


    def shortcut = { attrs, body ->
        def user = attrs.userId ? User.get( attrs.userId ) : User.get( session.user.id )
        assert user

        String title = attrs.title ?: "Posted Items"

        out << link(controller:'postItem', action:'list') { title }
    }

    /**
     * Resolve the type of the media and display it correctly.
     *
     * Example: <postItem:resolve id="<POSTITEM_ID>" />
     */
    def resolve = { attrs, body ->
        def item = PostItem.get( attrs.id )
        assert item
        def value = item.value
        def type = item.type

        if ( type == "link" && isImage(value) ) {
            if (item.signature) {
                out << "<a href=\"${value}\"><img src=\"${createLink(controller:'mediaViewer', action:'image', id:item.signature)}\" /></a>"
            } else {
                out << "<a href=\"${value}\"><img src='${value}' /></a>"
            }
        }
        else if ( type == "link" && isYoutube(value) ) {
            def matcher = (  value =~ /.*youtube.com\/watch\?v=(\w+).*/ )
            if (matcher.count) {
                String yid = matcher[0][1]
                String youtubeEmbedder = """
                <object width="425" height="344"><param name="movie" value="http://www.youtube.com/v/${yid}&hl=en&fs=1"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/${yid}&hl=en&fs=1" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="425" height="344"></embed></object>
                """
                out << youtubeEmbedder
                //TODO -- Check config to see if the user want it to be embedded or just show as a link
               // out << "<a href=\"${value}\" target=_blank><img src='http://img.youtube.com/vi/${yid}/3.jpg' /></a>"
            } else {
                out << value
            }
        } else {
            out << value
        }
    }

    /**
     * Display latest posted items.
     * XXX: Only support image and youtube video for now.
     *
     * Example:  <postItem:list userId="USER_ID" />
     */
    def list = { attrs, body ->

        def user = User.get( attrs.userId ?: session.user.id )
        assert user, "User must not be null"
        int maxItem = attrs.max ?: 3
        String title = attrs.title ?: "Latest Post Items"

        out << "<div id=\"postedItems\">"
        out << "<strong>${title}</strong>"
        
        def itemslist = user.profile.postedItems.sort { it.lastUpdated }?.reverse()

        int n = 0
        itemslist.each { item ->
            if (n > maxItem ) return
            n++

            String value = item.value
            String type = item.type

            if ( type == "link" && isImage(value) ) {
                out << "<div class='item' >"
                if (item.signature) {
                    out << "<a class='postitem_link' href=\"${value}\"><img class='postItem' src=\"${createLink(controller:'mediaViewer', action:'image', id:item.signature)}\" /></a>"
                    out << "<p class='desc'>${item.description ? item.description + '<br/>' : ""} Posted ${item.lastUpdated?.prettyDate()}</p>"
                } else {
                    out << "<a class='postitem_link' href=\"${value}\"><img class='postItem' src='${value}' /></a>"
                    out << "<p class='desc'>${item.description ? item.description + '<br/>' : ""} Posted ${item.lastUpdated?.prettyDate()}</p>"
                }
                out << "</div>"
            }
            else if ( type == "link" && isYoutube(value) ) {
                def matcher = (  value =~ /.*youtube.com\/watch\?v=(\w+).*/ )
                if (matcher.count) {
                    String yid = matcher[0][1]
                    out << "<div class='item' >"
                    out << "<a class='postitem_link' href=\"${value}\" target=_blank><img class='postItem' src='http://img.youtube.com/vi/${yid}/3.jpg' /></a>" 
                    out << "<p class='desc'>${item.description ? item.description + '<br/>' : ""} Posted ${item.lastUpdated?.prettyDate()}</p>"
                    out << "</div>"
                } else {
                    out << value
                }
            } else {
                out << value
            }
        }

        out << "</div>"

    }

    /**
     * Check if the link points to a image.
     * @return true if it's image, false otherwise
     */
    def boolean isImage(url) {
        if (!url) return false
        boolean contentType 

        try {
            contentType = (new URL(url).openConnection().getHeaderField('Content-Type')) ==~ /image.*/
        } catch (e) {
            log.error( e )
        }
        return contentType
    }

    def isYoutube(String url) {
        return ( url ==~ /.*youtube.com\/.*/)
    }

}
