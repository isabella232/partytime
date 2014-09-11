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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://www.facebook.com/2008/fbml">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="site" />
        <title>PartyTime -- Login to continue</title>
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">

            <div class="clear gapper">&nbsp;</div>

            <div class="clear">&nbsp;</div>

            <div class="grid_4">
            &nbsp;
                <div class="clear">&nbsp;</div>
            </div>

            <div class="grid_8">
                <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
                </g:if>
                <div id="right-sidebar">
                        <g:form action="registersave" method="post" >
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
                                                <label for="password">Password:</label>
                                            </td>
                                            <td valign="top" class="value ${hasErrors(bean:userInstance,field:'password','errors')}">
                                                <input type="password" id="password" name="password" value="${fieldValue(bean:userInstance,field:'password')}"/>
                                            </td>
                                        </tr> 
                                    
                                        <tr class="prop">
                                            <td valign="top" class="name">
                                                <label for="email">Email:</label>
                                            </td>
                                            <td valign="top" class="value ${hasErrors(bean:userInstance,field:'email','errors')}">
                                                <input type="text" id="email" name="email" value="${fieldValue(bean:userInstance,field:'email')}"/>
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
                            </div>
                        </g:form>


                    <br/>
                    <!-- Login providers links -->
                    <table style="width:300px" >
                        <tr>
                            <td colspan="4"><b>Alternative Registration with</b></td>
                        </tr>
                        <tr>
                            <td> <g:link controller="login" action="openIdRegistration"><img title="Register with OpenID"  style="width:75px" src="${request.contextPath}/images/login/openid.gif"></g:link> </td>
                            <td><g:link controller="yahoo" action="login"><img title="Login with Yahoo!"  style="width:75px;height:20px" src="${request.contextPath}/images/login/yahoo.gif" /></g:link></td>
                            <td> <g:link controller="login" action="windowsLiveRegistration"><img title="Login with Windows Live ID" style="width:40px;height:40px" src="${request.contextPath}/images/login/liveid.jpg" /></g:link>
                            </td>
                            <td>
                            	<fb:login-button size="large" background="white" length="short" onlogin="login()">
                            	</fb:login-button>
                           	</td>
                           	<td>
                          		<g:oauthLink consumer='google' returnTo="[controller:'login',action:'googleLogin']" error="[controller:'login',action:'index']">
                          			<img title="Login with Google"  style="width:75px;height:20px" src="${request.contextPath}/images/login/google.jpg" />
                          		</g:oauthLink>
                        	</td>
                        </tr>
                    </table>
                    <!-- eo Login providers links -->
                </div>
            </div>

            <div class="clear">&nbsp;</div>
            
        </div>
		
		<script type="text/javascript">
			function login() {
				FB.Connect.ifUserConnected('/partytime/login/facebookLogin', '/partytime'); 
			}
		</script>
		<g:facebookConnectJavascript />
    </body>
</html>

