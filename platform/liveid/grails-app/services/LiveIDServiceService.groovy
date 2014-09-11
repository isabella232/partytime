import com.jp.windows.live.*


/**
 * Make the Window Live Authentication
 */
class LiveIDServiceService {

    boolean transactional = false


    /**
     * Authenticate to Windows Live Passport service.
     * Will return security token containg passport information, if
     */
    def authenticate(String username, String password, String realm="live.com") {
        def token 
        try {
            def loginToken = new LogonManager().logon(realm, username, password)
            token = loginToken.properties as Map
        } catch(e) {
            log.error("Failed to login to Windows Live ID: ${e}") 
        }

        return token
    }

}
