package com.tuulingo.whattoeat

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
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
import kotlinx.coroutines.*
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
        binding = FragmentSpecificFoodRecipeBinding.inflate(inflater, container, false)
        return binding.root;

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                 retrofitBuilder = Client().getClient()!!.create(ApiInterface::class.java)}
                    val retrofitData = retrofitBuilder.getRecipeById(
                        recipeId = MainActivity.RECIPE_ID.toString(),
                        information = MainActivity.INFORMATION,
                        apikey = MainActivity.API_KEY,)


                if (retrofitData.isSuccessful) {
                    val responseBody = retrofitData.body()!!
                    binding.readyInMinutes.text = "Ready in ${responseBody.readyInMinutes} minutes"
                    val descTextView = binding.recipeDescription
                    descTextView.movementMethod = LinkMovementMethod.getInstance()
                    descTextView.text = Html.fromHtml(responseBody.summary)
                    descTextView.setLinkTextColor(Color.CYAN)

                    if(responseBody.sourceUrl != "" || responseBody.sourceUrl != null){
                        binding.recipeSource.text = responseBody.sourceUrl
                    }
                    else{
                        binding.recipeSource.text = responseBody.spoonacularSourceUrl
                    }


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
                    var counter = 0
                    listOfTextViews.forEach {
                        Log.d("it name", "$it")
                        if (listOfAllergens[counter]){
                            it.text = "${it.text} ✔"
                        }
                        else {
                            it.text = "${it.text} ❌"
                        }
                        Log.d("counter", "$counter")
                        counter++

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
                }

            }
        }
    }
