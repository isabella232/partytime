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

import com.favoritemedium.partytime.domain.*


class LoginController {

    def eventLogService
    def openidService
    def liveIDService
    def facebookConnectService
    def oauthService


    def index = { 
        if (session.user) { // || openidService.isLoggedIn(session)) {
            redirect(controller:'home')
        }
     // request.properties.each {
     //     println "  ** ${it} "
     // }
    }
    
    def register = { }

    def login = {

        print params

        def user = User.findByUsernameAndPassword( params.username, params.password )

        if (user) {
            session.user = user
            session.userId = user.id
            redirect(controller:'home')
        } else {
            flash.message = "Wrong username or password"
            redirect(action:'index')
        }

    }
    
    def logoff = {
		flash.message = "Signing off..."
        session.invalidate()
    }
    
    def cancelRegister = {
        session.invalidate()
        redirect(action:'index')
    }
    
    def windowLiveLogin = {

        print params
        def user

        def token = liveIDService.authenticate(params.username, params.password)

        //XXX: FIX ME -- make sure the profile - attribute relationship works
        if (token) {
            //user = User.findByUsername(params.username)
            User.list().each { u ->
                if (user) return

                def profile = u?.profile

                def attributes = profile?.attributes

                def a = attributes.find { it.name == "liveId" && it.value == params.username }

                if (a) {
                    user = u
                }
            }

        } else {
            flash.message = "Invalid Windows Live ID credential"
            redirect(action:'index')
            return
        }

        //def user = User.findByUsernameAndPassword( params.username, params.password )

        if (user) {
            session.user = user
            session.userId = user.id
            session["liveid_token"] = token
            redirect(controller:'home')
        } else {
            flash.message = "No user found with the given Windows Live ID"
            redirect(action:'index')
        }

    }

    def windowsLiveRegistration = {
        print params

        if ( params.username && params.password )  {

            def token = liveIDService.authenticate(params.username, params.password)
            if (!token) {
                flash.message = "Login failed. Please try again with your Windows Live ID"
            } else {
                session["liveid_token"] = token
                flash.message = "Please fill in the details below to continue"
            }

        }
        ["live_username":params.username, "username":params.username]
    }

    def retrievePassword = {
        def email = params.email
        if (email) {
            def u = User.findByEmail(email)
            if (u) {
                // send email to user
                String password = "passwordreset"
                u.password = password
                String msg = """
                Hi ${u},

                Please login with this password: ${password}.
                Remember to change this password before you log out.

                Regards,
                PT
                """
                sendMail {
                        to u.email
                        subject "Party-Time: Password Reset"
                        body msg
                }

                flash.message = "Account information has been mailed to you. Please check your e-mail."
                redirect(controller:'home')
            } else {
                flash.message = "Not account with given e-mail. Please try again"
            }
        }
        render(view:'retrievePassword')
    }

    def registersave = {
        def userInstance = new User(params)
        userInstance.active = true

        if (checkUsername( params.username, "register") ) return

        if(!userInstance.hasErrors() && userInstance.save()) {
            flash.message = "Account created. Please login with your new ID"
            eventLogService.log(EventLog.SYSTEM, "Newcomer, ${userInstance}!", "Newcomer, ${userInstance}!", "dongle")
            redirect(action:'index')
        }
        else {
            flash.message = "Account cannot be created. Please try again."
            render(view:'register', model:[userInstance:userInstance])
        }
    }

    def openIdLogin = {
        def user = User.findByIdentifier( openidService.getIdentifier(session) )

        if (user) {
            session.user = user
            session.userId = user.id
            redirect(controller:'home')
        } else {
        	/*
            flash.message = "No user are registered with this OpenID account"
            redirect(action:'index')
            */
            redirect(action:'openIdRegistration')
        }
    }

    def openIdError = {
        flash.message = "Failed to login with the given OpenID account, please try again."
        redirect(action:'index')
    }

    def openIdRegistration = {
        if(!flash.message)
            flash.message = "Please fill in the details below to continue"
    }

    def registersaveWithOpenId = {
        println params
        def redirectAction = 'openIdLogin'
        def user = new User(params)
        user.password = openidService.getIdentifier( session )
        user.identifier = openidService.getIdentifier( session )

        if (checkUsername(params.username, "openIdRegistration")) return

        if (!user.save()) {
            user.errors.allErrors.each {
                log.error(it)
            }
        	//Warning! Eternal loop
            flash.message = "Failed to create new account. Please try again."
        	redirectAction = 'openIdRegistration'
        }
        redirect(action:redirectAction)
    }
    
    def registersaveWithWindowsLiveID = {
        println params
        def user = new User(params)
        def liveUsername = params["live_username"]
        assert liveUsername, "Live ID Username must be available"

        if ( checkUsername(params.username, "windowsLiveRegistration") ) return

        user.password = liveUsername
        user.setAttribute("liveId", liveUsername) 
        
        if (!user.save()) {
            user.errors.allErrors.each {
                log.error(it)
            }
            flash.message = "Registration with Windows Live ID failed"
            render(view:'windowsLiveRegistration', model:["live_username":liveUsername, "username":liveUsername])
        } else {
            flash.message = "Registration with Windows Live ID successful"
            session.user = user
            redirect(action:'index')
        }
    }
    
    def yahooLoginOrPairing = {
		// There is already a user in the session, so this yahoo account is to be paired with that user
		if (session.user) {
			redirect(controller:'profile', action:'registerYahoo')
		}
		else {
			redirect(action:'yahooLogin')
		}
    }
    
    def yahooLogin = {
		if (session.token) {
			def user = User.findByToken( session.token )

	        if (user) {
	            session.user = user
	            session.userId = user.id
	            redirect(controller:'home')
	        } else {
	        	/*
	            flash.message = "No user are registered with this Yahoo account"
	            redirect(action:'index')
	            */
	            redirect(action:'yahooRegistration')
	        }
		}
    }

    def yahooError = {
        flash.message = "Failed to login with the given Yahoo account, please try again."
        redirect(action:'index')
    }

    def yahooRegistration = {
        if (flash.message)
            flash.message = "Please fill in the details below to continue"
    }

    def registersaveWithYahoo = {
        def redirectAction = 'yahooLogin'
        
        def user = new User(params)
        // See if we can reverify the token
        //user.password = openidService.getIdentifier( session )
        //user.identifier = openidService.getIdentifier( session )

        if (checkUsername(params.username, "yahooRegistration")) return

        user.password = session.token
        user.token = session.token
        if (!user.save()) {
        	//Warning! Eternal loop
            user.errors.allErrors.each {
                log.error(it)
            }
            flash.message = "Registration failed. Please try again"
        	redirectAction = 'index'
        }
        redirect(action:redirectAction)
    }
    
    def googleLoginOrPairing = {
		// There is already a user in the session, so this yahoo account is to be paired with that user
		if (session.user) {
			redirect(controller:'profile', action:'registerGoogle')
		}
		else {
			redirect(action:'googleLogin')
		}
    }
    
    def googleLogin = {
		if (session.oauthToken) {
			def feedString = oauthService.accessResource('http://www.google.com/m8/feeds/contacts/default/full?max-results=0', 'google', session.oauthToken, null, null)
			def feed = new XmlSlurper().parseText(feedString)
			session.oauthTokenMail = feed.id.text()

			def user = User.findByOauthToken( session.oauthTokenMail )

	        if (user) {
	            session.user = user
	            session.userId = user.id
	            redirect(controller:'home')
	        } else {
	        	/*
	            flash.message = "No user are registered with this Yahoo account"
	            redirect(action:'index')
	            */
	            redirect(action:'googleRegistration')
	        }
		}
    }

    def googleError = {
        flash.message = "Failed to login with the given Google account, please try again."
        redirect(action:'index')
    }

    def googleRegistration = {
        if (flash.message)
            flash.message = "Please fill in the details below to continue"
            
    	[email:session.oauthTokenMail]
    }

    def registersaveWithGoogle = {
        def redirectAction = 'googleLogin'
        
        def user = new User(params)

        if (checkUsername(params.username, "googleRegistration")) return

        user.password = session.oauthTokenMail
        user.oauthToken = session.oauthTokenMail
        if (!user.save()) {
        	//Warning! Eternal loop
            user.errors.allErrors.each {
                log.error(it)
            }
            flash.message = "Registration failed. Please try again"
        	redirectAction = 'index'
        }
        redirect(action:redirectAction)
    }
    
    def facebookLogin = {
		println "facebookLogin: ${params}"
		
		if (facebookConnectService.isLoggedIn(request)) {
			def fbClient = facebookConnectService.getFacebookClient()
			def facebookId = fbClient.users_getLoggedInUser()
			
			session.facebookId = facebookId
		
			def user = User.findByFacebookId( session.facebookId )

	        if (user) {
	            session.user = user
	            session.userId = user.id
	            redirect(controller:'home')
	        } else {
	        	/*
	            flash.message = "No user are registered with this Yahoo account"
	            redirect(action:'index')
	            */
	            redirect(action:'facebookRegistration')
	        }
		}
    }
    
    def facebookRegistration = {

        if (!flash.message )
            flash.message = "Please fill in the details below to continue"
		
		def firstName, lastName

        if (facebookConnectService.isLoggedIn(request)) {
        	def fbClient = facebookConnectService.getFacebookClient()
        	
            def userInfoDoc = fbClient.users_getInfo([session.facebookId], ["last_name", "first_name"]);
			def userInfo = userInfoDoc?.get(0)
					
            firstName = userInfo["first_name"]
            lastName = userInfo["last_name"]
        }
		
		[username:firstName, givenName:firstName, familyName:lastName]
    }
    
    def registersaveWithFacebook = {
		println params
        def redirectAction = 'facebookLogin'
        
        def user = new User(params)
        
        if (checkUsername( params.username, "facebookRegistration")) return

        user.password = session.facebookId
        user.facebookId = session.facebookId

        if (!user.save()) {
        	//Warning! Eternal loop
            user.errors.allErrors.each {
                log.error(it)
            }
            flash.message = "Registration failed. Please try again"
        	redirectAction = 'facebookRegistration'
        }
        redirect(action:redirectAction)
    }

    def cancelRegisterFacebook = {
            session.invalidate()
            redirect(action:'logoff')
    }

    /**
     * Check if the given username already existed in the system.
     * Will redirect to the given redirectUrl is the username exists, do nothing otherwise.
     *
     * @param username
     * @param redirectUrl
     */
    def checkUsername(String username, String redirectUrl="index") {
        
        if ( username && User.findByUsername(username) ) {
           flash.message = "The username is taken. Please try with another username"
           redirect(action:redirectUrl)
           return true
        }
        return false
    }

}


