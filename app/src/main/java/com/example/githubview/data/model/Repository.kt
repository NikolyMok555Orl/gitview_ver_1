package com.example.githubview.data.model

import com.example.githubview.data.model.api.JsRepository


class Repositories{
    var total_count=0
    var items:List<Repository> = emptyList()
}


class Repository {
    var id=0
    var full_name=""
    var description=""
    var languages: List<String> = emptyList()
    var updated_at=""
    var avatar_url=""
    var stargazers_count=0



    constructor(jsRepository: JsRepository, languages: List<String>){
        id=jsRepository.id
        full_name=jsRepository.full_name
        description=jsRepository.description
        updated_at=jsRepository.updated_at
        avatar_url=jsRepository.owner.avatar_url
        stargazers_count=jsRepository.stargazers_count
        this.languages=languages
    }

}