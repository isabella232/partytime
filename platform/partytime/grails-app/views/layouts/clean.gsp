<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
        <title><g:layoutTitle default="Grails" /></title>
        <g:layoutHead />
        <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <social:cssAndJavascripts />

    </head>
    <body>
            <div id=topbar class="container_12">
                <div class="grid_8">
                        <span style="color:#ffffff;font-size:1em;font-weight:bold;margin-left:1.5em;font-family:verdana,sans-serif">Party</span><span style="color:#FCCDFF;font-size:1em;font-weight:bold">Time</span>
                       <span style="padding:0.5em;padding-left:1em"><g:link controller="home" action="index">Home</g:link></span>
                </div>

                <div class="grid_4"> </div>
            </div>
        
        <div class="clear"></div>

            <div id="navigation" class="container_12">

                <div class="grid_4">
                    <span class="identity">${session.user}</span> 
                </div>
                
                <div class="grid_8 rightnavigation">
                <!-- Navigation menu -->
                    <security:ifLoggedIn showInstructions="true" >
                         <social:menu />
                    </security:ifLoggedIn >

                    <security:ifNotLoggedIn >
                      <g:link controller="login" action="index">Login</g:link> 
                     | <g:link controller="login" action="register">Register</g:link>
                    </security:ifNotLoggedIn >
                <!-- EO Navigation menu -->
                </div>

            </div>

        <g:layoutBody />		

        <!-- Footer container -->
        <div class="container_12">
            <div id="footer" class="grid_12 alpha">
                <div class="grid_4 alpha">&nbsp;</div>
                <div class="grid_8 omega"> 
                    <a href="http://code.google.com/p/party-time/">Get Party Time Code</a> -- Build ${application['REVISION']}
                    <g:link controller="profile" action="changeTheme" params="[theme:'a']">Change Theme</g:link>
                </div>
            </div>
        </div>

    </body>	
</html>

