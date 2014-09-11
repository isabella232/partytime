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
        <title>Tribe List</title>
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">

            <div class="clear gapper">&nbsp;</div>

            <div class="clear">&nbsp;</div>

            <div class="grid_2">
                <div id="left-sidebar">
                <security:ifLoggedIn>
         <!--       Create new admin Tribe -->
                </security:ifLoggedIn>
                &nbsp;
                </div>
            </div>

            <div class="grid_10"> <!-- Start of grid_8, page body -->
            <h1> Tribe list page </h1>
                <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
                </g:if>
                    <div id="friend_search">
                        <g:form controller="tribe" action="search">
                        <input type="text" class="tribe_search" name="searchtribe" />
                        <input type="submit" value="Search" />
                        </g:form>
                    </div>

                    <div class="clear">&nbsp;</div>

                    <div class="list">
                        <table>
                            <thead>
                                <tr>
                                    <g:sortableColumn property="username" title="Description" />
                                
                                    <g:sortableColumn property="email" title="Creator" />
                                
                                    <g:sortableColumn property="active" title="Active Members" />
                                
                                    <g:sortableColumn property="dateCreated" title="Last Update" />

                                    <td></td>

                                </tr>
                            </thead>
                            <tbody>
                            <g:each in="${tribes}" status="i" var="tribe">
                                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                
                                    <td><g:link controller='tribe' action="index" id="${tribe.id}">${tribe.name}</g:link><br/>${tribe.description}</td>
                                
                                    <td>${fieldValue(bean:tribe, field:'creator')}</td>
                                
                                    <td>${tribe.members?.size()}</td>
                                
                                    <td>${tribe.dateCreated}</td>

                                    <td>
                                        <tribe:ifNotMember tribeId="${tribe.id}" userId="${session.user.id}">
                                            <g:link controller="tribe" action="join" id="${tribe.id}">Join this Tribe</g:link>
                                        </tribe:ifNotMember>
                                    </td>
                                
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                    <div class="paginateButtons">
                        <g:paginate total="${com.favoritemedium.partytime.domain.Tribe.count()}" />
                    </div>


            </div> <!-- End of grid_8 -->

            <div class="clear">&nbsp;</div>

        </div>

    </body>
</html>


