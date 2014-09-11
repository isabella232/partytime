import grails.test.GroovyPagesTestCase
import com.favoritemedium.partytime.domain.*


class SocialTagLibTests extends GroovyPagesTestCase {

    def user
    def user2
    def connectionService


    void setUp() {
        user = new User(username:'tester', password:'123456', email:'test@123.com')
        user.save(flush:true)
        user.errors.allErrors.each { println it }
        
        user2 = new User(username:'tester2', password:'123456', email:'test@123.com')
        user2.save(flush:true)
        user2.errors.allErrors.each { println it }
        
        def session = [:]
        session.user = user
        SocialTagLib.metaClass.getSession = { -> session }
    }

    void testAreFriends() {
        def template = "<social:areFriends userId='${user2.id}'>YES</social:areFriends>"
        assertOutputEquals "", template
    }
    
    void testAreFriendsWithSession() {

        connectionService.makeFriends(user, user2)
        connectionService.confirmFriendship(user2, user)

        assertTrue "Should be friends by now", connectionService.areFriends(user, user2)
        
        def template = "<social:areFriends userId='${user2.id}'>YES</social:areFriends>"
        assertOutputEquals "", template
    }


    void testFriendList() {
        connectionService.makeFriends(user, user2)
        connectionService.confirmFriendship(user2, user)

        assertTrue "Should be friends by now", connectionService.areFriends(user, user2)
        
        def template = "<social:friendsList userId='${user.id}' />"
        String expectedoutput = """
        <div id='friends'><ul><li><a href="/profile/index/${user2.id}">${user2}</a> </li></ul><a href="/friend/myFriends">See all <br/></a><a href="/friend/all">Add more friends?</a></div>
        """
        assertOutputEquals expectedoutput?.trim(), template
    }

    void testNewsTag() {
        def template = "<social:news />"
        assertOutputEquals "No news yet", template
    }

    void testIsConnected() {
        def template = "<social:isConnected userId='${user2.id}' />"
        assertOutputEquals "<a href=\"/friend/makeFriend/${user2.id}\">Add as friend</a>", template
    }

    void tearDown() {
        User.list().each {
            it.delete(flush:true)
        }

        TribeMessage.list().each {
            it.delete(flush:true)
        }

        Message.list().each {
            it.delete(flush:true)
        }

        EventLog.list().each {
            it.delete(flush:true)
        }
        
        TribeEventLog.list().each {
            it.delete(flush:true)
        }

        UserEventLog.list().each {
            it.delete(flush:true)
        }
    }

}
