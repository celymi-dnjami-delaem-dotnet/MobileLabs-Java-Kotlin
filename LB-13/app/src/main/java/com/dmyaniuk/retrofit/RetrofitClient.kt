package com.dmyaniuk.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private var retrofit: Retrofit? = null
    private val gson: Gson = GsonBuilder().setLenient().create()

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        return retrofit!!
    }
}