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
import com.favoritemedium.partytime.service.*


class UserManagerService {

    boolean transactional = true

    /**
     * Change user password.
     *
     * @param u User
     * @param original Original password
     * @param latest New password
     * @return true for successful change, false otherwise
     */
    def Boolean changePassword(User u, String original, String latest) {
        def user = User.findByUsernameAndPassword(u.username, original)
        boolean changed = false

        if ( user?.password == original) {
            user.password = latest
            if (user && user.save()) {
                changed = true
            } else {
                log.error("User not found -- Password not changed")
                user.errors.allErrors.each {
                    log.error( it )
                    System.err.println( it )
                }
            }
        }

        return changed
    }

    /**
     * Change user password.
     *
     * @param userId User's ID
     * @param original Original password
     * @param latest New password
     * @return true for successful change, false otherwise
     */
    def Boolean changePassword(Long userId, String original, String latest) {
        assert userId, "userId must not be null"
        def user = User.get( userId )
        return changePassword(user, original, latest)
    }

}

