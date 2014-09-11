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

import javax.activation.FileDataSource 
import com.favoritemedium.partytime.domain.*


class ProfileController {

    def connectionService
    def openidService
    def liveIDService
    def facebookConnectService

    def index = { 

        println params
        def user = User.get ( params.id ?: session.user.id ) 
        assert user, "User must not be null, probably didn't login"

        def friends = connectionService.getFriendsFor(user)
        log.debug("Friends: ${friends}")

        def tribes = []  //Will be replace by actual Tribes

        [friends:friends, member:user, tribes:tribes]
    }

    def edit = {
        println params
        def user = session.user
        def editUser = User.get( params.id )

        if ( user != editUser ) {
            flash.message = "You are not allowed to edit this profile"
        }

        [member:editUser]
    }

    def savePassword = {
        println params
        def user = User.get( session.user.id )

        String currentPassword = params.currentPassword
        String pass1 = params.password1
        String pass2 = params.password2

        if (currentPassword == user?.password) {
            if (pass2 == pass2) {
                user.password = pass1
                flash.message = "Password changed successfully"
            } else {
                flash.message = "Password not matched"
            }

        } else {
            flash.message = "Wrong password given. Password not changed."
        }

        redirect(action:'password')
    }

    def save = {
        println params

        def user = User.get( session.user.id )

        def profile = user.profile
        if (!profile) {
            profile = new Profile()
            user.profile = profile
            profile.user = user
            if (! profile.save()) {
                profile.errors.allErrors.each {
                    log.error( it )
                }
                
                user.errors.allErrors.each {
                    log.error( it )
                }
            }
        }

        // Basic Information
        String givenName = params.givenName
        String familyName = params.familyName
        user.givenName = givenName
        user.familyName = familyName

        int dob_year = Integer.parseInt( params.dob_year )
        int dob_month = Integer.parseInt( params.dob_month )
        int dob_day = Integer.parseInt( params.dob_day )

        def dob = Calendar.instance
        dob.set(dob_year, dob_month-1, dob_day)
        println " ${dob_year}, ${dob_month}, ${dob_day} ==  DOB ==> ${dob}"
        user.profile.setAttributeAsDate( "dob", dob.time )

        String sex = params.sex
        String location = params.location
        //Date dob = params.dob
        user.profile.setAttribute("sex", sex, "basic")
        user.profile.setAttribute("location", location, "basic")


        // Personal Information
        String hobby = params.hobby
        String interest = params.interest
        String favMusic = params.favMusic
        String about = params.about
        
        profile.setAttribute("hobby", hobby, "personal")
        profile.setAttribute("interest", interest, "personal")
        profile.setAttribute("favMusic", favMusic, "personal")
        profile.setAttribute("about", about, "personal")


        // Contact information
        String email = params.email
        String im = params.im
        String mobilePhone = params.mobilephone
        String landPhone = params.landphone
        user.email = email
        profile.setAttribute("im", im, "personal")
        //profile.setAttribute("im_type", imType, "personal")
        profile.setAttribute("mobilephone", mobilePhone, "personal")
        profile.setAttribute("landphone", landPhone, "personal")

        flash.message = "Profile information saved"

        redirect(action:'edit', id:user.id)
    }

    def password = {
        println params
        def user = User.get( session.user.id )

        //TODO: GET RID OF THESE
        def editUser = User.get( params.id )
        if ( user != editUser ) {
      //      flash.message = "You are not allowed to edit this profile"
        }

        [member:user]
    }

    //TODO: Move this out of here.
    def viewPhoto = {
        println params
        def path = params.path
        def name = params.name
        def fullpath = params.fullpath
        def imgPath 

        if (fullpath && ( new File(fullpath).exists() )) {
            imgPath = fullpath
        } else {
            imgPath = grailsApplication.config.images.location.toString() +  path + File.separatorChar + name
            def file = new File(imgPath)
            if (!file.exists()) {
                imgPath = grailsApplication.config.images.location.toString() + "thumb.jpg"
            }
        }

        def ds
        def is
        try {
            ds = new FileDataSource( imgPath ) 
            is = ds.inputStream
        } catch (e) {
            log.error(" Failed to load User's photo: " + e ) 
        }

        response.setContentLength( is.available() )
        response.setContentType( ds.contentType )
        OutputStream out = response.outputStream
        out << is
        out.close() 
    }

    def photo = {
        def user = User.get( session.user.id )
        [member:user]
    }

    def upload = {
        println params
        def user = User.get( session.user.id )

		def f = request.getFile('fileUpload')
	    if(!f.empty) {
	      flash.message = 'Your file has been uploaded'
          def imgDirPath = grailsApplication.config.images.location.toString() + File.separatorChar + user.username 
		  new File( imgDirPath ).mkdirs()
          def file = new File( grailsApplication.config.images.location.toString() + File.separatorChar + user.username + File.separatorChar + f.getOriginalFilename() )
		  f.transferTo( file ) 
          def imageTool = new ImageTool()
          imageTool.load( file.getAbsolutePath() )
          imageTool.thumbnailSpecial(180,180,2,1)
          imageTool.writeResult(imgDirPath + File.separatorChar + "profile.jpg", "JPEG")
          
          imageTool.square()
          imageTool.swapSource()
          imageTool.thumbnailSpecial(40,40,2,2)
          imageTool.writeResult(imgDirPath + File.separatorChar + "thumb.jpg", "JPEG")
          user.profile.setAttribute("uploadedPhoto", "yes", "photo")
          user.profile.setAttribute("photo_file_name", f.getOriginalFilename(), "photo")
		} 
	    else {
	       flash.message = 'file cannot be empty'
	    }
		redirect( action:index )
    }

    def registerOpenId = {
		def identifier = openidService.getIdentifier( session )
		
		if (identifier) {
	        def user = User.get( session.user.id )
	        user.identifier = identifier
	        flash.message = "Registered OpenID with this account"
		}
		
        redirect(action:'password')
    }
        
    def registerYahoo = {
		if (session.token) {
	        def user = User.get( session.user.id )
	        user.token = session.token
	        flash.message = "Registered Yahoo! with this account"
		}
		
        redirect(action:'password')
    }

    def registerwindowLive = {
        def user = User.get( session.user.id )
        def token = liveIDService.authenticate(params.username, params.password)
        if (token) {
            user.setAttribute("liveId", params.username) 
            flash.message = "Registered Windows Live ID with this account"
        } else {
            flash.message = "Failed to register Windows Live ID with this account. Please try again."
        }
        
        redirect(action:'password')
    }
    
    def registerFacebook = {
        println params
        
        if (facebookConnectService.isLoggedIn(request)) {
        	def fbClient = facebookConnectService.getFacebookClient()
			def facebookId = fbClient.users_getLoggedInUser()
			
			session.facebookId = facebookId
			
        	def user = User.get( session.user.id )
        	user.facebookId = session.facebookId
        	flash.message = "Registered Facebook Connect with this account"
        }
        else {
            flash.message = "Failed to register Facebook Connect with this account. Please try again."
        }
        
        redirect(action:'password')
    }
    
    def changeTheme = {
        if (session.theme) {
            session.theme = null
        } else {
            session.theme = "pro"
        }

        redirect(controller:'home', action:'index')
    }

}


