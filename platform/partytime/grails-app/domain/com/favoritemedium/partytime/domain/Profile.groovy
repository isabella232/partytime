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

import java.text.SimpleDateFormat


class Profile {

    def dateFormater = new SimpleDateFormat("dd/MM/yyyy")

    
    static searchable =  {
        attributes component:true
    }

    User user
    Date dateCreated
    Date lastUpdated
    boolean privateProfile = false


    def Profile() {}

    def Profile(user) {
        this.user = user
    }
    
    public String toString() {
        def sb = new StringBuilder("Profile")
        this.attributes?.each { 
            sb << it.toString()
        }
        return sb.toString()
    }

    def setAttribute(name, value, group="default") {
        def attribute = this.attributes.find { it.name == name && it.attributeGroup == group }
        if (attribute) {
            attribute.value = value
        } else {
            def att = new ProfileAttribute(name:name, value:value, attributeGroup:group)
            
            this.addToAttributes(att)
            if (! this.save() ) {
                this.errors.allErrors.each {
                    log.error( it )
                }
            }
        }
    }

    def setAttributeAsDate(name, Date value, group="default") {
        String dateValue = dateFormater.format(value)
        setAttribute(name, dateValue, group)
    }

    def getAttribute(name, group=null) {
        def attribute = this.attributes.find { it.name == name }
        return attribute?.value ?: ""
    }

    def getAttributeAsDate(name, group=null) {
        def value = getAttribute(name, group)
        Date dateValue = null

        if (value) {
            dateValue = dateFormater.parse( value )    
        }
        return dateValue
    }

    static belongsTo = User

    static hasMany = [attributes:ProfileAttribute]

}

