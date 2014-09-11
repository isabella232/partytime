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
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'style.css')}" />
        <title>Write New Message | ${session.user.toString()}</title>
        <style type="text/css">
            input.big_text {
               font-size: 15pt;
               width: 500px;
            }

            textarea.msg {
                height: 150px;
                width: 500px;
                font-size: 12pt;
            }

        </style>
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">


            <div class="clear">&nbsp;</div>

            <div class="grid_2">
                <div id="left-sidebar">
                <security:ifLoggedIn>
                <g:link controller="message" action="inbox">Back to Inbox</g:link>
                </security:ifLoggedIn>
                </div>
            </div>

            <div class="grid_10"> <!-- Start of grid_8, page body -->

                <g:form action='save'>
                    <h4>To</h4>
                    <input type="text" class="text big_text" name="to" value="${message?.to}"></input>

                    <h4>Subject</h4>
                    <input type="text" class="text big_text" name="subject" value="${message?.subject}"></input>

                    <h4>Message</h4>
                    <textarea name="messageBody" class="msg">${message?.body?.encodeAsHTML()}</textarea>

                    <div class="clear gapper">&nbsp;</div>
                    <div>
                        <br/>
                        <g:actionSubmit value="Send"/> or <g:link controller="message" action="inbox">Cancel</g:link>
                    </div>
                </g:form>
            </div> <!-- End of grid_8 -->

        </div>

    </body>
</html>


