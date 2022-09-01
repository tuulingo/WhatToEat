package com.tuulingo.whattoeat.Api

import com.tuulingo.whattoeat.Data.RecipesData
import com.tuulingo.whattoeat.Data.SpecificFoodRecipe
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("recipes/complexSearch")
    suspend fun getRecipes(@Query("apiKey") apikey: String,
                      @Query("number") number: String,
                      @Query("offset") offset: String,
                      @Query("type") type: String)
    : Response<RecipesData>

    @Headers("Accept: application/json")
    @GET("recipes/{recipeId}/{information}")
    fun getRecipeById(@Path("recipeId") recipeId: String,
                      @Path("information") information: String,
                      @Query("apiKey") apikey: String,)
    : Call<SpecificFoodRecipe>

}