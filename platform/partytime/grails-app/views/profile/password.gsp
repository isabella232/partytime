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
        <meta name="layout" content="${ session.theme ?: 'site'}" />
        <title>Profile | ${member}</title>
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'style.css')}" />
        <openid:css />
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">
            <div class="grid_3">
                <profile:info userId="${member?.id}"/>
                <div class="gapper">&nbsp;</div>
                <social:friendsList userId="${member?.id}" title="Friends"/>
            </div>

            <div class="grid_7">
                
                <g:if test="${flash.message}">
                    <div class="message">${flash.message}</div>
                </g:if>

                <!-- Edit Profile section -->
                <g:form controller='profile' action='savePassword'>
                <div id="profile_edit">
                    <h5>Change Password</h5>
                    <table>
                        <tr>
                            <td>Current Password</td>
                            <td><input type=password name="currentPassword" /></td>
                        </tr>
                        <tr>
                            <td>New Password</td>
                            <td><input type=password name="password1" value="" /></td>
                        </tr>
                        <tr>
                            <td>New Password Again</td>
                            <td><input type=password name="password2" value=""  /></td>
                        </tr>
                    </table>
                    <input type="submit" value="Change Password" />
                </div>

                </g:form>
                <!-- End of Edit Profile section -->

                <br/><br/>
                <div id="openid_section">
                    Current OpenID: ${member.identifier}
                    <h5>Register OpenID Account</h5>
                    <openid:hasLoginError>
                        <div class="errors">
                            <ul>
                                <li><openid:renderLoginError /></li>
                            </ul>
                        </div>
                    </openid:hasLoginError>
                    <openid:form success="[controller:'profile', action:'registerOpenId']">
                        <openid:input size="30" value="http://" /> (e.g. http://username.myopenid.com)
                        <br/>
                        <g:submitButton name="login" value="Register" />
                    </openid:form>
                                
                </div>
                
                <br/><br/>
                <div id="yahoo_section">
                    Current token: ${member.token}
                    <h5>Register Yahoo Account</h5>
                    <g:link controller="yahoo" action="login"><img title="Login with Yahoo!"  style="width:75px;height:20px" src="${request.contextPath}/images/login/yahoo.gif" /></g:link>
                </div>

                <br/><br/>
                <div id="windowsliveid_section">

<img title="Login with Yahoo!"  style="width:25px;height:20px" src="${request.contextPath}/images/login/liveid.jpg" />
                    Windows Live ID Enabled: ${ member.getAttribute("liveId") ? "<span style='color:green'>Yes</span>" : "<span style='color:blue'>No</span>"  }

                    <g:form controller="profile" action="registerwindowLive">
                        <table>
                            <tr>
                                <td>Live ID</td><td><input name="username"></input></td>
                            </tr>
                            <tr>
                                <td>Password</td><td><input type="password" name="password"></input></td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="submit" value="Register with Live ID"></input> 
                                </td>
                            </tr>
                        </table>
                    </g:form>
                    
                </div>
                
                <br/><br/>
                <div id="facebook_section">
                    Current uid: ${member.facebookId}
                    <h5>Register Facebook Account</h5>
                    <fb:login-button size="large" background="white" length="long" onlogin="register()">
                  	</fb:login-button>
                </div>

            </div>

            <div class="grid_2">
                <div id="right-sidebar">
                </div>
            </div>

            <div class="clear">&nbsp;</div>


        </div>
		<script type="text/javascript">
			function register() {
				FB.Connect.ifUserConnected('/partytime/profile/registerFacebook', null); 
			}
        </script>
		
		<g:facebookConnectJavascript />
    </body>
</html>

