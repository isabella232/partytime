<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--
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
-->
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="${session.theme ?: 'site' }" />
        <title>Home</title>
    </head>

    <body>

        <div id="content-body" class="container_12">

            <div class="clear">&nbsp;</div>


            <div class="grid_3">
                
                <profile:info userId="${member?.id}"/>
                
                <div class="gapper">&nbsp;</div>

            </div>

            <div class="grid_6">

            <social:news title="Latest Activities"/>

            <div class="gapper">&nbsp;</div>
            <tribe:tribeSummaries userId="${member.id}"/>

            </div>

            <div class="grid_3">
                <tribe:tribeList userId="${member.id}" title="My Tribes" max="" />
                
                <div class="gapper">&nbsp;</div>
                <social:friendsList userId="${member?.id}" title="Friends"/>

                <div class="gapper">&nbsp;</div>
                <postItem:list userId="${member.id}" />
            </div>

            <div class="clear">&nbsp;</div>

        </div>
        <div class="clear">&nbsp;</div>

    </body>
</html>

