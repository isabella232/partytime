import grails.test.ControllerUnitTestCase
import com.favoritemedium.partytime.domain.*
import com.favoritemedium.partytime.service.*


class ProfileControllerTests extends ControllerUnitTestCase {

    def user
    def user2
    def connectionService
    def openidService


    void setUp() {
        super.setUp()
        controller = new ProfileController()
        user = new User(username:"test", password:'test123')
        user2 = new User(username:"test2", password:'test123')
        mockDomain(User, [user, user2])
        mockDomain(Profile, [])
        mockDomain(ProfileAttribute, [])
        Profile.metaClass.addToAttributes = { arg -> true }
        
        connectionService = mockFor(ConnectionService)
        openidService = mockFor(OpenidService)

        controller.metaClass.getSession = { -> [user:user] }
        controller.metaClass.
        controller.connectionService = connectionService
        controller.openidService = openidService
    }

    void testIndex() {
        controller.connectionService.metaClass.getFriendsFor = { arg -> user2 }

        def model = controller.index()
        assertTrue model.containsKey('friends')
        assertTrue model.containsKey('member')
        assertTrue model.containsKey('tribes')
    }

    void testEdit() {
        def model = controller.edit()
        assertTrue model.containsKey('member')
    }

    void testSave() {
        user.profile.metaClass.addToAttributes = { arg -> true }
        user.profile.metaClass.save = { -> true }
        controller.save()
        assertEquals "edit", redirectArgs.action
    }
    
    void testPassword() {
        def model = controller.password()
        assertTrue model.containsKey('member')
    }

    void testRegisterOpenId() {
        controller.openidService.metaClass.getIdentifier = { arg -> 1 }
        controller.registerOpenId()
        assertEquals "password", redirectArgs.action
    }
    

    void tearDown() {
    }

}
