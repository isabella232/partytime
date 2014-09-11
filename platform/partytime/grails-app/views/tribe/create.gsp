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
        <title>Start New Tribe</title>
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">

            <div class="clear gapper">&nbsp;</div>

            <div class="clear">&nbsp;</div>

            <div class="grid_2">
                <div id="left-sidebar">
                <!--Profile -->
                &nbsp;
                </div>
            </div>

            <div class="grid_8">
                
                <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
                </g:if>

            <g:form controller="tribe" action="save" >
            <input type="hidden" name="id" value="${tribe.id}" />
                <table>
                    <tr>
                        <td>Tribe Name</td>
                        <td><input type="text" name="name" value="${tribe?.name}" /></td>
                    </tr>
                    <tr>
                        <td>Description</td>
                        <td><textarea name="description">${tribe?.description}</textarea></td>
                    </tr>
                    <tr>
                        <td>Tribe Mantra</td>
                        <td><textarea name="mantra">${tribe?.getAttribute('mantra')}</textarea></td>
                    </tr>
                    <tr>
                        <td>Invite only?</td>
                        <td><g:checkBox name="inviteOnly" value="${tribe?.inviteOnly}"/></td>
                    </tr>
                </table>
                <g:actionSubmit value="Save" /> or 
                    <g:if test="${tribe}">
                        <g:link controller='tribe' action='index' id="${tribe?.id}">Cancel</g:link>
                    </g:if>
                        <g:link controller='tribe' action='all'>Back to list</g:link>
                </g:form>
            </div>

            <div class="grid_2">
                <div id="right-sidebar">
                Right bar
                </div>
            </div>

            <div class="clear">&nbsp;</div>


        </div>

    </body>
</html>


