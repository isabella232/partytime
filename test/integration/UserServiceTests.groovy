import com.favoritemedium.partytime.domain.*


class UserServiceTests extends GroovyTestCase {

    def userManagerService
    User user

    void setUp() {
        user = new User(username:'tester', password:'123456', email:'test@123.com')
        user.save(flush:true)
        user.errors.allErrors.each { println it }
    }

    void testChangePasswordByUser() {
        
        assertTrue  "Password should changed successfully", 
                    userManagerService.changePassword(user, '123456', '123457')
        
        assertFalse  "Password change should fail",
                    userManagerService.changePassword(user, 'x123456', '123457')

        assertTrue  "Password change should pass",
                    userManagerService.changePassword(user, '123457', '12345X')
        
        assertEquals "Password should be the same", "12345X", user.password
    }
    
    void testChangePasswordByUserId() {
        
        assertTrue  "Password should changed successfully", 
                    userManagerService.changePassword(user.id, '123456', '123457')
        
        assertFalse  "Password change should fail",
                    userManagerService.changePassword(user.id, 'x123456', '123457')

        assertTrue  "Password change should pass",
                    userManagerService.changePassword(user.id, '123457', '12345X')
        
        assertEquals "Password should be the same", "12345X", user.password
    }

    void tearDown() {
        user = User.findByUsername("tester")
        user?.delete(flush:true)
    }

}
