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
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'style.css')}" />
        <title>Message Inbox | ${session.user?.toString()}</title>
        <style type="text/css">
           .read {
               color:#666;
           }
        </style>
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">

            <div class="clear gapper">&nbsp;</div>

            <div class="clear">&nbsp;</div>

            <div class="grid_2">
                <div id="left-sidebar">
                <security:ifLoggedIn>
            <!--    <g:link controller="message" action="write">Write Message</g:link> -->
                &nbsp;
                </security:ifLoggedIn>
                </div>
            </div>

            <div class="grid_10"> <!-- Start of grid_8, page body -->
            <h1>Message Inbox</h1>
                <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
                </g:if>
                    <div id="friend_search">
                        <g:form controller="message" action="search">
                        <input type="text" class="message_search" name="searchmessage" />
                        <input type="submit" value="Search" />
                        </g:form>
                    </div>

                    <div class="clear">&nbsp;</div>

                    <div class="list">
                        <table>
                            <thead>
                            </thead>
                            <tbody>
                            <g:each in="${messages}" status="i" var="message">
                                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                                    <td>
                                        <g:link controller="profile" action="index" id="${message.from?.id}">${message.from}</g:link>
                                        <br/>
                                        ${message.dateCreated}
                                    </td>
                                
                                
                                    <td>
                                        <div id="message_entry">
                                        <span class="${message.status}"><strong>${message.subject}</strong></span>
                                        <br/>
                                       <g:link controller="message" action="read" id="${message.id}">${message.body}</g:link>
                                       </div>
                                    </td>

                                
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                    <div class="paginateButtons">
                        <g:paginate total="${msgCount}" />
                    </div>


            </div> <!-- End of grid_8 -->

            <div class="clear">&nbsp;</div>

        </div>

    </body>
</html>


