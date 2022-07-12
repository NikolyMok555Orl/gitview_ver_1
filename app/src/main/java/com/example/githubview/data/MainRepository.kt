package com.example.githubview.data

import com.example.githubview.Api
import com.example.githubview.data.model.Repositories
import com.example.githubview.data.model.Repository

open class MainRepository {


    suspend fun getRepositories(q: String, page: Int): Repositories {
        val allRepositories= Api.get().searchRepositories(q=q, page=page)
        val repositories=Repositories().apply {total_count=allRepositories.total_count }
        repositories.items=allRepositories.items.map { Repository(it) }.toList()

        return repositories
    }

}