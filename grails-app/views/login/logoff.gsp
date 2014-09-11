<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://www.facebook.com/2008/fbml">
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="site" />
        <title>PartyTime -- Logoff to continue</title>
        
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
    
	<body onload=>
		<div name='dif' id="content-body" class="container_12">
			<g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
			<script type="text/javascript">
				
				function logoff() {
					FB.Connect.ifUserConnected( FB.Connect.logoutAndRedirect('/partytime'), '/partytime');
                    window.location = "${request.contextPath}/login";
				}
				
				window.onload = logoff;
			</script>
			
			<g:facebookConnectJavascript />
		</div>
	</body>
</html>
