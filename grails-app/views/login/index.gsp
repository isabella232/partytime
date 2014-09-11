<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://www.facebook.com/2008/fbml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="login" />
        <title>PartyTime -- Login to continue</title>
        
        <openid:css />
        <style type="text/css">
            #loginbox {
                font-family: verdana, arial, helvetica, sans-serif;
                font-size: 1.3em;
            }

            .righter {
                text-align: right;
            }

            .message {
                color:#DF0174;
            }
        </style>
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">

            <div class="clear">&nbsp;</div>

            <div class="grid_4">
                <div class="logo"><!-- <img src="${createLinkTo(dir:'images',file:'logo.png')}" alt="Socialr" title="Socialr" /> --></div>	
                <div class="clear">&nbsp;</div>
            </div>

            <div class="grid_8">

                <div id="loginbox">
                    <g:form controller="login" action="login">
                    <span class="strongtitle">Please login with your Account ID</span>
                    <g:if test="${flash.message}">
                    <div class="message">${flash.message}</div>
                    </g:if>
                    <g:hasOauthError>
						<div class="errors">
							<g:renderOauthError />
						</div>
					</g:hasOauthError>

                    <br/> 
                    <div class="grid_2 alpha righter">Username</div>
                    <div class="grid_4 omega"><input type="text" name="username" /></div>
                    <div class="clear">&nbsp;</div>
                    <div class="grid_2 alpha righter">Password</div>
                    <div class="grid_4 omega"><input type="password" name="password" /></div>
                    <div class="clear">&nbsp;</div>
                    <div class="grid_1 alpha righter">&nbsp;</div>
                    <div class="grid_4 omega righter"><input type="submit" value="Continue" /> or <g:link action="register">Register new account</g:link> <g:link controller="login" action="retrievePassword">Forgot password</g:link>
                    <br/>

                    <br/>
                    <!-- Login providers links -->
                    <table>
                        <tr>
                            <td colspan="4"><b>Alternative login</b></td>
                        </tr>
                        <tr>
                            <td> <a href="#" id="useOpenID"><img title="Login with OpenID"  style="width:75px" src="${request.contextPath}/images/login/openid.gif"></a> </td>
                            <td> <g:link controller="yahoo" action="login"><img title="Login with Yahoo!"  style="width:75px;height:20px" src="${request.contextPath}/images/login/yahoo.gif" /></g:link></td>
                            <td> <a href="#" id="useWindowLiveID"><img title="Login with Windows Live ID" style="width:40px;height:40px" src="${request.contextPath}/images/login/liveid.jpg" /></a>
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

                    <br/>
                    <br/>
                    </div>

                    <div class="grid_1 omega righter">&nbsp;</div>
                    </g:form>
                </div>

                <div id="openIDLoginBox">
                    <h3>OpenID Login</h3>
                    <openid:hasLoginError>
                        <div class="errors">
                            <ul>
                                <li><openid:renderLoginError /></li>
                            </ul>
                        </div>
                    </openid:hasLoginError>

                    <openid:form success="[controller:'login', action:'openIdLogin']" error="[controller:'login', action:'openIdError']">
                        <openid:input size="30" value="http://" /> (e.g. http://username.myopenid.com)
                        <br/>
                        <g:submitButton name="login" value="Login" />
                    </openid:form>

                    <g:link controller="login" action="openIdRegistration">
                        Register with OpenID
                    </g:link>
                    or <a href="#" id="oldLoginForm">Use normal login</a>
                </div>

                <div id="windowLiveId" style="width:400px">
                    <g:form controller="login" action="windowLiveLogin">
                        <table>
                            <tr>
                                <td>Live ID</td><td><input name="username"></input></td>
                            </tr>
                            <tr>
                                <td>Password</td><td><input type="password" name="password"></input></td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="submit" value="Login"></input> 
                                        or <a href="#" id="oldLoginForm2">Use normal login</a>
                                </td>
                            </tr>
                        </table>
                    </g:form>
                </div>
            </div>

            <div class="grid_2"> 
            </div>

            </div>

            <div class="clear">&nbsp;</div>

        </div>

        <script type="text/javascript">
            $(document).ready( function() {
                $('#openIDLoginBox').hide();

                $('#useOpenID').click( function() {
                    $('#openIDLoginBox').toggle();
                    $('#loginbox').toggle();
                });
                
                $('#oldLoginForm').click( function() {
                    $('#openIDLoginBox').toggle();
                    $('#loginbox').toggle();
                });
                
                $('#windowLiveId').hide();
                
                $('#useWindowLiveID').click( function() {
                    $('#windowLiveId').toggle();
                    $('#loginbox').toggle();
                });
                
                $('#oldLoginForm2').click( function() {
                    $('#windowLiveId').toggle();
                    $('#loginbox').toggle();
                });
            });
            
            function login() {
				FB.Connect.ifUserConnected('/partytime/login/facebookLogin', '/partytime'); 
			}
        </script>
		
		<g:facebookConnectJavascript />
    </body>
</html>

