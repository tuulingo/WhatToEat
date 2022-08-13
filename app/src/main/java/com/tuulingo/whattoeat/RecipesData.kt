package com.tuulingo.whattoeat

data class RecipesData(
    val number: Int,
    val offset: Int,
    val results: List<Result>,
    val totalResults: Int
)