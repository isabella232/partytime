import com.favoritemedium.partytime.domain.*


class MessageServiceTests extends GroovyTestCase {

    def messageService

    def user
    def anotherUser


    void setUp() {
        user = User.findByUsername("tester") ?: new User(username:'tester', password:'123456', email:'test@123.com')
        user.save(flush:true)
        user.errors.allErrors.each { println it }
        
        anotherUser = User.findByUsername("tester") ?: new User(username:'tester', password:'123456', email:'test@123.com')
        anotherUser.save(flush:true)
        anotherUser.errors.allErrors.each { println it }
    }

    void testSendMessage() {
        assertEquals "Should be empty initially",
                0, messageService.getLatestMessage(user)?.size()

        boolean success = messageService.sendMessage(user, anotherUser, 
                                "Test send message", "This is a test send message")
        assertTrue "Should have been send out", success
        assertEquals "Check Message.count()", 1, Message.count()

    }

    void testGetMessage() {

        assertEquals "Should be empty initially",
                0, messageService.getLatestMessage(anotherUser)?.size()
        
        assertEquals "Should be empty initially",
                0, messageService.getAllMessages(anotherUser)?.size()
        
        assertEquals "Should be empty initially",
                0, messageService.getAllMessagesCount(anotherUser)
        
        boolean success = messageService.sendMessage(user, anotherUser, 
                                "Test send message", "This is a test send message")
        assertTrue "Should have been send out", success

        assertEquals "Should be 1 message waiting",
                        1, messageService.getAllMessagesCount(anotherUser)
        
        assertEquals "Should have the latest message now",
                1, messageService.getLatestMessage(anotherUser)?.size()
        
        assertEquals "Should be 1 message waiting",
                        1, messageService.getLatestMessage(anotherUser)?.size()
    }

    void tearDown() {
        User.list().each {
            it.delete(flush:true)
        }

        Message.list().each {
            it.delete(flush:true)
        }
    }

}

