package com.tuulingo.whattoeat.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Client {
    val BASE_URL = "https://api.spoonacular.com/"
    var retrofit: Retrofit? = null


    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}