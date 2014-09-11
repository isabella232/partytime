import grails.test.GroovyPagesTestCase
import com.favoritemedium.partytime.domain.*


class ProfileTagLibTests extends GroovyPagesTestCase {

    def user
    def user2

    void setUp() {
        user = new User(username:'tester', password:'123456', email:'test@123.com')
        user.save(flush:true)
        user.errors.allErrors.each { println it }
        
        user2 = new User(username:'tester2', password:'123456', email:'test@123.com')
        def profile = new Profile()
        user2.profile = profile
        profile.user = user2
        profile.save()
        user2.save(flush:true)
        user2.errors.allErrors.each { println it }
        user2.profile.setAttribute("photo_file_name", "photo123.jpg");
       
        def session = [:]
        session.user = user
        ProfileTagLib.metaClass.getSession = { -> session }
    }

    void testIfISOwnProfile() {
        def template = "<profile:ifOwnProfile userId='${user.id}'>YES</profile:ifOwnProfile>"
        assertOutputEquals "YES", template
    }
    
    void testIfOwnProfile() {
        def template = "<profile:ifOwnProfile userId='${user2.id}'>YES</profile:ifOwnProfile>"
        assertOutputEquals "", template
    }

    void testThumbnail() {
        def template = "<profile:thumbnail userId='${user.id}' />"
        assertOutputEquals "<div id='profile_photo'><img src='/images/thumb.jpg' /></div>", template
    }
    
    //XXX: Will fail now with the new file check as no actual file on path
    void testProfileThumbnail() {
        def template = "<profile:thumbnail userId='${user2.id}' />"
        assertOutputEquals "<div id='profile_photo'><img src='/profile/viewPhoto?path=${user2.username}&name=thumb.jpg' title='${user2.username}'/></div>", template
    }
    
    void testPhoto() {
        def template = "<profile:photo userId='${user.id}' />"
        assertOutputEquals "<div id='profile_photo'><img src='/images/profile.jpg' /></div>", template
    }

    //XXX: Will fail now with the new file check as no actual file on path
    void testProfilePhoto() {
        def template = "<profile:photo userId='${user2.id}' />"
        assertOutputEquals "<div id='profile_photo'><img src='/profile/viewPhoto?path=${user2.username}&name=profile.jpg' title='${user2.username}'/></div>", template
    }

    void testInfoTag() {
        def template = "<profile:info userId='${user2.id}' />"
        String expectedOutput = """
        <div id='profile'>${user2} Profile<br/><div id='profile_photo'><img src='/profile/viewPhoto?path=${user2.username}&name=profile.jpg' /></div><a href="/friend/makeFriend/${user2.id}" Class="profile_link">Add as Friend</a><ul><li><span class='value'>${user2}</span></li><li><span class='field'>Current Location:</span></li><li><span>Self description:</span></li></ul></div>
        """
        assertOutputEquals expectedOutput.trim(), template
    }


    void tearDown() {
        User.list().each {
            it.delete(flush:true)
        }
    }
    
    void testAssertTotearDown() { }

}

