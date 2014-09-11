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


class Tribe {

    static searchable = {
        attributes component:true
    }

    User creator
    String name
    String description
    Boolean inviteOnly = false
    
    Date dateCreated
    Date lastUpdated

    static hasMany = [administrators:User, moderators:User, members:User, attributes:TribeAttribute, pendingList:User]

    def setAttribute(name, value="", group="default") {
        def attribute = this.attributes.find { it.name == name && it.attributeGroup == group }
        if (attribute) {
            attribute.value = value
        } else {
            def att = new TribeAttribute(name:name, value:value, attributeGroup:group)
            this.addToAttributes(att)
            if (! this.save() ) {
                this.errors.allErrors.each {
                    log.error( it )
                }
            }
        }
    }

    def getAttribute(name, group=null) {
        def attribute 
        if (group) {
            attribute = this.attributes.find { it.name == name && it.attributeGroup == group }
        } else {
            attribute = this.attributes.find { it.name == name }
        }
        return attribute?.value ?: ""
    }

    def boolean isMember(User u) {
        return this.members?.contains( u )
    }

    def boolean isModerator(User u) {
        return this.moderators?.contains( u )
    }

    def boolean isAdmin(User u) {
        return this.administrators?.contains( u )
    }

    def boolean isPartOfTribe( User u ) {
        return (
            isMember( u ) ||
            isModerator( u ) ||
            isAdmin( u ) ||
            ( u == creator )
        )
    }

    public String toString() {
        return name
    }

    static constraints = {
        name(blank:false)
        description(blank:true, size:0..5000)
        inviteOnly(nullable:true)
    }

}


