import org.codehaus.groovy.grails.commons.ApplicationHolder
// Place your Spring DSL code here
beans = {
    cdnProvider(LocalCDNService)    
    // cdnProvider(AWSCDNService)

    blogService(PebbleService)
    
/* Is this redundant? The config has mail settings
    mailMessage(org.springframework.mail.SimpleMailMessage) {
        from = ApplicationHolder.application.config.mail.message.from
        subject = ApplicationHolder.application.config.mail.message.subject
    }

    mailSender(org.springframework.mail.javamail.JavaMailSenderImpl) {
        host = ApplicationHolder.application.config.mail.smtpServer.host
        port = ApplicationHolder.application.config.mail.smtpServer.port
        username = ApplicationHolder.application.config.mail.smtpServer.username
        password = ApplicationHolder.application.config.mail.smtpServer.password
    }

*/
    /*
    wiser(org.subethamail.wiser.Wiser) { bean ->
    bean.initMethod = 'start'
    bean.destroyMethod = 'stop'
    bean.lazyInit = true
    port = 2500
    }
    mailSender(org.springframework.mail.javamail.JavaMailSenderImpl) {
        host = 'localhost'
        port = 2500
    }
     */
}