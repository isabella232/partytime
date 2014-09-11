import grails.test.GroovyPagesTestCase
import com.favoritemedium.partytime.domain.*


class MessageTagLibTests extends GroovyPagesTestCase {

    def user

    void setUp() {
        user = new User(username:'tester', password:'123456', email:'test@123.com')
        user.save(flush:true)
        user.errors.allErrors.each { println it }
    }

    void testUnreadStatus() {
        def template = "<message:unreadStatus userId=\"${user.id}\" />"
        
        assertOutputEquals "No new message", template
    }
    
    void testUnread() {
        def template = "<message:unread userId=\"${user.id}\" />"
        
        assertOutputEquals "0", template
    }
    
    void tearDown() {
        User.list().each {
            it.delete(flush:true)
        }
    }

}
