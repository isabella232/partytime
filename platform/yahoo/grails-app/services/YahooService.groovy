import org.codehaus.groovy.grails.plugins.codecs.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class YahooService {
/*
	$appid = "i%3DB%26p%3DUw70JGIdHWVRbpqYItcMw--";  // my application ID, obtained at registration 
	$appdata = "foobar";                             // my optional, arbitrary url-encoded data 
	$ts = time();                                    // seconds since Jan 1, 1970 GMT
	$secret = "a34f389cbd135de4618eed5e23409d34450"; // my shared secret, obtained at registration 

	$sig = md5( "/WSLogin/V1/wslogin?appid=$appid&appdata=$appdata&ts=$ts" . "$secret" );
	$url = "https://api.login.yahoo.com/WSLogin/V1/wslogin?appid=$appid&appdata=$appdata&ts=$ts&sig=$sig";
*/

    boolean transactional = true
    
    def static BASE_URL = 'https://api.login.yahoo.com/WSLogin/V1/wslogin'
	def static RELATIVE_URL = '/WSLogin/V1/wslogin'
    
//	The application ID, which identifies the developer and the application. Obtained at registration.
	def static APPID = ConfigurationHolder.config.yahoo.appid
			
//	Your shared secret is b2edf1e46fbc8cd37cebb81a8a8d6cd8
	def static SECRET = ConfigurationHolder.config.yahoo.secret
			
	def getLoginUrl(def appdata = null, def send_userhash = null) {
//		An optional url-encoded string with a maximum length of 100 characters
		def optional = ""
		if (appdata) {
			optional = "${optional}&appdata=${appdata}"
		}
				
//		Optional unique identifier
		if (send_userhash) {
			optional = "${optional}&send_userhash=${send_userhash}"
		}

		def ts = getTimeStamp()
		
		def sig = getSignature()
		
		def	loginUrl = "${BASE_URL}?appid=${APPID}&ts=${ts}&sig=${sig}${optional}"
		
		return loginUrl
	}

	def getSignature(def appdata = null) {
//		An hexadecimal md5 hash of a particular relative URL:
/*
		* The relative login path (/WSLogin/V1/wslogin), plus
		* Your application ID (?appid=id), plus
		* Optional data to be consumed by your application (&appdata=data), if any, plus
		* The timestamp (&ts=seconds), plus
		* Your shared secret (secret).
*/
		def optional = ""
		if (appdata) {
			optional = "${optional}&appdata=${appdata}"
		}
		
		def ts = getTimeStamp()
	
		def signature = "${RELATIVE_URL}?appid=${APPID}&ts=${ts}${optional}${SECRET}"

		def sig = MD5Codec.encode(signature)
		
		return sig
	}
	
	def verifySignature(def reqUrl, def params) {
//		Check that the ts parameter is within 600 seconds of the current time
		def currentTime = getTimeStamp();
		def clock_skew  = currentTime - Integer.parseInt(params.ts);
		
		if (clock_skew >= 600 ) {
			log.info("Invalid timestamp: clock_skew is ${clock_skew} seconds")
			return false
		}
		
//		Convert the request url to a relative path
		def requestUrl = new StringTokenizer(reqUrl, "/", true).collect{it}
		def relativeUrl = requestUrl[4..requestUrl.size-1].join()
		
//		now calculate the signature, and verify that the resulting signature
//		equals what was passed to us
		def appid = params.appid
		def token = params.token
		def appdata = params.appdata
		def ts = params.ts

		def signature = "${relativeUrl}?appid=${appid}&token=${token}&appdata=${appdata}&ts=${ts}${SECRET}"

		def sig = MD5Codec.encode(signature)
		
		return(params.sig == sig)
	}
	
	def getAppId() {
		return APPID
	}
	
	def getTimeStamp() {
//		The timestamp in seconds as measured from Jan 1, 1970 GMT.
		def mil = new Date().time
		def ts = (int) (mil/1000)
		return ts
	}
}
