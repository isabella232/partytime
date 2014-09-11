import com.favoritemedium.partytime.domain.*


class ConnectionServiceTests extends GroovyTestCase {

    def connectionService
    def user
    def anotherUser


    void setUp() {
        user = User.findByUsername("tester") ?: new User(username:'tester', password:'123456', email:'test@123.com')
        user.save(flush:true)
        user.errors.allErrors.each { println it }
        
        anotherUser = User.findByUsername("tester2") ?: new User(username:'tester2', password:'123456', email:'test2@123.com')
        anotherUser.save(flush:true)
        anotherUser.errors.allErrors.each { println it }
    }
    

    void testIsConnected() {
        assertNotNull "Should not be null", user
        assertNotNull "Should not be null", anotherUser

        assertFalse "Should not be friends yet", connectionService.areFriends(user, anotherUser)
    }

    void testMakeFriends() {
        assertNotNull "Should not be null", user
        assertNotNull "Should not be null", anotherUser

        assertFalse "Should not be friends yet", connectionService.areFriends(user, anotherUser)

        assertNotNull connectionService.makeFriends(user, anotherUser)
        def con = connectionService.getFriendRequests(anotherUser)
        assertEquals "Should be both 1", 1, con?.size()
        assertTrue con[0] == user
        assertEquals con[0], user
    }


    void testConfirmFriends() {
        assertFalse "Should not be friends yet", connectionService.areFriends(user, anotherUser)

        assertNotNull connectionService.makeFriends(user, anotherUser)
        def con = connectionService.getFriendRequests(anotherUser)
        assertEquals "Should be both 1", 1, con?.size()
        assertTrue con[0] == user

        boolean success = connectionService.confirmFriendship(anotherUser, user)
        assertTrue "Should  be confirmed already", success
        assertTrue connectionService.areFriends(user, anotherUser)
    }

    void testRejectFriends() {
        assertFalse "Should not be friends yet", connectionService.areFriends(user, anotherUser)

        assertNotNull connectionService.makeFriends(user, anotherUser)
        def con = connectionService.getFriendRequests(anotherUser)
        assertEquals "Should be both 1", 1, con?.size()
        assertTrue con[0] == user

        boolean success = connectionService.declineFriendship(anotherUser, user)
        assertTrue "Should  be confirmed already", success
        assertFalse connectionService.areFriends(user, anotherUser)
    }


    void testGetFriends() {
        assertFalse "Should not be friends yet", connectionService.areFriends(user, anotherUser)

        assertNotNull connectionService.makeFriends(user, anotherUser)
        def con = connectionService.getFriendRequests(anotherUser)
        assertEquals "Should be both 1", 1, con?.size()
        assertTrue con[0] == user

        boolean success = connectionService.confirmFriendship(anotherUser, user)
        assertTrue "Should  be confirmed already", success
        assertTrue connectionService.areFriends(user, anotherUser)

        def friends = connectionService.getAllFriendsFor(user)

        assertEquals "Should have 1 friends", 1, connectionService.friendsCount(user)
        assertEquals "Should have 1 friends", 1, friends?.size()
        assertTrue "Should be friends now", connectionService.areFriends(user, anotherUser)
    }

    void tearDown() {

        Connection.list().each {
            it.delete(flush:true)
        }


        User.list().each {
            it.delete(flush:true)
        }

    }
    
}
