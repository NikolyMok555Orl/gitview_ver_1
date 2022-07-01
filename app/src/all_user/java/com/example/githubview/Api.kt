package com.example.githubview.all_user

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface Api {





    companion object {

        private const val URL = "api.github.com/"

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
            api = retrofit.create(Api::class.java)
        }

        fun get(): Api {
            if (api == null)
                createApi()
            return api!!
        }
    }


}