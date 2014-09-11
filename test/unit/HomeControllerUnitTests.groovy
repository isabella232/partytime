import grails.test.GrailsUnitTestCase 
import com.favoritemedium.partytime.domain.*


class HomeControllerUnitTests extends GrailsUnitTestCase {


    /**
     * Test out the home page access with login session.
     */
    void testHomePageWithSession() {

        def u = new User(username:"test", password:'test123')
        mockDomain(User, [u])

        HomeController.metaClass.getRequest = { -> [method:'GET'] }
        HomeController.metaClass.getSession = { -> [user:u] }

        HomeController hp = new HomeController()
        def model = hp.index()

        assertEquals "Expect to display GSP with model containing the user object", 
                        "test", model.member.username
    }
    
    /**
     * Test out the home page access without login session.
     */
    void testHomePageWithoutSession() {
        def redirectParams = [:]
        HomeController.metaClass.getSession = { -> [user:null] }
        HomeController.metaClass.redirect = { Map args -> redirectParams = args }

        HomeController hp = new HomeController()
        hp.index()
        assertEquals "Expect to redirect to login page", "login", redirectParams.controller
    }

}
