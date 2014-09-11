// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text-plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]
// The default codec used to encode data with ${}
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"
grails.converters.encoding="UTF-8"

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
grails.war.destFile="partytime.war"


// set per-environment serverURL stem for creating absolute links
environments {
    production {
        openid.serverURL="http://dev1.favoritemedium.com/partytime"
    }
}
/*
// log4j configuration
log4j {
    appender.stdout = "org.apache.log4j.ConsoleAppender"
    appender.'stdout.layout'="org.apache.log4j.PatternLayout"
    appender.'stdout.layout.ConversionPattern'='[%r] %c{2} %m%n'
    appender.stacktraceLog = "org.apache.log4j.FileAppender"
    appender.'stacktraceLog.layout'="org.apache.log4j.PatternLayout"
    appender.'stacktraceLog.layout.ConversionPattern'='[%r] %c{2} %m%n'
    appender.'stacktraceLog.File'="stacktrace.log"
    rootLogger="error,stdout"
    logger {
        grails {
            app="debug"
        }
        StackTrace="error,stacktraceLog"
        org {
            codehaus.groovy.grails.web.servlet="error"  //  controllers
            codehaus.groovy.grails.web.pages="error" //  GSP
            codehaus.groovy.grails.web.sitemesh="error" //  layouts
            codehaus.groovy.grails."web.mapping.filter"="error" // URL mapping
            codehaus.groovy.grails."web.mapping"="error" // URL mapping
            codehaus.groovy.grails.commons="info" // core / classloading
            codehaus.groovy.grails.plugins="error" // plugins
            codehaus.groovy.grails.orm.hibernate="error" // hibernate integration
            springframework="off"
            hibernate="off"
        }
    }
    additivity.StackTrace=false
}
*/
// Image location
//images.location="../../images/"
images.location="/usr/local/images/"


pebble.enabled=true
pebble.admin.username="sey"
pebble.admin.password="sey"

compress {
    excludePathPatterns = [".*\\.gif", ".*\\.ico", ".*\\.jpg", ".*\\.swf"]
 //   excludeContentTypes = ["image/png", "application/x-shockwave-flash"]
    // include and exclude are mutually exclusive
  //  includeContentTypes = ["text/html", "text/xml", "text/json"]
    includeUserAgentPatterns = []
    excludeUserAgentPatterns = [".*MSIE 4.*"]
    // probably don't want these, but their available if needed
    javaUtilLogger = ""
    jakartaCommonsLogger = ""
    statsEnabled = false
    enabled = true
}

// Requires an accessible server so localhost cannot be used
yahoo {
	appid = '30q1ot7IkYmt5kaRvUog6eRdy2_mlAMY'
	secret = '8a5ed58c8989c9aa9da9e7aabf059716'
	redirectLoggedInUrl = '/login/yahooLoginOrPairing'
	redirectNotLoggedInUrl = '/login/yahooError'
}

environments {
        production {
            facebookConnect {
                APIKey = "fde5bee2a3aadeafc90a382b0cd3cc72"
                SecretKey = "92f9d02b1f525befb98e6c79e2e2faf4"
            }
            images.location="/usr/local/images/"
            openid.serverURL="http://dev1.favoritemedium.com/partytime"
        }
        development {
            facebookConnect {
                APIKey = "989d2b060c7254364ed14fc51e4433fd"
                SecretKey = "9c469cabf1286f1cf28cccd6f0e733d8"
            }
            images.location="../../images/"
            openid.serverURL="http://localhost:8090/partytime"
        }
}

grails {
    mail {
        username = "favmedbot"
        password = "favmedb0t"
        host = "smtp.gmail.com"
        port = 465
        props = ["mail.smtp.auth":"true",
              "mail.smtp.socketFactory.port":"465",
              "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
              "mail.smtp.socketFactory.fallback":"false"]
    }
}

//Requires an accessible server so localhost cannot be used
grails {
	serverURL = "http://pinto.favoritemedium.com/partytime"
}
oauth {
	google {
		requestTokenUrl = 'https://www.google.com/accounts/OAuthGetRequestToken'
		accessTokenUrl = 'https://www.google.com/accounts/OAuthGetAccessToken'
		authUrl = 'https://www.google.com/accounts/OAuthAuthorizeToken'
		consumer.key = 'pinto.favoritemedium.com'
		consumer.secret = 'O5hlznMXi+68SqPp8URp0Kf/'
	}
}

