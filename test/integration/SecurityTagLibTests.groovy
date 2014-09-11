import grails.test.GroovyPagesTestCase
import com.favoritemedium.partytime.domain.*


class SecurityTagLibTests extends GroovyPagesTestCase {
    
    def user
     
    void setUp() {
        user = User.findByUsername('tester') ?: new User(username:'tester', password:'123456', email:'test@123.com')
        user.save(flush:true)
        user.errors.allErrors.each { println it }
        def adminGroup = new UserGroup(name:"Administrator", description:"Administrator system group")
        adminGroup.save(flush:true)
        adminGroup.addToMembers(user)
        adminGroup.save(flush:true)
        
        def session = [:]
        session.user = user
        SecurityTagLib.metaClass.getSession = { -> session }
    }

    void testIsLoggedIn() {
        def template = "<security:isLoggedIn>YES</security:isLoggedIn>"
        assertOutputEquals "YES", template
    }
    
    void testIfLoggedIn() {
        def template = "<security:ifLoggedIn>YES</security:ifLoggedIn>"
        assertOutputEquals "YES", template
    }
    
    void testIfAdmin() {
        def template = "<security:ifAdmin>YES</security:ifAdmin>"
        assertOutputEquals "YES", template
    }

    void tearDown() {
        
        UserGroup.list().each {
            it.delete(flush:true)
        }

        User.list().each {
            it.delete(flush:true)
        }
    }

}

