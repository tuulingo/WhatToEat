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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retrofitBuilder = Client().getClient()!!.create(ApiInterface::class.java)
        binding = FragmentSpecificFoodRecipeBinding.inflate(inflater, container, false)
        return binding.root;

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSpecificRecipe()
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
                var listOfAllergens: List<Boolean> =  listOf(
                    responseBody.dairyFree,
                    responseBody.glutenFree,
                    responseBody.vegan,
                    responseBody.vegetarian)
                var listOfTextViews = listOf(
                    binding.dairyFree,
                    binding.glutenFree,
                    binding.vegan,
                    binding.vegetarian)
                listOfAllergens.forEach {
                    for (item in listOfTextViews){
                        if (it){
                            item.text = "jah did itt"
                        }
                        item.text = "tuli false"
                    }
                }
                binding.specificFoodName.text = responseBody.title
                var requestOptions = RequestOptions()
                requestOptions = requestOptions.transforms(FitCenter(), RoundedCorners(16))
                Glide.with(requireActivity().application)
                    .load(responseBody.image)
                    .apply(requestOptions)
                    .skipMemoryCache(true)//for caching the image url in case phone is offline
                    .placeholder(R.drawable.placeholder)
                    .into(binding.specificFoodImageView)

                Log.i("TAG", "respnose code: ${response.code()}")
                Log.i("TAG", "You just got this id ${MainActivity.RECIPE_ID} with this title ${responseBody.title}")
            }

            override fun onFailure(call: Call<SpecificFoodRecipe?>, t: Throwable) {
                Toast.makeText(requireActivity().application, "Error : $t", Toast.LENGTH_LONG).show()
                Log.d("mainactivity", "$t")
            }
        })

    }
}