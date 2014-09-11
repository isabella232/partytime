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
        <title>My Friends</title>
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">

            <div class="clear gapper">&nbsp;</div>

            <div class="grid_3">
                <profile:info userId="${member.id}/>
            </div>

            <div class="grid_9">
                    <span style="font-weight:bold;font-size:14pt;padding:1.5em">My Friends</span> <g:link controller="friend" action="all">Add new friend</g:link>
                    <g:if test="${flash.message}">
                    <div class="message">${flash.message}</div>
                    </g:if>
                    
                    <div id="friend_search">
                        <g:form controller="friend" action="search">
                        <input type="text" class="friend_search" name="searchfriend" />
                        <input type="submit" value="Search" />
                        </g:form>
                    </div>
                    <div class="clear">&nbsp;</div>

                    <div class="list">
                        <table>
                            <thead>
                                <tr>
                                    <td title=""></td>
                                    
                                    <g:sortableColumn property="familyName" title="Display Name" />
                                
                                    <g:sortableColumn property="email" title="Email" />
                                
                                    <g:sortableColumn property="dateCreated" title="Last Update" />

                                    <td></td>

                                </tr>
                            </thead>
                            <tbody>
                            <g:each in="${friends}" status="i" var="userInstance">
                                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                
                                    <td><g:link controller='profile' action="index" id="${userInstance.id}"><profile:thumbnail userId="${userInstance.id}"/></g:link></td>
                                
                                    <td>${userInstance?.toString()}</td>
                                
                                    <td>${fieldValue(bean:userInstance, field:'email')}</td>
                                
                                    <td>${userInstance.lastUpdated?.prettyDate()}</td>

                                    <td><social:isConnected userId="${userInstance.id}" /></td>
                                
                                </tr>
                            </g:each>
                            
                            <g:each in="${pendingFriends}" status="i" var="userInstance">
                                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                
                                    <td><g:link controller='profile' action="index" id="${userInstance.id}">${fieldValue(bean:userInstance, field:'username')}</g:link></td>
                                
                                    <td>${userInstance?.toString()}</td>
                                
                                    <td>${fieldValue(bean:userInstance, field:'email')}</td>
                                
                                    <td>${fieldValue(bean:userInstance, field:'active')}</td>
                                
                                    <td>${userInstance.lastUpdated?.prettyDate()}"</td>

                                    <td><social:isConnected user="${userInstance}" /></td>
                                
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                    <div class="paginateButtons">
                        <g:paginate total="${totalCount}" />
                    </div>
            </div> <!-- grid_9 -->

            <div class="clear">&nbsp;</div>

        </div>

    </body>
</html>

