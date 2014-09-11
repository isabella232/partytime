class PebbleIntegrationGrailsPlugin {
    def version = 0.1
    def dependsOn = [partyTime:"0.1 > 1.0"]

    def author = "tcp@favoritemedium.com"
    def authorEmail = "tcp@favoritemedium.com"
    def title = "Pebble Blog Integration Plugins"
    def description = '''\
    Pebble blog integration plugin. Provides friendly service API to access Pebble blog engine features.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/PebbleIntegration+Plugin"

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
