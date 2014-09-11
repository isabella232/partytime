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

import org.springframework.context.*
import com.favoritemedium.partytime.domain.*
import com.favoritemedium.partytime.service.*


/**
 * Event log is the event message to be displayed in the app front page. 
 * It's like FaceBook Wall messages.
 */
class EventLogService implements ApplicationContextAware, IEventLogService {

    def appContext 
    def connService // is actually ConnectionService but renamed to prevent auto inject 


    /**
     * Logs an event for the given user.
     * The log type will be auto set to "user".
     *
     * @param user
     * @param subject
     * @param description
     */
    def void log(User user, String subject, String description) {
        def event = new UserEventLog(user:user, type:"user", name:subject, body:description) 
        if ( !event.save() ) {
            event.errors.allErrors.each {
                log.error( it )
            }
        }
    }

    /**
     * Logs an event for the given tribe.
     * The log type will be auto set to "tribe".
     *
     * @param tribe
     * @param subject
     * @param description
     */
    def void log(Tribe tribe, String subject, String description) {
        def event = new TribeEventLog(tribe:tribe, type:"tribe", name:subject, body:description) 
        if ( !event.save() ) {
            event.errors.allErrors.each {
                log.error( it )
            }
        }
    }
    
    /**
     * Logs system level event.
     * This log will be displayed and viewable by all the users of the system.
     *
     * @param type Default to "system"
     * @param subject
     * @param description
     */
    def void log(String subject, String description) {
        def event = new EventLog(type:EventLog.SYSTEM, name:subject, body:description) 
        if ( !event.save() ) {
            event.errors.allErrors.each {
                log.error( it )
            }
        }
    }
    
    /**
     * Generic event logger.
     * This log will be displayed and viewable by all the users of the system 
     * if no type is given.
     *
     * @param type Default to "system"
     * @param subject
     * @param description
     */
    def void log(String type="system", String subject, String description, String dongle) {
        def event = new EventLog(type:type, name:subject, body:description) 
        if ( !event.save() ) {
            event.errors.allErrors.each {
                log.error( it )
            }
        }
    }

    /**
     * Get any event messages that is relevant to the given user.
     * Will return 7 days or 20 messages by default.
     */
    def getNewsFor(User user, int daysEvent=7, int msgNumber=15) {

        if (!connService ) {
            connService = appContext.getBean("connectionService")
        }

        // XXX:TODO 1 Find only news for the past 7 days

        def news = []

        //1. Get Tribe news that this user is part of
        //UserEventLog.findAllByUserNotEqual(user, [sort:'lastUpdated', order:'desc']) {
        UserEventLog.list(sort:'lastUpdated', order:'desc', max:10).each {
            if ( connService.isConnected( it.user, user ) ) news << it
        }

        //2. Get news about this user's friends
        TribeEventLog.list( [sort:'lastUpdated', order:'desc']  ).each {
            if ( it.tribe?.isPartOfTribe( user ) ) news << it
        }

        //3. Get system news
        news += EventLog.findAllByType(EventLog.SYSTEM, [sort:'lastUpdated', order:'desc'])
        news = news.sort { it.dateCreated }?.reverse()

        def results 
        if ( news?.size() > msgNumber ) {
            results = news[0..msgNumber]
        } else {
           results = news 
        }

        return results
    }

    void setApplicationContext(org.springframework.context.ApplicationContext appContext) {
        this.appContext = appContext
    }

}

