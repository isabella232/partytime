import org.codehaus.groovy.grails.commons.ConfigurationHolder

class YahooController {
	def yahooService
	
	def login = {
		def loginUrl = yahooService.getLoginUrl()
		redirect(url: loginUrl)
	}
	
	def callback = {
		log.info("Callback called")
		def redirectUrl = ConfigurationHolder.config.yahoo.redirectNotLoggedInUrl
		log.info("redirectUrl: ${redirectUrl}")
		def reqUrl = request.request.requestURL.toString()
		if (yahooService.verifySignature(reqUrl, params)) {
			log.info("Login Successful with token ${params.token}")
			session.token = params.token
			redirectUrl = ConfigurationHolder.config.yahoo.redirectLoggedInUrl
		}
		log.info("redirectUrl: ${redirectUrl}")
		redirect(uri: redirectUrl)
	}
}
