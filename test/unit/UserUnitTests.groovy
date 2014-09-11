import grails.test.GrailsUnitTestCase 
import com.favoritemedium.partytime.domain.*


class UserUnitTests extends GrailsUnitTestCase {


    void testFailedValidation1() {
        def user = new User(password:"test123")

        mockDomain(User, [user])

        assertFalse user.validate()
    }

    void testFailedValidation2() {
        def user = new User(username:'test', password:"")

        mockDomain(User, [user])

        assertFalse user.validate()
    }

}

