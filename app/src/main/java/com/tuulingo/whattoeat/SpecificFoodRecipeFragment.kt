package com.tuulingo.whattoeat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuulingo.whattoeat.Adapters.RecipeAdapter
import com.tuulingo.whattoeat.Api.ApiInterface
import com.tuulingo.whattoeat.Api.Client
import com.tuulingo.whattoeat.Data.RecipesData
import com.tuulingo.whattoeat.Data.SpecificFoodRecipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpecificFoodRecipeFragment : Fragment() {
    private lateinit var retrofitBuilder: ApiInterface


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retrofitBuilder = Client().getClient()!!.create(ApiInterface::class.java)
        Toast.makeText(requireActivity().application, "${MainActivity.RECIPE_ID}", Toast.LENGTH_SHORT).show()
        /*getSpecificRecipe()*/
        return inflater.inflate(R.layout.fragment_specific_food_recipe, container, false)

    }

    private fun getSpecificRecipe(){
        val retrofitData = retrofitBuilder.getRecipeById(
            recipeId = MainActivity.RECIPE_ID.toString(),
            information = MainActivity.INFORMATION,
            apikey = MainActivity.API_KEY,)

        retrofitData.enqueue(object : Callback<SpecificFoodRecipe?> {
            override fun onResponse(
                call: Call<SpecificFoodRecipe?>,
                response: Response<SpecificFoodRecipe?>
            ) {
                val responseBody = response.body()
            }

            override fun onFailure(call: Call<SpecificFoodRecipe?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }
}