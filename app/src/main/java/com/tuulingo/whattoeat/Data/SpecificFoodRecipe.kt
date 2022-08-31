package com.tuulingo.whattoeat.Data

data class SpecificFoodRecipe(
    val dairyFree: Boolean,
    val glutenFree: Boolean,
    val id: Int,
    val image: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val spoonacularSourceUrl: String,
    val summary: String,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean
)