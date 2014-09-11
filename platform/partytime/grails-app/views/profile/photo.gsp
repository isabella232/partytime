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
        <title>Profile Photo | ${member}</title>
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'style.css')}" />
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">

            <div class="clear gapper">&nbsp;</div>

            <div class="clear">&nbsp;</div>

            <div class="grid_3">

                <profile:info userId="${member?.id}"/>

                <div class="gapper">&nbsp;</div>
                <social:friendsList userId="${member?.id}" title="Friends"/>
            </div>

            <div class="grid_7">

            			<h1>File Upload:</h1><br>

			 <g:form controller="profile" action="upload" method="post"  enctype="multipart/form-data">
	                <div class="dialog">
	                    <table>
	                        <tbody>
	                            <tr class="prop">
	                                <td valign="top" class="name">
	                                    <label for="fileUpload">Upload:</label>
	                                </td>
	                                <td valign="top" class="value ${hasErrors(bean:fileResourceInstance,field:'upload','errors')}">
	                                    <input type="file" id="fileUpload" name="fileUpload" />
	                                </td>
	                            </tr> 
	                        </tbody>
	                    </table>
	                </div>
	                <div class="buttons">
	                    <span class="button"><g:actionSubmit class="upload" value="Upload" action="upload" /></span>
	                </div>
	            </g:form>

                <h5>Current Profile Photo</h5>
                <profile:photo userId="${member.id}" />

            </div>

            <div class="grid_2">
                <div id="right-sidebar">
                </div>
            </div>

            <div class="clear">&nbsp;</div>


        </div>

    </body>
</html>

