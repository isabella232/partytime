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


class TribeMessage {

    static searchable = true

    String subject
    String body
    User author
    Tribe tribe
    TribeMessage originalMessage
    Date dateCreated
    Date lastUpdated


    public String toString() {
        return "${body} -- by ${author.toString()}"
    }

    static belongsTo = TribeMessage
    static hasMany = [replies:TribeMessage]

    static constraints = {
        subject(nullable:true, blank:true)
        body(nullable:true, blank:false)
        originalMessage(nullable:true)
    }

}

