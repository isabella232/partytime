
import grails.test.*

class EventLogServiceTests extends GrailsUnitTestCase {

    def mockService
    def david // user
    def tribe 

    void setUp() {
        super.setUp()
        mockService = mockFor(EventLogServiceTests, true)
        david = new User(username:'david', password:'testing', email:'test@mail.com')
        tribe = new Tribe(creator:david, name:"Tester Club", description:"This is testers only club")
    }

    void testCreateNewUserEventLog() {


        mockDomain(UserEventLog)

        def eventLogger = new EventLogService()
        assertTrue eventLogger.log( david, "Testing", "This is a test")
        //def msg = UserEventLog.findByUser( david) 
        def msg = UserEventLog.list(0) 
        assertEquals msg.body, "This is a test" 
        assertEquals msg.body + "1", "This is a test1" 

    }

    void testCreateNewTribeEventLog() {

        mockDomain(TribeEventLog)

        def eventLogger = new EventLogService()
        assertTrue eventLogger.log( tribe, "Testing", "This is a test")
        def tribeMsg = TribeEventLog.findByTribe( tribe )
        assertEquals tribeMsg.body, "This is a test"
    }

}
