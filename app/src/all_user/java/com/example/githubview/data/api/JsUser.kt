package com.example.githubview.all_user.api



data class JsUser(
    var id: Int=0,
    var login: String="",
    var bio: String? ="",
    var avatar_url: String="",
    var blog: String?="",
    var followers:Int=0,
    var following: Int=0



    )