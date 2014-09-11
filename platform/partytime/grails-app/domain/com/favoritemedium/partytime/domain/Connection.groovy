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


class Connection {

    static final String CREATED_STATUS = "created"
    static final String SENT_STATUS = "sent"
    static final String FAILED_STATUS = "failed"
    static final String EXPIRED_STATUS = "expired"
    static final String ACCEPTED_STATUS = "accepted"
    static final String DECLINED_STATUS = "declined"
    static final String NA_STATUS = "na"

    User fromFriend
    User toFriend
    String status = CREATED_STATUS

    Date dateCreated
    Date lastUpdated

    public String toString() {
        return "${fromFriend} --->  ${toFriend}"
    }

    static constraints = {
        status(nullable:true, blank:true)
    }

}


