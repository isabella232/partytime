import com.favoritemedium.partytime.domain.*


class PartyTimeBootStrap {

	def init = { servletContext ->
		/* Hack workaround for bug, fixed in grails 1.2
		http://www.nabble.com/No-such-property%3A-save-for-class-...-td23667707.html		
		http://www.nabble.com/save()-method-on-domain-class-seen-as-property-td23659161.html
		*/
		Profile.get(-1)
	
        def admin = User.findByUsername("admin")

            // 1) Init a default admin user if not available
        if (!admin) {
            log.debug(" No admin user, creating one now ")
            admin = new User( username:"admin", 
                              givenName:"Administrator", 
                              familyName:"", 
                              password:'password', active:true)
            admin.email = "support@admin-domain-class.xx"
            if ( !admin.save() ) {
                log.debug("Failed to create admin user. See err log below")
                admin.errors.allErrors.each {
                    log.error(it)
                }
            }
        }

        def adminGroup = UserGroup.findByName("Administrator")
        if (!adminGroup) {
            adminGroup = new UserGroup(name:"Administrator", 
                                    description:"Administrator system group")
            adminGroup.save()
            adminGroup.addToMembers(admin)
            log.info("Added Admin system group")
        }
        

        // Add pretty date func to Date class
        Date.metaClass.prettyDate = {
            def date = delegate
            def curDate = new Date()
            def tdiff = (curDate.time - date.time)/1000
            def ddiff = Math.floor(tdiff/86400)
            
            String prettyDate = "NA"
            
            if (ddiff == 0) {
            
                if (tdiff < 60) {
                    prettyDate = "Moments ago"
                } else if(tdiff < 120) {
                    prettyDate = "1 minute ago"
                } else if(tdiff < 3600) {
                    prettyDate = "${(int)Math.floor(tdiff/60)} minutes ago"
                } else if(tdiff < 7200) {
                    prettyDate = "1 hour ago"
                } else if (tdiff < 86400) {
                    prettyDate = "${(int)Math.floor(tdiff/3600)} hours ago"
                }
            
            } else {
                if (ddiff < 7) {
                    prettyDate = "${(int)ddiff} days ago"
                } else if (ddiff < 31) {
                    prettyDate = "${(int)Math.ceil(ddiff/7)} weeks ago"
                } else if (ddiff < 365) {
                    prettyDate =  "About ${(int)Math.ceil(ddiff/30)} months ago"
                } else {
                    prettyDate = "About ${(int)Math.ceil(ddiff/365)} years ago"
                }
            
            } //eo
            return prettyDate
        }

     }


     def destroy = {
     }
} 
