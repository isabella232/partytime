import com.favoritemedium.partytime.domain.*


class EventLogServiceTests extends GroovyTestCase {

    def eventLogService
    def tribeService

    def user
    def anotherUser
    def tribe1
    def tribe2


    void setUp() {
        user = User.findByUsername("tester") ?: new User(username:'tester', password:'123456', email:'test@123.com')
        user.save(flush:true)
        user.errors.allErrors.each { println it }
        
        anotherUser = User.findByUsername("tester2") ?: new User(username:'tester2', password:'123456', email:'test2@123.com')
        anotherUser.save(flush:true)
        anotherUser.errors.allErrors.each { println it }

        def struct = ["mantra":"We test Grails"]
        tribe1 = tribeService.createNewTribe(user, "Test Tribe", "Tester Club", false, struct)
        tribe2 = tribeService.createNewTribe(anotherUser, "Tribe ABC", "Tester Club", false, struct)
    }

    void testNewTribeLog() {
        eventLogService.log(tribe1, "Test log message from tribe ${tribe1}", "Hi, from ${tribe1}")
        eventLogService.log(tribe2, "Test log message from trieb ${tribe2}", "Hi, from ${tribe2}")
        assertEquals "Expecting 2 logged msg", 2, TribeEventLog.count()
    }
   
    void testNewUserLog() {
        eventLogService.log(user, "Test log message from ${user}", "Hi, from ${user}")
        eventLogService.log(anotherUser, "Test log message from ${anotherUser}", "Hi, from ${anotherUser}")
        assertEquals "Expecting 2 logged msg", 2, UserEventLog.count()
    }


    void testSystemLog() {
        eventLogService.log("First test log message from Tweety", "Hi, from Tweety")
        eventLogService.log("Second test log message from Tweety", "Hi, from Tweety again")
        assertEquals "Expecting 2 logged msg", 2, EventLog.count()
    }

    void testCustommTypeLog() {
        eventLogService.log("XXX", "First test log message from Tweety", "Hi, from Tweety", "dongle")
        eventLogService.log("XXX", "Second test log message from Tweety", "Hi, from Tweety again", "dongle")
        assertEquals "Expecting 2 logged msg", 2, EventLog.findAllByType("XXX")?.size()
    }

    void testGetNewsForUser() {
        eventLogService.log(user, "Test log message from ${user}", "Hi, from ${user}")
        eventLogService.log(tribe1, "Test log message from tribe ${tribe1}", "Hi, from ${tribe1}")
        eventLogService.log("First test log message from Tweety", "Hi, from Tweety")
        eventLogService.log("XXX", "First test log message from Tweety", "Hi, from Tweety", "dongle")
        def msgs = eventLogService.getNewsFor(user)

        assertEquals "Expecting 4 logged msg", 4, msgs?.size()
    }

    void tearDown() {
        User.list().each {
            it.delete(flush:true)
        }

        UserEventLog.list().each {
            it.delete(flush:true)
        }

        TribeEventLog.list().each() {
            it.delete(flush:true)
        }

    }

}
