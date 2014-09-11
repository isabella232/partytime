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


class Message {

    static final String NORMAL = "normal"
    static final String CHAR = "chat"
    static final String GROUP_CHAT = "group_chat"
    static final String HEADLINE = "headline"
    static final String ERROR = "error"

    static final String READ = "read"
    static final String UNREAD = "unread"


    User from
    User to
    String subject
    String body
    String thread
    Date dateCreated
    Date lastUpdated
    String type = NORMAL
    String status = UNREAD

    static constraints = {
        body(blank:true, size:0..5000)
        subject(blank:true, nullable:true)
        thread(blank:true, nullable:true)
    }

}

