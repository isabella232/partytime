import grails.test.ControllerUnitTestCase
import com.favoritemedium.partytime.domain.*
import com.favoritemedium.partytime.service.*


class FriendControllerTests extends ControllerUnitTestCase {

    def user
    def user2
    def connectionService

    void setUp() {
        super.setUp()
        controller = new FriendController()
        user = new User(username:"test", password:'test123')
        user2 = new User(username:"test2", password:'test123')
        mockDomain(User, [user, user2])
        connectionService = mockFor(ConnectionService)
        controller.connectionService = connectionService
    }

    void testAllPage() {
        controller.session['user'] = user
        def model = controller.all()
        assertEquals "Should be members = model",
                    model.members.size(), 1 
    }

    void testMyFriends() {
        controller.session['user'] = user
        connectionService.metaClass.getAllFriendsFor = { arg1, arg2 -> [] }
        connectionService.metaClass.friendsCount= { arg1  -> 0 }

        def model = controller.myFriends()
        assertEquals "Should be members = model", model.friends.size(), 0 
        assertEquals "Should be members = model", model.totalCount, 0 
    }

    void testBreakFriend() {
        controller.session['user'] = user
        connectionService.metaClass.getConnection = { arg1, arg2 -> [] }

        controller.params.id = user2.id

        controller.breakFriend()
        assertEquals "Expect to display GSP with model containing the user object", 
                        "myFriends", redirectArgs.action
    }

    void testMakeFriend() {
        controller.session['user'] = user
        connectionService.metaClass.makeFriends = { arg1, arg2 -> [] }

        controller.params.id = user2.id

        controller.makeFriend()
        assertEquals "Expect to display GSP with model containing the user object", 
                        "myFriends", redirectArgs.action
    }

    void testConfirmFriend() {
        controller.session['user'] = user
        connectionService.metaClass.getConnection = { arg1, arg2 -> [] }

        controller.params.id = user2.id

        controller.makeFriend()
        assertEquals "Expect to display GSP with model containing the user object", 
                        "myFriends", redirectArgs.action
    }

    void testRejectFriend() {
        controller.session['user'] = user
        connectionService.metaClass.getConnection = { arg1, arg2 -> [] }
        connectionService.metaClass.declineFriendship = { arg1, arg2 -> [] }

        controller.params.id = user2.id

        controller.rejectFriend ()
        assertEquals "Expect to display GSP with model containing the user object", 
                        "myFriends", redirectArgs.action
    }

    void tearDown() {
    }

}
