package com.example.githubview

import com.example.githubview.data.model.Repositories
import com.example.githubview.data.model.Repository
import com.example.githubview.data.model.api.JsRepositories
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

interface Api {


    @GET("search/repositories")
    suspend fun searchRepositories(@Header("Authorization") token :String =Api.token ,
        @Query("q") q: String,
        @Query("per_page") per_page: Int = Repositories.per_page, @Query("page") page: Int
    ): JsRepositories




    @GET
    suspend fun getLanguages(@Header("Authorization") token :String =Api.token ,@Url url: String): Map<String, Int>

    /*@GET("search/repositories")
    fun searchRepositories(@Query("query") q: String): Call<JsRepositories>*/


    companion object {
        private const val token="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@"
        private const val URL = "https://api.github.com/"

        private var api: Api? = null

        fun getUrlGetArrayString(input: List<Int>?): String? {
            input?.let {
                if (it.isEmpty())
                    return null
                else
                    return "${it.toString().replace(" ", "")}"
            }
            return null
        }

        private fun createApi() {
            val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            val client: OkHttpClient = OkHttpClient.Builder().apply {
                this.addInterceptor(interceptor)
                this.readTimeout(60, TimeUnit.SECONDS)
                this.connectTimeout(60, TimeUnit.SECONDS)
            }.build()

            val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            api = retrofit.create(Api::class.java)
        }

        fun get(): Api {
            if (api == null)
                createApi()
            return api!!
        }
    }


}