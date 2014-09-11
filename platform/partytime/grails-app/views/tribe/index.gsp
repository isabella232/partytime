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
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'tribe.css')}" />
        <title>Tribe | ${tribe.name}</title>
        <style>
            #tribe_messages {
                clear:both;
                margin-top:1em;
                padding-top:1em;
            }
        </style>
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">


            <div class="clear">&nbsp;</div>

            <div class="grid_2">
                <tribe:control tribeId="${tribe.id}" />
                <br/> Last updated ${tribe.lastUpdated?.prettyDate()} 
            </div>

            <div class="grid_8">
                <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
                </g:if>

                <h2>${tribe.name}</h2> 
                <div class="mantra">
                    <span class="mantra">${tribe.getAttribute("mantra")}</span>
                    <br/>-- tribe mantra, by ${tribe.creator}
                </div>

                <p> 
                    <br/> ${tribe.description} 
                    <br/>
                </p>

                <g:if test="${tribe.inviteOnly}">
                <strong>This is by invite only.</strong>
                </g:if>

                <div class="clear gapper">&nbsp;</div>

                <div id="tribe_member">
                    <h3>Members</h3>
                    
                    <g:each in="${tribe.members}" var="member">
                        <g:link controller="profile" action="index" id="${member.id}" title="${member.toString()}">
                        <profile:thumbnail userId="${member.id}" /></g:link>
                    </g:each>

                    <g:if test="${ (tribe.members?.size() < 1) }" >
                    No members yet.
                    </g:if>

                </div>
                <br/>

                <div id="tribe_messages">
                    <h5>Tribe Board</h5>

                    <g:each in="${messages}" var="msg" status="n">
                        <div class="tribe_message">
                            <span class="tribe_subject">
                            <g:link controller="tribe" action="messages" params="[id:tribe.id, 'msg.id':msg.id]">${msg.subject} by ${msg.author}</g:link>
                            </span><br/>
                            <span class="tribe_short_msg">${msg.replies?.size()} repl${msg.replies?.size() > 1 ? "ies" : "y" }. Last updated on <g:formatDate format="dd MMM yyyy H.mm a" date="${msg.lastUpdated}" /></span>
                        </div>
                    </g:each>

                    <br/>
                    <g:if test="${messages?.size() < 1}">
                        No board discussion yet.
                     <g:link controller="tribe" action="newMessage" params="[id:tribe.id]">
                            Start a new discussion now!
                     </g:link>
                    </g:if>

                    <br/>
                    <g:link controller="tribe" action="allMessage" params="[id:tribe.id]">See all</g:link>
                    <g:link controller="tribe" action="newMessage" params="[id:tribe.id]">New Message</g:link>
                </div>

                <tribe:ifCreator tribeId="${tribe.id}">
                    <g:if test="${ (tribe.pendingList?.size() > 0) }" >
                        <div id="tribe_member">
                        <h3>Pending Members</h3>

                        <table>
                        <g:each in="${tribe.pendingList}" var="member">

                            <tr> 
                                <td style="width:60px"><profile:thumbnail userId="${member.id}" /></td>
                                <td>
                                <g:link controller="profile" action="index" id="${member.id}">${member}</g:link> requested to join this Tribe. <br/>
                                 <g:link controller="tribe" action="approveMembership" id="${tribe.id}" params="[member:member.id]">Accept</g:link> <g:link controller="tribe" action="rejectMembership" id="${tribe.id}" params="[member:member.id]">Reject</g:link>
                                </td>
                            </tr>

                        </g:each>

                        </table>
                    </g:if>
                </div>
                </tribe:ifCreator >

                <g:if test="${false}">
                <div id="tribe_event">
                    <h3>Events</h3>
                    No events coming up for this Tribe
                </div>
                </g:if>
            </div>

            <div class="grid_2">
                <div id="right-sidebar">
                </div>
            </div>

            <div class="clear">&nbsp;</div>


        </div>

    </body>
</html>


