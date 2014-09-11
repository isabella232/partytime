import grails.test.ControllerUnitTestCase
import com.favoritemedium.partytime.domain.*
import groovy.mock.interceptor.MockFor


/**
 * Remember these params:
 *    redirectArgs
 *    renderArgs
 *
 */
class  LoginControllerTests extends ControllerUnitTestCase {

    def controller
    def ptMailService
    def user

    void setUp() {
        super.setUp()
        controller = new LoginController()
        user = new User(username:"test", password:'test123',email:'test@test.com',auth:'right-auth')
        mockDomain(User, [user])
        ptMailService = new PtMailService()
        ptMailService.metaClass.sendRecoveryEmail = { user -> if(!user) throw new IllegalArgumentException("Cannot send mail to 'null' user") }
    }

    void testFailureResetPassword() {
        controller.params.user = user.id
        controller.params.auth = 'wrong-auth'
        controller.resetPassword()
        assertTrue "Should preset error message", controller.flash.message ==~ /.*Wrong authorization.*/
        assertEquals "Should be redirected to index", "index", renderArgs.view
    }

    void testSucessfulResetPassword() {
        controller.params.user = user.id
        controller.params.auth = 'right-auth'
        controller.resetPassword()
        assertTrue "Should reset the password", controller.flash.message ==~ /.*Your new password.*/
        assertEquals "Should be redirected to index", "index", renderArgs.view
    }

    void testWithEmailSucessfulRetrievalConfirmation() {
        controller.ptMailService = ptMailService
        controller.params.email = 'test@test.com'
        controller.retrievalConfirmation()
        assertTrue "Should send confimation email", controller.flash.message ==~ /.*Account information has.*/
        assertEquals "Should be redirected to retrieval form", "retrievalForm", renderArgs.view
    }

    void testWithUsernameSucessfulRetrievalConfirmation() {
        controller.ptMailService = ptMailService
        controller.params.username = 'test'
        controller.retrievalConfirmation()
        assertTrue "Should send confimation email", controller.flash.message ==~ /.*Account information has.*/
        assertEquals "Should be redirected to retrieval form", "retrievalForm", renderArgs.view
    }

    void testFailureRetrievalConfirmation() {
        controller.ptMailService = ptMailService
        controller.params.email = 'support'
        controller.retrievalConfirmation()
        assertTrue "Should not send confimation email", controller.flash.message ==~ /.*No account with given.*/
        assertEquals "Should be redirected to retrieval form", "retrievalForm", renderArgs.view
    }
    
    /**
     * Test out the login page access with login session.
     */
    void testIndexPage() {

        controller.session['user'] = user
        controller.index()

        assertEquals "Expect to display GSP with model containing the user object",
                        "home", redirectArgs.controller
    }


    void testLoginUnsuccessful() {

        controller.params.username = "test"
        controller.params.password = "test"

        controller.login()
        assertEquals "Should be redirect to 'index' page",
                "index", redirectArgs.action
    }

    void testLoginSuccessful() {

        controller.params.username = "test"
        controller.params.password = "test123"

        controller.login()

        assertEquals "Should be redirect to 'home' page",
                "home", redirectArgs.controller
    }

    void testLogoff() {

        controller.logoff()

        assertEquals "Should be redirect to 'index' page",
                "index", redirectArgs.action
    }

    void testFailedOpenIdLogin() {
        def service = mockFor(OpenidService)
        service.metaClass.getIdentifier = { args -> '12345' }
        controller.openidService = service

        controller.openIdLogin()
        assertEquals "Should be redirect to 'index' page",
                "index", redirectArgs.action
    }

    void testSuccessOpenIdLogin() {
        def service = mockFor(OpenidService)
        service.metaClass.getIdentifier = { args -> '12345' }
        controller.openidService = service
        user.identifier = "12345"
        controller.session['userr'] = user

        controller.openIdLogin()
        assertEquals "Should be redirect to 'index' page",
                "home", redirectArgs.controller
    }

    void testRegisterSave() {
        def logArgs = [:]
        def service = mockFor(EventLogService)
        service.metaClass.log =  { arg1, arg2, arg3 -> logArgs }
        controller.eventLogService = service

        controller.params.username = "newUserABC"
        controller.params.password = "test123"

        controller.registersave()
        assertEquals "Should be redirect to 'index' page",
                "index", redirectArgs.action

    }

    void testRegisterSaveFailed() {
        def logArgs = [:]
        def service = mockFor(EventLogService)
        service.metaClass.log =  { arg1, arg2, arg3 -> logArgs }
        controller.eventLogService = service

        controller.params.username = "newUserABC"
        // Failed without password provided
        // controller.params.password = "test123"

        controller.registersave()
        assertEquals "Should be redirect to 'register' page because password not provided",
                "register", renderArgs.view

    }
}