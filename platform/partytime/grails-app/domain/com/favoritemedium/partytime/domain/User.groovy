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

package com.favoritemedium.partytime.domain


class User implements Comparable {

    static searchable = {
        familyName boost: 2.0
        givenName boost: 2.0
    }

    String username
    String password
    String givenName
    String familyName
    String email
    String identifier
    String token = "na"
    String facebookId = "na"
    boolean active = true

    Date dateCreated
    Date lastUpdated
    Profile profile = new Profile(this)

    def setAttribute(name, value="", group="default") {
        if(!this.profile) {
            this.profile = new Profile()
            this.profile.user = this // For some reason this is need
        }
        this.profile.setAttribute(name, value, group)
    }

    def getAttribute(name) {
        return this.profile?.getAttribute(name)
    }

    public String toString() {
        return "${givenName ?: ""} ${familyName ?: ""}".trim() ?: username
    }

    static constraints = {
        username(blank:false, unique:true)
        email(nullable:true, email:true)
        givenName(nullable:true, blank:true)
        familyName(nullable:true, blank:true)
        identifier(nullable:true, blank:true)
        password(blank:false)
        token(nullable:true, blank:true)
        facebookId(nullable:true, blank:true)
    }

    int compareTo(obj) {
        this.id?.compareTo( obj?.id )
    }

}


