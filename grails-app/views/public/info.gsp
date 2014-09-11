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
        <title>${member.toString()} Information</title>
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">

            <div class="clear gapper">&nbsp;</div>

            <div class="clear">&nbsp;</div>

            <div class="grid_2">
                <div id="left-sidebar">
                Member Profile
                </div>
            </div>

            <div class="grid_8">
                <table>
                    <tbody>

                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:member, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Username:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:member, field:'username')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Email:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:member, field:'email')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Active:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:member, field:'active')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Date Created:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:member, field:'dateCreated')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Family Name:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:member, field:'familyName')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Given Name:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:member, field:'givenName')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Last Updated:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:member, field:'lastUpdated')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Profiles:</td>
                            
                            <td  valign="top" style="text-align:left;" class="value">
                                <ul>
                                <g:each var="p" in="${member.profiles}">
                                    <li><g:link controller="profile" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                        </tr>
                    
                    </tbody>
                </table>

                <div id="friends">
                    
                    <g:if test="${friends}">
                    ${member}'s Friend${friends.size()>1 ? "s" : ""}
                        <ul>
                        <g:each in="${friends}" var="friend">
                            <li>${friend.toString()}</li>
                        </g:each>
                        </ul>
                    </g:if>
                    <g:else>
                        <Strong>No friends yet</strong>
                    </g:else>

                </div>
            </div>

            <div class="grid_2">
                <div id="right-sidebar">
                Member's Tribes
                </div>
            </div>

            <div class="clear">&nbsp;</div>

        </div>

    </body>
</html>


