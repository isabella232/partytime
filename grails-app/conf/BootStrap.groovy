import org.springframework.context.ApplicationContext
import org.codehaus.groovy.grails.commons.ApplicationHolder 
import org.codehaus.groovy.grails.commons.ConfigurationHolder as C
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import com.favoritemedium.partytime.domain.*


class BootStrap {

   def ApplicationContext ctx
    
     def init = { servletContext ->
        ctx = servletContext.getAttribute(
                                GrailsApplicationAttributes.APPLICATION_CONTEXT)
        // Needed to track uptime
        servletContext.setAttribute("startTime", new Date())

        def filePath = File.separator + "WEB-INF/REVISION"
        def revisionNumber = servletContext.getResourceAsStream(filePath)?.text ?: "0"
        servletContext.setAttribute("REVISION", revisionNumber)
        
//        def serverURL = C.config.grails.serverURL.toString()
//        if(!serverURL.endsWith('/')){
//        	serverURL += '/'
//        }
        
//        println serverURL
//        println C.config.grails
     }

     def destroy = {
     }
} 
