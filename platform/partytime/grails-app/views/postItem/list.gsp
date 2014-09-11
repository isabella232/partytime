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
        <meta name="layout" content="${session.theme ?: 'site'}" />
        <title>Profile | ${member}</title>
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'style.css')}" />
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">
            <div class="clear gapper">&nbsp;</div>

            <div class="clear">&nbsp;</div>

            <div class="grid_3">

                <profile:info userId="${member.id}" title="My Profile" />

                <div class="gapper">&nbsp;</div>
                <social:friendsList userId="${member.id}" title="My Friends" />
                
                <div class="gapper">&nbsp;</div>
                <tribe:tribeList userId="${member.id}" title="My Tribes" />
            </div>

            <div class="grid_7">
                <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
                </g:if>

                <div class="grid_3">
                
                </div>
                <div class="grid_4">
                
                </div>

            <div class="clear">&nbsp;</div>

                <g:form controller="postItem" action="add" >
                <table>
                <tr>
                    <td>Share a link</td>
                    <td><input name="link" value="${link}" /></td>
                </tr>
                <!--
                <tr style="display;none">
                    <td>or post a embedded content</td>
                    <td> <textarea name="content"></textarea></td>
                </tr>
                -->
                <tr>
                    <td colspan="2">
                    Comment:</br>
                        <textarea style="width:100%" name="description"></textarea>
                    </td>
                </tr>
                </table>
                <g:actionSubmit action="add" value="Save" />

                </g:form>

                <hr/>

                <div id="postitem_list">
                    <g:each in="${postedItems}" status="i" var="item">
                        <div class="post_item" style="padding-bottom:1em">
                            <div style="clear:both"><postItem:resolve id="${item.id}" /></div>
                            <div>
                                <p>
                                    ${item.description}
                                </p>
                                <g:link controller='postItem' action='remove' id="${item.id}">Remove</g:link>
                            </div> 
                        </div>
                    </g:each>
                </div>

            </div>

            <div class="grid_2">
                <div id="right-sidebar"> </div>
            </div>

            <div class="clear">&nbsp;</div>


        </div>

    </body>
</html>

