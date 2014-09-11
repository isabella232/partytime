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

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="site" />

        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'style.css')}" />
        <title>All Members</title>
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">

            <div class="clear gapper">&nbsp;</div>

            <div class="grid_3">
                <div id="left-sidebar">
                
                <h5>User Management</h5>
                <g:link controller="user" action="create">Add new member</g:link>
                <br/>

                </div>
            </div>

            <div class="grid_9">
                    <h1>User List</h1>
                    <g:if test="${flash.message}">
                    <div class="message">${flash.message}</div>
                    </g:if>
                    <div class="list">
                        <table>
                            <thead>
                                <tr>
                                    <g:sortableColumn property="username" title="Username" />
                                    
                                    <g:sortableColumn property="familyName" title="Display Name" />
                                
                                    <g:sortableColumn property="email" title="Email" />
                                
                                    <g:sortableColumn property="active" title="Active" />
                                
                                    <g:sortableColumn property="dateCreated" title="Last Update" />

                                    <td></td>

                                </tr>
                            </thead>
                            <tbody>
                            <g:each in="${members}" status="i" var="userInstance">
                                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                
                                    <td><g:link action="info" id="${userInstance.id}">${fieldValue(bean:userInstance, field:'username')}</g:link></td>
                                
                                    <td>${userInstance?.toString()}</td>
                                
                                    <td>${fieldValue(bean:userInstance, field:'email')}</td>
                                
                                    <td>${fieldValue(bean:userInstance, field:'active')}</td>
                                
                                    <td><g:prettyDate time="${userInstance.lastUpdated}" /></td>

                                    <td><social:isConnected user="${userInstance}" /></td>
                                
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                    <div class="paginateButtons">
                        <g:paginate total="${User.count()}" />
                    </div>
            </div> <!-- grid_9 -->

            <div class="clear">&nbsp;</div>

        </div>

    </body>
</html>

