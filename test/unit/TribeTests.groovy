import grails.test.GrailsUnitTestCase 
import com.favoritemedium.partytime.domain.*



class TribeTests extends GrailsUnitTestCase {


    void testSetAndGetAttribute() {
        def user = new User(username:"test", password:"test123")
        def tribe = new Tribe(name:"Test", description:"Test tribe", creator:user)

        mockDomain(User, [user])
        mockDomain(Tribe, [tribe])

        tribe.setAttribute("mantra", "Grails Forever!")
        assertEquals "Should be the same attribute back", 
                        tribe.getAttribute("mantra"), "Grails Forever!"

    }

    void testValidation() {
        def user = new User(username:"test", password:"test123")
        def tribe = new Tribe(description:"Test tribe", creator:user)

        mockDomain(User, [user])
        mockDomain(Tribe, [tribe])

        assertFalse tribe.validate()
    }


}
