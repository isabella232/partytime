import grails.test.ControllerUnitTestCase
import com.favoritemedium.partytime.domain.*


/**
 * Remember these params:
 *    redirectArgs 
 *    renderArgs 
 * 
 */
class  TribeControllerTests extends ControllerUnitTestCase {

    def controller 
    def user
    def tribe
    def tribeService


    void setUp() {
        super.setUp()
        controller = new TribeController()
        user = new User(username:"test", password:'test123')
        
        tribe = new Tribe(id:1, name:"test", description:"Tester club", creator:user)
        tribeService = mockFor(TribeService)

        mockDomain(User, [user])
        mockDomain(Tribe, [tribe])
        mockDomain(TribeMessage, [])
        controller.metaClass.getSession = { -> ['user':user] }
    }

    /**
     * Test out the login page access with login session.
     */
    void testIndexPage() {
        controller.params.id = 1
        TribeMessage.metaClass.findAllByTribeAndOriginalMessageIsNull = { arg1, arg2 -> [] }
        def model = controller.index()
        assertTrue "Model should include 'tribe' ", model.containsKey('tribe')
        assertTrue "Model should include 'messages' ", model.containsKey('messages')
    }
    
    void testAll() {
        def model = controller.all()
        assertEquals 1, model.tribes?.size()
        assertEquals "test", model.tribes[0]?.name
    }

    void testMyTribes() {
        tribeService.metaClass.tribesForMember = { arg1, arg2 -> [] }
        controller.tribeService = tribeService
        def model = controller.myTribes()
        assertTrue model.containsKey('tribes')
    }

    void testSave() {
        tribeService.metaClass.tribesForMember = { arg1, arg2 -> [] }
        controller.tribeService = tribeService
        def model = controller.myTribes()
        assertTrue "Should have the model tribes in there", model.containsKey("tribes")
    }

    void testApplyMembership() {
        controller.params.id = tribe.id
        controller.applyMembership()
        assertEquals "Should be redirecting to all", "all", redirectArgs.action
    }

    void testApproveMembership() {
        controller.params.id = tribe.id
        controller.params.member = user.id
        controller.approveMembership()
        assertEquals "Should be redirecting to all", "index", redirectArgs.action
    }

    void testRejectMembership() {
        controller.params.id = tribe.id
        controller.params.member = user.id
        controller.rejectMembership()
        assertEquals "Should be rendering to index", "index", renderArgs.view
    }

    void testCreate() {
        controller.params.id = tribe.id
        def model = controller.create()
        assertTrue model.containsKey('member') 
        assertTrue model.containsKey('tribe') 
    }

    void testJoin() {
        controller.params.id = tribe.id
        def model = controller.join()
        assertEquals "Should be rendering to index", "index", renderArgs.view
    }

    void testLeaveTribe() {
        def eventLogService = mockFor(EventLogService)
        eventLogService.metaClass.log = { arg1, arg2, arg3 -> true }
        controller.eventLogService = eventLogService
        
        controller.params.id = tribe.id
        def model = controller.leaveTribe()
        assertEquals "Should be rendering to index", "index", renderArgs.view
    }

    void testMessagesPage(){
        controller.params['msg.id'] = tribe.id
        controller.params.id = tribe.id
        def model = controller.messages()
        
        assertTrue model.containsKey('message') 
        assertTrue model.containsKey('tribe') 
    }

    void testNewMessage(){
        controller.params['msg.id'] = tribe.id
        controller.params.id = tribe.id
        def model = controller.newMessage()
        
        assertTrue model.containsKey('message') 
        assertTrue model.containsKey('tribe') 
    }

    void testReplyMessage(){
        tribeService.metaClass.addMessage = { arg, arg2, arg3, arg4 -> true }
        controller.tribeService = tribeService
        controller.params['msg.id'] = tribe.id
        controller.params.id = tribe.id
        def model = controller.replyMessage()
        
        assertEquals "Should be redirecting to index", "messages", redirectArgs.action
    }

    void testAllMessage(){

        TribeMessage.metaClass.findAllByTribeAndOriginalMessageIsNull= { arg1, arg2 -> [] }
        controller.params['msg.id'] = tribe.id
        controller.params.id = tribe.id
        def model = controller.allMessage()
        
        assertTrue "Should contain messages", model.containsKey('messages') 
        assertTrue "Should contain tribe", model.containsKey('tribe') 
    }
    
    void testSaveMessage(){
        controller.params['msg.id'] = tribe.id
        controller.params.id = tribe.id
        tribeService.metaClass.addMessage = { arg, arg2, arg3, arg4 -> true }
        controller.tribeService = tribeService
        controller.saveMessage()
        
        assertEquals "Should be redirecting to index", "index", redirectArgs.action
    }
    
    void testDeleteMessage(){
        controller.params.id = tribe.id
        def model = controller.deleteMessage()
        
        assertEquals "Should be redirecting to index", "allMessage", redirectArgs.action
    }

}

