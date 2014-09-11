
int startIndex = User.count() ?: 1
int endIndex = startIndex + 100

if (User.count() > 100) return //Limit test data

(startIndex..endIndex).each {

    String username = "User${it}"
    String password = username
    String givenName = "Test User"
    String familyName = it
    String email = "${username}@test.com"

    def u = new User(username:username, password:password, email:email, givenName:givenName, familyName:familyName)

    println u.email
    u.save(flush:true)
    u.errors.allErrors.each {
        println "   * ${it} "
    }
}


println " Users: ${User.count()}"