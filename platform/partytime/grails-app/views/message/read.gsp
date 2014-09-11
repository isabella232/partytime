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
        <title>Inbox | Read Message</title>

        <style type="text/css">
            div#message {
                background: #e9e9e9;
                padding: 2em;
                margin-right: 1em;
                border-right: 2px solid #ccc;
                border-bottom: 2px solid #ccc;
            }

            div.message_head {
                border-bottom: 2px solid #ccc;
            }
        </style>

    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">

            <div class="clear gapper">&nbsp;</div>

            <div class="clear">&nbsp;</div>

            <div class="grid_2">
                <div id="left-sidebar">
                <security:ifLoggedIn>
                Write Message
                </security:ifLoggedIn>
                </div>
            </div>

            <div class="grid_10"> <!-- Start of grid_10  page body -->
                <div id="message">
                    <div class="message_head">
                    <h5>Subject: ${message.subject}</h5>
                        From: ${message.from} <br/>
                        When: ${message.dateCreated}
                    </div>
                    <div class="message_body">
                    <p> 
                        <br/>
                        <br/>
                        ${message.body}
                    </p>
                    <g:link controller="message" action="delete" id="${message.id}">Delete</g:link>
                    </div>
                </div>
            </div> <!-- End of grid_10 -->

            <div class="clear">&nbsp;</div>

        </div>

    </body>
</html>


