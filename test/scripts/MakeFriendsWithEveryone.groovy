def messageService = new MessageService()
assert messageService

def connService = new ConnectionService()
connService.messageService = messageService
assert connService

User.list().each { me ->

    User.list().each { other ->

        if ( !connService.areFriends(me, other) ) {
            println " ${me} --X--> ${other}"
            connService.makeFriends(me, other)
        }

    }

    
}
println " == end == "
