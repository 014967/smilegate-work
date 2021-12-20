package com.example.layoutpractice.model

class User {


    var name: String? = null
    var password: String? = null
    var email: String? = null
    var roles: ArrayList<Role>? = null

    constructor(name: String?, password: String?, email: String?, roles : ArrayList<Role>?) {
        this.name = name
        this.password = password
        this.email = email
        this.roles = roles
    }
}