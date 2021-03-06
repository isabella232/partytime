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


class UserGroup {

    String name //group name
    String description
    String shortName
    Date dateCreated
    Date lastUpdated

    SortedSet members

    static hasMany = [members:User]

    static constraints = {
        name(blank:false, unique:true)
        description(nullable:true, blank:true, size:0..5000)
        shortName(nullable:true, blank:true, unique:true)
    }

}


