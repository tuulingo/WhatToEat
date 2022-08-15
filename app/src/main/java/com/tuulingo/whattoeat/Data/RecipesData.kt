package com.tuulingo.whattoeat.Data

import com.tuulingo.whattoeat.Data.Result

data class RecipesData(
    val number: Int,
    val offset: Int,
    val results: List<Result>,
    val totalResults: Int
)