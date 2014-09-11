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
        <title>Socialr -- Login to continue</title>
        <openid:css />
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">

            <div class="clear gapper">&nbsp;</div>

            <div class="clear">&nbsp;</div>

            <div class="grid_4">

                <div class="logo"><img src="${createLinkTo(dir:'images',file:'logo.png')}" alt="Socialr" title="Socialr" /></div>	
            </div>

            <div class="grid_8">
                <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
                </g:if>
                <div id="right-sidebar">
                
					<g:form action="registersaveWithGoogle" method="post" >
					    <div class="dialog">
					        <table>
					            <tbody>
					            
					                <tr class="prop">
					                    <td valign="top" class="name">
					                        <label for="username">Username:</label>
					                    </td>
					                    <td valign="top" class="value ${hasErrors(bean:userInstance,field:'username','errors')}">
					                        <input type="text" id="username" name="username" value="${fieldValue(bean:userInstance,field:'username')}"/>
					                    </td>
					                </tr> 
					            
					                <tr class="prop">
					                    <td valign="top" class="name">
					                        <label for="email">Email:</label>
					                    </td>
					                    <td valign="top" class="value ${hasErrors(bean:userInstance,field:'email','errors')}">
					                        <input type="text" id="email" name="email" value="${email}"/>
					                    </td>
					                </tr> 
					            
					                <tr class="prop">
					                    <td valign="top" class="name">
					                        <label for="givenName">Given Name:</label>
					                    </td>
					                    <td valign="top" class="value ${hasErrors(bean:userInstance,field:'givenName','errors')}">
					                        <input type="text" id="givenName" name="givenName" value="${fieldValue(bean:userInstance,field:'givenName')}"/>
					                    </td>
					                </tr> 
					            
					                <tr class="prop">
					                    <td valign="top" class="name">
					                        <label for="familyName">Family Name:</label>
					                    </td>
					                    <td valign="top" class="value ${hasErrors(bean:userInstance,field:'familyName','errors')}">
					                        <input type="text" id="familyName" name="familyName" value="${fieldValue(bean:userInstance,field:'familyName')}"/>
					                    </td>
					                </tr> 
					                
					            </tbody>
					        </table>
					    </div>
					    <div class="buttons">
					        <span class="button"><input class="save" type="submit" value="Register" /></span>
					        or 
					        <g:link controller="login" action="cancelRegister"> Cancel</g:link>
					    </div>
					</g:form>
                </div>
            </div>

            <div class="clear">&nbsp;</div>


        </div>

    </body>
</html>

