package com.example.layoutpractice.model

data class Token (
    var access_token : String,
    var refresh_token : String,
    var roles : Array<String>?
    )

