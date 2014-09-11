<g:form action="save" method="post" >
    <div class="dialog">
        <table>
            <tbody>
            
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="username">Username:</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean:userInstance,field:'username','errors')}">
                        <input type="text" id="username" name="username" value="${fieldValue(bean:userInstance,field:'username')}"/>
                    </td>
                </tr> 
            
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="password">Password:</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean:userInstance,field:'password','errors')}">
                        <input type="password" id="password" name="password" value="${fieldValue(bean:userInstance,field:'password')}"/>
                    </td>
                </tr> 
            
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="email">Email:</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean:userInstance,field:'email','errors')}">
                        <input type="text" id="email" name="email" value="${fieldValue(bean:userInstance,field:'email')}"/>
                    </td>
                </tr> 
            
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="givenName">Given Name:</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean:userInstance,field:'givenName','errors')}">
                        <input type="text" id="givenName" name="givenName" value="${fieldValue(bean:userInstance,field:'givenName')}"/>
                    </td>
                </tr> 
            
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="familyName">Family Name:</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean:userInstance,field:'familyName','errors')}">
                        <input type="text" id="familyName" name="familyName" value="${fieldValue(bean:userInstance,field:'familyName')}"/>
                    </td>
                </tr> 
                
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="active">Active:</label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean:userInstance,field:'active','errors')}">
                        <g:checkBox name="active" value="${userInstance?.active}" ></g:checkBox>
                    </td>
                </tr> 
            
            
            </tbody>
        </table>
    </div>
    <div class="buttons">
        <span class="button"><input class="save" type="submit" value="Create" /></span>
    </div>
</g:form>
