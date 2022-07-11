package com.example.githubview.all_user

import com.example.githubview.Api
import com.example.githubview.all_user.api.JsUser
import com.example.githubview.data.model.api.JsRepositories
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface Api:com.example.githubview.Api {


    @GET("users/{user}")
    suspend fun getUser(@Path("user") user: String): JsUser


    companion object {

        private const val URL = "https://api.github.com/"

        private var api: com.example.githubview.all_user.Api? = null

        private fun createApi() {
            val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            val client : OkHttpClient = OkHttpClient.Builder().apply {
                this.addInterceptor(interceptor)
                this.readTimeout(60, TimeUnit.SECONDS)
                this.connectTimeout(60, TimeUnit.SECONDS)
            }.build()

            val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            api = retrofit.create(com.example.githubview.all_user.Api::class.java)
        }

        fun get(): com.example.githubview.all_user.Api {
            if (api == null)
                createApi()
            return api!!
        }
    }

}