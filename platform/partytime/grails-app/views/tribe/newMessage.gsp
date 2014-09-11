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
        <meta name="layout" content="${ session.theme ?: 'site'}" />
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'tribe.css')}" />
        <title>Tribe | ${tribe.name}</title>
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">


            <div class="clear">&nbsp;</div>

            <div class="grid_2">&nbsp; </div>

            <div class="grid_10">
                <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
                </g:if>

                <h2><g:link controller="tribe" action="index" params="[id:tribe.id]">${tribe.name}</g:link></h2> 
                <div class="clear gapper">&nbsp;</div>

                <div class="clear gapper">&nbsp;</div>

                <div class="tribe_reply_box">
                    <g:form controller="tribe" action="saveMessage">
                        Subject: <input type="text" name="subject" />
                        <br/>
                        <textarea name="body"></textarea>
                        <input type="hidden" name="id" value="${tribe.id}" />
                        <br/>
                        <input type="submit" value="Send" /> or <g:link controller="tribe" action="index" id="${tribe.id}">Cancel</g:link>
                    </g:form>
                </div>

            </div>

            <div class="clear">&nbsp;</div>

        </div>

    </body>
</html>


