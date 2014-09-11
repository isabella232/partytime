import grails.test.GroovyPagesTestCase
import com.favoritemedium.partytime.domain.*


class TribeTagLibTests extends GroovyPagesTestCase {

    def user
    def user2
    def tribe
    def tribe2

    def tribeService


    void setUp() {
        user = new User(username:'tester', password:'123456', email:'test@123.com')
        user.save(flush:true)
        user.errors.allErrors.each { println it }
        
        user2 = new User(username:'tester2', password:'123456', email:'test@123.com')
        user2.save(flush:true)
        user2.errors.allErrors.each { println it }
        
        def session = [:]
        session.user = user
        TribeTagLib.metaClass.getSession = { -> session }
        
        def struct = ["mantra":"We test Grails"]
        tribe = tribeService.createNewTribe(user, "Test Tribe", "Tester Club", false, struct)
        tribe2 = tribeService.createNewTribe(user2, "Another Test Tribe", "XX", false, struct)
    }

    void testShortcut() {
        def template = "<tribe:shortcut userId='${user.id}' />"
        assertOutputEquals "<a href=\"/tribe/myTribes/${user.id}\">Tribes</a>", template
    }

    void testControlWithSessionButDifferentTribe() {
        def template = "<tribe:control tribeId='${tribe2.id}'/>"
        assertOutputEquals "<div id='tribe'><a href=\"/tribe/join/${tribe2.id}\">Join this Tribe</a></div>", template
    }

    void testControlWithSession() {
        String expectedOutput = """
        <div id='tribe'><a href="/tribe/create/${tribe2.id}">Edit Tribe Details</a><br/><a href="/tribe/newEvent/${tribe2.id}">Create New Event</a><br/></div>
        """.trim()
        def template = "<tribe:control tribeId='${tribe2.id}'/>"
        assertOutputEquals "<div id='tribe'><a href=\"/tribe/join/${tribe2.id}\">Join this Tribe</a></div>", template

    }

    void testControlWithoutSession() {
        String expectedOutput = """
        <div id='tribe'><a href="/tribe/join/${user2.id}">Join this tribe</a></div>
        """
        def template = "<tribe:control tribeId='${tribe2.id}' />"
        //assertOutputEquals "<a href=\"/tribe/myTribes/${user2.id}\">Tribes</a>", template
        assertOutputEquals "<div id='tribe'><a href=\"/tribe/join/${tribe2.id}\">Join this Tribe</a></div>", template
    }
    
    void testTribeSummaries() {
        def session = [:]
        session.user = null
        TribeTagLib.metaClass.getSession = { -> session }

        def template = "<tribe:tribeSummaries userId='${user2.id}' />"
        assertOutputEquals "", template
    }


    void tearDown() {
        User.list().each {
            it.delete(flush:true)
        }

        Tribe.list().each {
            it.delete(flush:true)
        }
    }

}
