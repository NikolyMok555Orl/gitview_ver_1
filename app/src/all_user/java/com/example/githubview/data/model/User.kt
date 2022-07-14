package com.example.githubview.data.model

import com.example.githubview.all_user.api.JsUser

class User {
    var id=0
    var name=""
    var bio=""
    var avatar_url=""
    var blog=""
    var followers=0
    var following=0
    /**Для превью*/
    constructor(){}


    constructor(jsUser: JsUser){
        id=jsUser.id
        name=jsUser.login
        bio=jsUser.bio?:""
        blog=jsUser.blog?:""
        followers=jsUser.followers
        following=jsUser.following
        avatar_url=jsUser.avatar_url
    }
}