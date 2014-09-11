dataSource {
	pooled = true
	//driverClassName = "org.hsqldb.jdbcDriver"
    driverClassName = "com.mysql.jdbc.Driver"  
	username = "partytime"
	password = "partytime"
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='com.opensymphony.oscache.hibernate.OSCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:hsqldb:file:data/devDB"
            driverClassName = "org.hsqldb.jdbcDriver"
            username = ""
            password = ""
           // url = "jdbc:mysql://localhost/partytime_dev?autoReconnect=true"
           // driverClassName = "com.mysql.jdbc.Driver"  
		}
	}
	test {
		dataSource {
			dbCreate = "create-drop"
			//url = "jdbc:hsqldb:mem:testDb"
            url = "jdbc:mysql://localhost/partytime_test?autoReconnect=true"
		}
	}
	production {
		dataSource {
			dbCreate = "update"
			//url = "jdbc:hsqldb:file:prodDb;shutdown=true"
            url = "jdbc:mysql://localhost/partytime?autoReconnect=true"
		}
	}
}
