println " Number of users: ${User.count()} "
println " NUmber of tribes: ${Tribe.count()} "

def tribeService = new TribeService()

assert tribeService, "Tribe service must not be null"

User.list().each { u ->
    Tribe.list().each { t ->
        tribeService.addMemberToTribe(t, u)
    }
}

println " == end == "