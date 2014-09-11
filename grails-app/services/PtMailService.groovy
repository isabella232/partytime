import groovy.text.Template;
import groovy.text.SimpleTemplateEngine;
import org.springframework.mail.MailException;
import java.util.Random;
import org.springframework.context.*
import org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib


class PtMailService implements ApplicationContextAware {

    ApplicationContext applicationContext
    boolean transactional = false
    def mailService
    def applicationTagLib = new ApplicationTagLib()

    def serviceMethod() {

    }

    def sendRecoveryEmail(user){
            File tplFile = applicationContext.getResource( File.separator + "WEB-INF" + File.separator + "recoveryEmail.template").getFile();
            user.auth = Long.toHexString((new Random()).nextLong()).toString()
            def link = applicationTagLib.createLink(controller:'login', action:'resetPassword', absolute:true, params:[auth:user.auth,user:user.id])
            def binding = ["user": user,"link":link]
            def template = new SimpleTemplateEngine().createTemplate(tplFile).make(binding)
            def body = template.toString()
            mailService.sendMail{
                to user.email
                html body
            }
            user.save()
    }

}
