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
        <meta name="layout" content="site" />
        <title>Socialr -- Password retrieval</title>
        <openid:css />
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">

            <div class="clear gapper">&nbsp;</div>

            <div class="clear">&nbsp;</div>

            <div class="grid_8">
                <div id="loginbox">
                    <span class="strongtitle">Password retrieval</span>
                    <g:if test="${flash.message}">
                    <div class="message">${flash.message}</div>
                    </g:if>

                   <g:form controller="login" action="retrievalConfirmation" method="post" >
                    <div class="dialog">
                                <table>
                                    <tbody>

                                        <tr class="prop">
                                            <td valign="top" class="name">
                                                <label>Please enter the registered email:</label>
                                            </td>
                                            <td valign="top" class="value">
                                                <input type="text" name="email" />
                                            </td>
                                        </tr>
                                        <tr class="prop">
                                            <td valign="top" class="name">
                                                <label>Or a registered username:</label>
                                            </td>
                                            <td valign="top" class="value">
                                                <input type="text" name="username" />
                                            </td>
                                        </tr>

                                    </tbody>
                                </table>
                            <span class="button">
                                 <g:submitButton name="recover" value="Recover" />
                            </span>
                       </div>
                    </g:form>

                </div>
            </div>

            <div class="clear">&nbsp;</div>


        </div>

    </body>
</html>

