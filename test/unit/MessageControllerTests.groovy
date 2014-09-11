import grails.test.ControllerUnitTestCase
import com.favoritemedium.partytime.domain.*
import com.favoritemedium.partytime.service.*


class MessageControllerTests extends ControllerUnitTestCase {

    def user
    def user2
    def messageService
    def tribeService


    void setUp() {
        super.setUp()
        controller = new MessageController()
        user = new User(username:"test", password:'test123')
        user2 = new User(username:"test2", password:'test123')
        mockDomain(User, [user, user2])
        mockDomain(Message, [])
        messageService = mockFor(MessageService)
        tribeService = mockFor(TribeService)
        controller.metaClass.getSession = { -> [user:user] }
        controller.messageService = messageService
        controller.tribeService = tribeService
    }

    void testInbox() {
        controller.messageService.metaClass.getAllMessages = { arg1, arg2 -> [] }
        controller.messageService.metaClass.getAllMessagesCount = { arg1 -> 0 }

        def model = controller.inbox()
        assertEquals model.msgCount, 0
        assertEquals model.messages?.size(), 0
    }

    void testWrite() {
        def model = controller.write()
        assertNotNull model.message
    }

    void testSave() {
        controller.messageService.metaClass.sendMessage = { a1,a2,a3,a4 -> true }
        controller.save()
        assertEquals "inbox", redirectArgs.action
    }

    void testDelete() {
        controller.params.id = 1
        controller.delete()
        assertEquals "inbox", redirectArgs.action
    }
    
    void testRead() {
        controller.params.id = 1
        def model = controller.read()
        println model
        assertTrue model.containsKey('message')
    }

    void tearDown() {
    }

}
