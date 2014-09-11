
import com.favoritemedium.partytime.domain.*


class BlogController {

    def blogService


    def index = { redirect(action:"list") }

    def list = {
        log.info params

        def user = User.get( params.id ?: session.user.id )

        if ( !user.getAttribute("blog") ) {

            // 1. Init a blog user account
            def data = [:]
            data.username = user.username
            data.name = user.toString()
            data.password = user.password ?: user.identifier
            data.email = user.email
            println blogService.addUser(data)

            // 2. Init the blog instance
            def blogData = [:]
            blogData.name = "${user.toString()}'s Blog"
         //   blogData.blogid = user.username
            def resp = blogService.createNewBlog(user.username, blogData)
            log.info resp
            user.setAttribute("blog", user.username)
        }

        //def blogPosts = blogService.recentPosts('default', 'sey', 'sey123')
        def blogPosts = blogService.recentPosts( user.username, user.username, user.password ?: user.identifier )

        [member:user, blogPosts:blogPosts]
    }

    def show = {
        println params
        
        def user = User.get( params.id ?: session.user.id )
        String blogpostId = params.postid
        String username = user.username
        String password = user.password ?: user.identifier

        def blogPost = blogService.getPost(blogpostId, username, password)

        [member:user, post:blogPost]
    }

    def create = {
        def user = User.get( params.id ?: session.user.id )
        [member:user]
    }

    def edit = {
        println params
        
        def user = User.get( params.id ?: session.user.id )
        String blogpostId = params.postid
        String username = user.username
        String password = user.password ?: user.identifier

        def blogPost = blogService.getPost(blogpostId, username, password)

        [member:user, post:blogPost]
    }

    def save = {
        println params

        def user = User.get( params.id ?: session.user.id )
        def data = [:]
        
        data.title = params.title
        data.description = params.description
        data.tags = params.tags

        String username = user.username
        String password = user.password ?: user.identifier

        String blogPostId = blogService.newPost( username, username, password, data) 

        if (blogPostId) {
            flash.message = "Blog entry saved"
        } else {
            flash.message = "Update failed"
        }

        redirect(action:edit, params:[postid:blogPostId])
    }

    def update = {
        println params

        def user = User.get( params.id ?: session.user.id )
        def data = [:]
        
        data.title = params.title
        data.description = params.description
        data.tags = params.tags

        String username = user.username
        String password = user.password ?: user.identifier

        String blogPostId = params.postid
        blogService.editPost(blogPostId, username, password, data) 

        if (blogPostId) {
            flash.message = "Blog entry updated"
        } else {
            flash.message = "Update failed"
        }

        redirect(action:edit, params:[postid:blogPostId])
    }

    def delete = {
        println params
        String blogPostId = params.postid
        def user = User.get( params.id ?: session.user.id )
        String username = user.username
        String password = user.password ?: user.identifier

        blogService.removePost(blogPostId, username, password) 
        flash.message = "Deleted blog post"

        redirect(action:"list") 
    }

}
