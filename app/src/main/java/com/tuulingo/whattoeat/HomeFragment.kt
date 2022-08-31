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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        retrofitBuilder = Client().getClient()!!.create(ApiInterface::class.java)
        getMainCourse()
        getBreakfast()
        getSalad()
        getDessert()
        getBeverage()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    private fun getMealData(adapter: RecipeAdapter) {
        adapter.setOnItemClickListener(object : RecipeAdapter.onItemClickListener{
            override fun onItemClick(position: Int){
                MainActivity.RECIPE_ID = adapter.foodList[position].id
                Toast.makeText(requireActivity().application, "${MainActivity.RECIPE_ID} <--- see on sinu iiddd", Toast.LENGTH_SHORT).show()
                val mainActivity = (activity as MainActivity)
                mainActivity.switchToRecipeFragment()
            }
        })
    }

    private fun getDifferentFoodTypes(offsetAmount: Int, viewId: Int){
        val retrofitData = retrofitBuilder.getRecipes(
            apikey = MainActivity.API_KEY,
            number = MainActivity.ITEMS_SHOWN,
            offset = OffsetAmount().offsetAmount(amount = offsetAmount),
            type = "main-course")

        retrofitData.enqueue(object : Callback<RecipesData?> {
            override fun onResponse(call: Call<RecipesData?>, response: Response<RecipesData?>) {
                val responseBody = response.body()
                val recipes = responseBody!!.results

                recyclerView = view!!.findViewById(viewId)
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(requireActivity().application, RecyclerView.HORIZONTAL, false)
                adapter = RecipeAdapter(recipes, requireActivity().application)
                recyclerView.adapter = adapter
                getMealData(adapter)

            }

            override fun onFailure(call: Call<RecipesData?>, t: Throwable) {
                Toast.makeText(requireActivity().application, "$t", Toast.LENGTH_LONG).show()
                Log.d("MainActivity", "tuli error!!"+t.message)
            }
        })
    }

    private fun getMainCourse() {

        getDifferentFoodTypes(900, (R.id.main_course_recycler_view))
    }

    private fun getBreakfast() {

        getDifferentFoodTypes(199, (R.id.breakfast_recycler_view))
    }

    private fun getSalad() {

        getDifferentFoodTypes(241, (R.id.salad_recycler_view))
    }

    private fun getDessert() {

        getDifferentFoodTypes(274, (R.id.dessert_recycler_view))
    }

    private fun getBeverage() {

        getDifferentFoodTypes(61, (R.id.beverage_recycler_view))
    }
    
}