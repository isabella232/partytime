package com.favoritemedium.partytime.domain

/**
 *
 */
class PostItem {
    String name
    String value
    String type = "NA"
    String description

    Date dateCreated
    Date lastUpdated

    static constraints = {
        description(nullable:true, blank:true)
        value(size:0..5000)
    }

}
