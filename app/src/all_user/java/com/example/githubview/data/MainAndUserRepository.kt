package com.example.githubview.data

import com.example.githubview.all_user.Api
import com.example.githubview.data.model.User

class MainAndUserRepository: MainRepository() {

    suspend fun getNewUser(user: String): User {
        val jsUser= Api.get().getUser(user)
        return User(jsUser)
    }
}