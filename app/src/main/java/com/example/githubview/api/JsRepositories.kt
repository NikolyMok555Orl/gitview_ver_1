package com.example.githubview.api

class JsRepositories {
    var total_count=0
    var items: List<JsRepository> = emptyList()
}


class JsRepository{
    var full_name=""
    var name=""
    var description=""
    var language=""
    var languages_url=""
    var updated_at=""
    var stargazers_count=0
    var owner=object {
        var avatar_url=""
    }



}