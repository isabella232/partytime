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


class SecurityTagLib {

    static namespace = "security"

    /**
     * Execute if the user is login.
     */
    def isLoggedIn = { attrs, body ->
        if ( session.user || session.userId ) {
           out << body()
        }
    }

    /**
     * Execute the body is the user is login.
     */
    def ifLoggedIn = { attrs, body ->
        if ( session.user || session.userId ) {
          out <<  body()
        }
    }

    /**
     * Execute the body if the user is not login.
     */
    def ifNotLoggedIn = { attrs, body ->
        def showInstructions = attrs.showInstructions
        if ( !( session.user || session.userId ) ) {
          out <<  body()
        }
    }

    /**
     * Execute if the user is admin user.
     */
    def ifAdmin = { attrs, body ->

        def adminGroup = UserGroup.findByName("Administrator") ?: UserGroup.findAll(" from UserGroup as g where g.name like ? and ? in elements(g.members)", ["%dmin%", session.user] )

        if ( session.user && adminGroup) {
            out << body()
        }
    }
}

