<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <g:layoutHead />
        <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <social:cssAndJavascripts />

    </head>
    <body>
        
        <!-- Header -->
        <div id="topbar" class="container_12">
            
            <div class="grid_5">
            <span style="color:#fff;font-weight:bold;font-family;verdana;font-size:9pt;letter-spacing:2;padding-left:1.3em">Party Time</span>
            </div>

            <div class="grid_7" style="text-align:right">
                <security:ifLoggedIn showInstructions="true" >
                    <g:link controller="login" action="logoff" style="padding-right:1em;color:#fff;text-decoration:none">Sign off</g:link>
                </security:ifLoggedIn >

                <security:ifNotLoggedIn >
                  <g:link controller="login" action="index">Login</g:link> 
                 | <g:link controller="login" action="register">Register</g:link>
                </security:ifNotLoggedIn >
            </div>
            <div class="clear"></div>
        </div> 
        <div class="clear"></div>
        <!-- eof header -->
        
        <!-- Gutter -->
        <div id="gutter" class="container_12">
           
            <div class="grid_12" style="height:40px;padding:1em 1em 0" > 
               <span class="identity"> ${session.user} </span>
            </div>
            <div class="grid_8">
                <security:ifLoggedIn showInstructions="true" >
                     <social:menu />
                </security:ifLoggedIn >
            </div>

            <div class="grid_4">
            </div>
            <div class="clear"></div>
        </div> 
        <div class="clear"></div>
        <!-- eof gutter -->

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
        <!-- eof Footer container -->

    </body>	
</html>

