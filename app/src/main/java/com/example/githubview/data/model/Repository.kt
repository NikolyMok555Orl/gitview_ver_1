package com.example.githubview.data.model

import com.example.githubview.Api
import com.example.githubview.data.model.api.JsRepository
import com.example.githubview.utils.Resource


class Repositories{
    var total_count=0
    var items:List<Repository> = emptyList()

    companion object{

        val per_page=10

    }
}


class Repository {
    var id=0
    var full_name=""
    var nameOwner=""
    var description=""
    var languages: List<String> = emptyList()
    var languages_url: String=""
    var updated_at=""
    var avatar_url=""
    var stargazers_count=0



    constructor(jsRepository: JsRepository){
        id=jsRepository.id
        full_name=jsRepository.full_name
        description=jsRepository.description?:""
        updated_at=jsRepository.updated_at
        avatar_url=jsRepository.owner.avatar_url
        stargazers_count=jsRepository.stargazers_count
        languages_url=jsRepository.languages_url
        nameOwner=jsRepository.owner.login
    }


    suspend fun getLanguages(): String{
       if(languages.isNotEmpty()) return languages.joinToString()
         return try {
           val res=  Resource.success(data = Api.get().getLanguages(url=languages_url).keys.toList())
             languages=res.data?: emptyList()
             languages.joinToString()
        } catch (exception: Exception) {
            "Ошибка при загрузки"
        }
    }
}