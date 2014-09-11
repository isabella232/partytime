import grails.test.*
import grails.test.MockUtils
import grails.test.GrailsUnitTestCase


class UserManagerServiceTests extends GrailsUnitTestCase {

    User user

    void setUp () { 
        super.setUp()

        user = new User(username:"tester", password:"test123")
        assertEquals  user.username, "tester"
    }
    
    void testSimpleDomainOps() {
        assertEquals  user.username, "tester"
    }

    void testChangePassword() {

        mockDomain(User, [new User(username:'tester', password:'test', email:'test@mail.com')])
        
        UserManagerService userManager = new UserManagerService()
        assert userManager, "User manager must not be null"


        assertTrue userManager.changePassword(user, "test123", "test234")
        assertFalse userManager.changePassword(user, "test235", "test123")

/*
        assertTrue userManager.changePassword(user.id, "test123", "test234")
        assertFalse userManager.changePassword(user.id, "test235", "test123")
*/
    }

}
