package com.tuulingo.whattoeat

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {

    @GET("recipes/complexSearch")
    fun getMainCourse(@Query("apiKey") apikey: String,
                      @Query("number") number: String,
                      @Query("offset") offset: String,
                      @Query("type") type: String)
    : Call<RecipesData>

}