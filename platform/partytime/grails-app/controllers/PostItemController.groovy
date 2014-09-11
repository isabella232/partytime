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


class PostItemController {

    def contentProcessorService


    def index = { redirect(action:'list') }

    def list = {
        def user = User.get( params.id ?: session.user.id )
        def items = user.profile.postedItems.sort { it.lastUpdated }?.reverse()

        [postedItems:items, member:user]
    }

    def remove = {
        def item = PostItem.get( params.id )

        item.delete()
        flash.message = "PostItem ${params.id} deleted"
        redirect(action:'list')
    }

    def save = {
        println params
        flash.message = "Saved posted item"
        redirect(action:'list')
    }

    def add = {
        def user = User.get( params.id ?: session.user.id )

        String name = params.name ?: ""
        String value = params.link ?: params.content
        String type =  params.link ? "link" : "other"
        String desc = params.description ?: ""

        def postItem = new PostItem(name:name, value:value, type:type, description:desc)
        user.profile.addToPostedItems(postItem)

        if ( postItem.save()) {
            contentProcessorService.process(postItem)
            flash.message = "Posted item"
        } else {
            flash.message = "Failed to post item"
            postItem.errors.allErrors.each {
                log.error( it )
            }
        }
        redirect(action:'list')
    }

    

}
