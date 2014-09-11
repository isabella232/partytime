import com.favoritemedium.partytime.domain.*


class UserTests extends GroovyTestCase {

    void testSetAndGetAttribute() {

        User user = new User(username:"test", password:"test123")
        user.save(flush:true)

        user.setAttribute("mantra", "Grails Forever!", "test")

        assertEquals "Should be the same attribute back", 
                        user.getAttribute("mantra"), "Grails Forever!"
    }

    void tearDown() {

        User.list().each {
            it.delete(flush:true)
        }
    }
}

