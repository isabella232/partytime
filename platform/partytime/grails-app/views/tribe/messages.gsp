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
        <meta name="layout" content="${ session.theme ?: 'site' }" />
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'tribe.css')}" />
        <title>Tribe | ${tribe.name}</title>
        <style>
            #msg div.tribe_message { 
                background-color: #fff;
                border: 1px solid #c9c2c1;
                margin-top: 1.5em;
                width: 400px;
                padding: 8px;
                padding-left: 15px;
            }

            div#msg cite {
                padding-left:20px;
                padding-top: 10px;
                position: relative;
                top:6px;
                margin:0px;
                background: transparent url(${request.contextPath}${pluginContextPath}/images/tip.gif) no-repeat scroll 15px 0;
            }
        </style>
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">


            <div class="clear">&nbsp;</div>

            <div class="grid_2">
                Last updated on <g:formatDate format="d MMM yyyy H.mm a" date="${message.lastUpdated}" />
            </div>

            <div class="grid_10">
                <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
                </g:if>

                <h2><g:link controller="tribe" action="index" params="[id:tribe.id]">${tribe.name}</g:link></h2> 
                <div class="clear gapper">&nbsp;</div>

                
                <span class="tribe_subject">${message.subject}</span>
                <tribe:ifCreator tribeId="${tribe.id}" >
                <g:link controller="tribe" action="deleteMessage" id="${message.id}">Delete</g:link>
                </tribe:ifCreator>

                <div class="tribe_meta">by ${message.author} on <g:formatDate format="d MMM yyyy H.mm a" date="${message.lastUpdated}" /> | ${message.replies?.size()} repl${message.replies?.size() > 1 ? "ies" : "y"}</div>
                <div class="tribe_message">${message.body}</div>
                
                <div class="clear gapper">&nbsp;</div>

                <div class="tribe_reply_box">
                    <g:form controller="tribe" action="replyMessage">
                        <textarea name="reply" style="width:400px; height:80px"></textarea>
                        <input type="hidden" name="msg.id" value="${message.id}" />
                        <br/>
                        <input type="submit" value="Reply" />
                    </g:form>
                </div>

                <!-- List all the replies for this message -->
                <g:if test="${message.replies}" >
                <div id="messages">

                    <g:each in="${message.replies?.sort { it.lastUpdated }?.reverse() }" var="msg" status="i">
                        <div class="bubble" id="msg">
                            <div class="tribe_message">${msg.body?.replaceAll('\n', "<br/>")}</div> 
                            <cite><g:link controller="profile" action="index">${msg.author}</g:link> (${msg.lastUpdated?.prettyDate()})</cite>
                        </div>
                    </g:each>
                
                </div>
                <script type="text/javascript">
                    DD_roundies.addRule('#msg div.tribe_message', '5px', true);
                </script>
                </g:if>

            </div>

            <div class="clear">&nbsp;</div>

        </div>

    </body>
</html>


