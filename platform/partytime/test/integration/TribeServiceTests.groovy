class TribeServiceTests extends GroovyTestCase {

    def userManagerService
    //User user

    void setUp() {
      //User  user = new User(username:'tester', password:'123456', email:'test@123.com')
      //user.save(flush:true)
      //user.errors.each { println it }
    }

    void testBasicTribeService() {
        User  user = new User(username:'tester', password:'123456', email:'test@123.com')
        user.save(flush:true)
        user.errors.each { println it }
        userManagerService.changePassword(user, '123456', '123457')
    }
}
