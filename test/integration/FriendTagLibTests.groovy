import grails.test.GroovyPagesTestCase

import com.favoritemedium.partytime.domain.*


class FriendTagLibTests extends GroovyPagesTestCase {

    def user


    void setUp() {
        user = new User(username:'tester', password:'123456', email:'test@123.com')
        user.save(flush:true)
        user.errors.allErrors.each { println it }
    }

    void testShortcut() {
        def template = "<friend:shortcut userId='${user.id}' />"
        assertOutputEquals "<a href=\"/friend/myFriends\">Friends</a>", template
    }

    void tearDown() {
        User.list().each {
            it.delete(flush:true)
        }
    }
}
