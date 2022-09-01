package com.tuulingo.whattoeat

import android.content.Context
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
import com.tuulingo.whattoeat.databinding.FragmentSpecificFoodRecipeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpecificFoodRecipeFragment : Fragment() {
    private lateinit var retrofitBuilder: ApiInterface
    private lateinit var binding: FragmentSpecificFoodRecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpecificFoodRecipeBinding.inflate(inflater, container, false)
        retrofitBuilder = Client().getClient()!!.create(ApiInterface::class.java)
        getSpecificRecipe()
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
                val responseBody = response.body()!!
                binding.specificFoodName.text = responseBody.title

                Log.e("TAG", "response is $responseBody")
                Toast.makeText(requireActivity().application, "${response.code()}", Toast.LENGTH_SHORT).show()
                Toast.makeText(requireActivity().application, "You just got this id ${MainActivity.RECIPE_ID} with this title ${responseBody.title}", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<SpecificFoodRecipe?>, t: Throwable) {
                Toast.makeText(requireActivity().application, "Error : $t", Toast.LENGTH_LONG).show()
                Log.d("mainactivity", "$t")
            }
        })

    }
}