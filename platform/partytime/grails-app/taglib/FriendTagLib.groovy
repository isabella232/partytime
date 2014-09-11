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


class FriendTagLib {

    static namespace = "friend"


    /**
     * Shortcut to display link to current login user "My Friends" page.
     * 
     * Example:
     *       <friend:shortcut [title="Optional title"] />
     */
    def shortcut = { attrs, body ->
        def user = attrs.userId ? User.get( attrs.userId ) : User.get( session.user.id )
        assert user

        String title = attrs.title ?: "Friends"

        out << link(controller:'friend', action:'myFriends') { title }
    }

}

