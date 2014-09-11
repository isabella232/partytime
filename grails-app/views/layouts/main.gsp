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
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'960.css')}" />
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'960.reset.css')}" />
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'960.text.css')}" />
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'site.css')}"  />
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'style.css')}" />

        <g:javascript src="jquery-1.3.1.min.js" />

        <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <script type="text/javascript">
            var BUILD_ID = '$Id: site.gsp 357 2009-02-03 06:18:29Z seymores $';
        </script>
    </head>
    <body>
        <div id="sitebar" class="container_12">
            <div class="grid_4 alpha leftsitebar">
                <span style="color:#ffffff;font-size:1em;font-weight:bold;margin-left:1.5em;font-family:verdana,sans-serif">Party</span><span style="color:#FCCDFF;font-size:1em;font-weight:bold">Time</span>
               <span style="padding:0.5em;padding-left:1em"><g:link controller="home" action="index">Home</g:link></span>
            </div>
            <!-- XXX: Replace site bar links with taglib -->
            <div class="grid_7 omega rightsitebar">
                <security:ifLoggedIn showInstructions="true" >
                 <g:link controller='profile' action='index' id="${session.user?.id}">${session.user?.toString()}</g:link> 
                 | <friend:shortcut /> 
                 | <tribe:shortcut /> 
                 | <blog:shortcut /> 
                 | <postItem:shortcut />
                 | <g:link controller="message" action="inbox"><message:unreadStatus /></g:link> 
                 | <g:link controller="login" action="logoff">Sign Off</g:link>
                </security:ifLoggedIn >

                <security:ifNotLoggedIn >
                  <g:link controller="login" action="index">Login</g:link> 
                 | <g:link controller="login" action="register">Register</g:link>
                </security:ifNotLoggedIn >
            </div>
            
            <div class="clear">&nbsp;</div>

            <div id="header" class="grid_12">
                <div class="grid_7 alpha"><!-- <img src="${createLinkTo(dir:'images',file:'logo.png')}" alt="Grails" /> --></div>
                <div class="grid_5 omega"></div>
            </div>
        </div>

        <g:layoutBody />		

        <div class="clear">&nbsp;</div>
        <!-- Footer container -->
        <div class="container_12">
            <div id="footer" class="grid_12">
                <div class="grid_4 alpha">&nbsp;</div>
                <div class="grid_8 omega"> 
                <a href="http://code.google.com/p/party-time/">Get Party Time Code</a> -- Build ${application['REVISION']}
                </div>
            </div>
        </div>

    </body>	
</html>

