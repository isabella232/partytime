class SecurityFilters {

    def filters = {

       // loginCheck( controller:"\\b(?!(public|profile|login|test)\\b)\\w+", actions:'*' ) {
        loginCheck( controller:'*', actions:'*' ) {
            before = {
                if(!session.user && !session.openidIdentifier && !actionName.equals('login') 
                    && !(controllerName.equals('login') || controllerName.equalsIgnoreCase('openId')
            		|| controllerName.equalsIgnoreCase('yahoo') || controllerName.equalsIgnoreCase('oauth') )) {
                    redirect(controller:'login')
                    return false
                }
            }
        }
    }

}
