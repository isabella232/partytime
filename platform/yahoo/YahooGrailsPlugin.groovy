class YahooGrailsPlugin {
    def version = 0.1
    def dependsOn = [:]

    def author = "aj"
    def authorEmail = "asri@favoritemedium.com"
    def title = "Yahoo BBAuth"
    def description = '''\
    	Grails implementation of Yahoo BBAuth. Consists of a taglib and verification of the Yahoo callback.
    	Token is put into session.
	'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/Yahoo+Plugin"

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }
   
    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)		
    }

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional)
    }
	                                      
    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }
	
    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
