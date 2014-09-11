def admin = User.get(1)
println admin


int startIndex = Tribe.count()
if (startIndex > 100) return

int endIndex = startIndex + 100

(startIndex..endIndex).each {

    String name = "Tribe ${it} "
    String description = " Tribe ${it} is for all!!!"

    def t = new Tribe(name:name, description:description, creator:admin)
    t.save()
    t.errors.allErrors.each {
        println "   * ${it} "
    }
    println t

}

println " == ${Tribe.count()} "