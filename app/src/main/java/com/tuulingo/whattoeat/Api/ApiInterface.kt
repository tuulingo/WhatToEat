package com.tuulingo.whattoeat.Api

import com.tuulingo.whattoeat.Data.RecipesData
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("recipes/complexSearch")
    fun getRecipes(@Query("apiKey") apikey: String,
                      @Query("number") number: String,
                      @Query("offset") offset: String,
                      @Query("type") type: String)
    : Call<RecipesData>

}