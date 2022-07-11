package com.example.githubview.data.model.api

class JsRepositories {
    var total_count=0
    var items: List<JsRepository> = emptyList()
}


class JsRepository{
    var id=0
    var full_name=""
    var name=""
    var description=""
    var language=""
    var languages_url=""
    var updated_at=""
    var stargazers_count=0
    var owner=JsOwner()


}