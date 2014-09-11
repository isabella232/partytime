class PebbleController {

    def index = { 
        redirect(action:'list')
    }

    def pebbleService


    // Get list of blog entry
    def list = {

        String blogId = 'default'
        String username = "sey"
        String password = "sey123"
        def posts = blogService.recentPosts(blogId, username, password)

        println posts

        [posts:posts]
    }

    def delete = {
        println params

        String username = "sey"
        String password = "sey123"

        if ( blogService.removePost( params.id, username, password) ) {
            flash.message = "Deleted blog post"
        } else {
            flash.message = "Fail to deleted blog post"
        }
        
        redirect(action:list)
    }

    def edit = {
        println params
        
        String username = "sey"
        String password = "sey123"

        def post = blogService.getPost( params.id, username, password)
        if (grailsApplication.config.debug) {
            post.each {
                    println " -- ${it} "
            }
        }
        [post:post]
    }

    def newPost = {
        println params

        println "  --> Posting new blog post"
    }

    def update = {
        println params
        println "UPDATE"
        def data = [:]
        
        data.title = params.title
        data.description = params.description
        data.tags = params.tags

        String username = "sey"
        String password = "sey123"

        if (blogService.editPost( params.id, username, password, data ) ) {
            flash.message = "Blog entry updated"
        } else {
            flash.message = "Update failed"
        }

        redirect(action:edit, params:[id:params.id])
    }

    def save = {
        println params
        println "SAVE"
        def data = [:]
        
        data.title = params.title
        data.description = params.description
        data.tags = params.tags

        String username = "sey"
        String password = "sey123"

        String blogPostId = blogService.newPost('default', username, password, data) 
        if (blogPostId) {
            flash.message = "Blog entry saved"
        } else {
            flash.message = "Update failed"
        }

        redirect(action:edit, params:[id:blogPostId])
    }
}
