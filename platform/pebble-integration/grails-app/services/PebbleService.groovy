import groovy.net.xmlrpc.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder


class PebbleService {

    boolean transactional = true
    static final String DEFAULT_URL = "http://localhost:8080/pebble/xmlrpc"
    
    def server
    def config = ConfigurationHolder.config

    String adminUsername
    String adminPassword


    def PebbleService() {
        this.adminUsername = config.pebble.admin.username ?: 'sey'
        this.adminPassword = config.pebble.admin.password ?: 'sey123'
    }
    
    /**
     * Get the blog post with the given blogPostId.
     * @param username
     * @param password
     */
    def getPost( blogPostId, username, password ) {
        def metaWeblog = getServer().metaWeblog
        def post = metaWeblog.getPost( blogPostId, username, password )
        post.tags = post.tags ?: [] 
        return post
    }
    
    /**
     * Get all the recent post for the given blogId.
     * 
     * @param username
     * @param password
     * @param msgCount the number of post to get, by default get all
     */
    def recentPosts(blogId, username, password, msgCount=100000) {
        def metaWeblog = getServer().metaWeblog
        def posts = []
        
        try {
            posts = metaWeblog.getRecentPosts(blogId, username, password, msgCount)
        } catch(e) {
            log.error(e)
        }

        return posts
    }

    /**
     * Updates the blog post with new edit.
     *
     * @param username
     * @param password
     * @param data new updates
     */
    def editPost(blogPostId, username, password, data ) {
        def metaWeblog = getServer().metaWeblog

        return metaWeblog.editPost( blogPostId, username, password, data, true ) 
    }

    /**
     * Post new blog entry.
     *
     * @param username
     * @param password
     * @param data blog post 
     */
    def newPost(blogId, username, password, data) {
        def metaWeblog = getServer().metaWeblog
        String blogPostId = metaWeblog.newPost( blogId, username, password, data, true ) 
        return blogPostId
    }

    /**
     * Remove the blog post with the given blogpostId.
     *
     * @param blogpostId 
     * @param username
     * @param password
     */
    def removePost(blogPostId, username, password) {
        def blogger = getServer().blogger
        return blogger.deletePost( 'xxx', blogPostId, username, password, false )
    }


    /**
     * Add new user.
     *
     * @param data new user struct data
     */
    def addUser(data) {
        def pebble = getServer().pebble
        //def data = [:]
        //data.username = "tester1"
        //data.name="Tester ABC 1"
        //data.password = "tester1"
        //data.email = "tester1"
        //data.website = ""
        //data.profile = ""
        if (!data.role)
            data.role = "ROLE_BLOG_OWNER, ROLE_BLOG_PUBLISHER, ROLE_BLOG_CONTRIBUTOR"

        def returnMsg 
        try {
            returnMsg = pebble.addUser(adminUsername, adminPassword, data)
        } catch(ConnectException e) {
            returnMsg = ["success":false, "message":"Failed to connect: ${e}", "disconnected":true]
        } catch(e) {
            returnMsg = ["success":false, "message":"Error occured while trying to add new user: ${e}"]
        }
        return returnMsg
    }

    /*
     * Create new blog for a user.
     *
     * @param data blog post 
     */
    def createNewBlog(ownerUsername, data=[:]) {
        server = getServer()
        def pebble = server.pebble

        def returnMsg 
        try {
            returnMsg = pebble.addBlog(ownerUsername, ownerUsername, data)
        } catch(ConnectException e) {
            returnMsg = ["success":false, "message":"Failed to connect: ${e}"]
        } catch(e) {
            returnMsg = ["success":false, "message":"Error occured while trying to create new blog: ${e}"]
        }
        return returnMsg
    }

    /**
     * Close down blog with the given blogId.
     *
     * @param data blog post 
     */
    def closeBlog(blogId) {
        server = getServer()
        def pebble = server.pebble
        log.warn("NOT IMPLEMENTED YET")
    }

    private getServer() {
        if (!server) {
            String xmlRpcUrl = config.blog.xmlrpc.url ?: DEFAULT_URL
            server = new XMLRPCServerProxy(xmlRpcUrl)
        }
        return server
    }
}

