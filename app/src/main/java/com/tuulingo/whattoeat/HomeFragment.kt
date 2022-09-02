package com.tuulingo.whattoeat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuulingo.whattoeat.Adapters.RecipeAdapter
import com.tuulingo.whattoeat.Api.ApiInterface
import com.tuulingo.whattoeat.Api.Client
import com.tuulingo.whattoeat.Data.RecipesData
import com.tuulingo.whattoeat.Data.Result
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.system.measureTimeMillis

open class HomeFragment : Fragment() {
    private lateinit var adapter: RecipeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var retrofitBuilder: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {

                retrofitBuilder = Client().getClient()!!.create(ApiInterface::class.java)
                suspend fun getDifferentFoodTypes(
                    offsetAmount: Int,
                    viewId: Int,
                    foodType: String
                ) {

                    val retrofitData = retrofitBuilder.getRecipes(
                        apikey = MainActivity.API_KEY,
                        number = MainActivity.ITEMS_SHOWN,
                        offset = OffsetAmount().offsetAmount(amount = offsetAmount),
                        type = foodType
                    )

                    if (retrofitData.isSuccessful) {
                        val responseBody = retrofitData.body()
                        val recipes = responseBody!!.results

                        recyclerView = requireView().findViewById(viewId)
                        recyclerView.setHasFixedSize(true)
                        recyclerView.layoutManager = LinearLayoutManager(
                            requireActivity().application,
                            RecyclerView.HORIZONTAL,
                            false
                        )
                        adapter = RecipeAdapter(recipes, requireActivity().application)
                        recyclerView.adapter = adapter
                        getMealData(adapter)
                    }
                }
                    val downloadFoodTypes = listOf(
                        async { getDifferentFoodTypes(900, (R.id.main_course_recycler_view), "main-course") },
                        async { getDifferentFoodTypes(199, (R.id.breakfast_recycler_view), "breakfast") },
                        async { getDifferentFoodTypes(241, (R.id.salad_recycler_view), "salad") },
                        async { getDifferentFoodTypes(274, (R.id.dessert_recycler_view), "dessert") },
                        async { getDifferentFoodTypes(61, (R.id.beverage_recycler_view), "beverage") }
                    )
                    downloadFoodTypes.awaitAll()


            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    private fun getMealData(adapter: RecipeAdapter) {
        adapter.setOnItemClickListener(object : RecipeAdapter.onItemClickListener{
            override fun onItemClick(position: Int){
                MainActivity.RECIPE_ID = adapter.foodList[position].id
                val mainActivity = (activity as MainActivity)
                mainActivity.switchToRecipeFragment()
            }
        })
    }
    
}