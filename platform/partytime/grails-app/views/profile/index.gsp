<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="${ session.theme ?: 'site'}" />
        <title>Profile | ${member}</title>
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'style.css')}" />
    </head>

    <body>
        <div class="clear">&nbsp;</div>

        <div id="content-body" class="container_12">

            <div class="clear">&nbsp;</div>

            <div class="grid_3">

                <profile:info userId="${member.id}" title="My Profile" />
                <div class="gapper">&nbsp;</div>

                <social:friendsList userId="${member.id}" title="My Friends" />
                <div class="gapper">&nbsp;</div>

            </div>

            <div class="grid_7">
                <g:if test="${flash.message}">
                    <div class="message">${flash.message}</div>
                </g:if>

                <!-- Edit Profile section -->
                <div id="profile_edit">
                    <h5>Basic Information</h5>
                    <table>
                        <tr>
                            <td>Given Name</td>
                            <td>${member.givenName}</td>
                        </tr>
                        <tr>
                            <td>Family Name</td>
                            <td>${member.familyName}</td>
                        </tr>
                        <tr>
                            <td>Sex</td>
                            <td>${member.profile.getAttribute('sex')}</td>
                        </tr>
                        <tr>
                            <td>Birthday</td>
                            <td>
                            <profile:attribute name="dob" type="date" />
                            </td>
                        </tr>
                        <tr>
                            <td>Location</td>
                            <td>${member.profile?.getAttribute('location')}</td>
                        </tr>
                    </table>
                </div>

                <div id="profile_edit">
                <h5>Personal Information</h5>
                    <table>
                        <tr>
                            <td>Hobby</td>
                            <td>${member.profile?.getAttribute('hobby')}</td>
                        </tr>
                        <tr>
                            <td>Interest</td>
                            <td>${member.profile?.getAttribute('interest')}</td>
                        </tr>
                        <tr>
                            <td>Favorite Music</td>
                            <td>${member.profile?.getAttribute('favMusic')}</td>
                        </tr>
                        <tr>
                            <td>About Me</td>
                            <td>${member.profile?.getAttribute('about')}</td>
                        </tr>
                    </table>
                </div>

                <div id="profile_edit">
                <h5>Contact Information</h5>
                    <table>
                        <tr>
                            <td>E-mail</td>
                            <td>${member.email}</td>
                        </tr>
                        <tr>
                            <td>Instant Message</td>
                            <td>${member.profile.getAttribute('im')}</td>
                        </tr>
                        <tr>
                            <td>Mobile Phone</td>
                            <td>${member.profile.getAttribute('mobilephone')}</td>
                        </tr>
                        <tr>
                            <td>Land Phone</td>
                            <td>${member.profile.getAttribute('landphone')}</td>
                        </tr>
                    </table>
                </div>
                <profile:ifOwnProfile userId="${member.id}">
                    To edit click <g:link controller='profile' action='edit' id="${member.id}">here</g:link>
                </profile:ifOwnProfile>
                <!-- End of Edit Profile section -->
            </div>

            <div class="grid_2">
                <div id="right-sidebar"> </div>
            </div>

            <div class="clear">&nbsp;</div>


        </div>

    </body>
</html>

