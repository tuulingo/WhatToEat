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

class HomeFragment : Fragment() {
    private lateinit var adapter: RecipeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var retrofitBuilder: ApiInterface
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if(isVisible){
            menu.findItem(R.id.home).icon = ContextCompat.getDrawable(requireActivity().application, R.drawable.ic_baseline_home_24)
        }

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
            override fun onItemClick(position: Int) {
                Toast.makeText(requireActivity().application, "Clicked on ${adapter.foodList[position].id}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getMainCourse() {

        val retrofitData = retrofitBuilder.getRecipes(
            apikey = MainActivity.API_KEY,
            number = MainActivity.ITEMS_SHOWN,
            offset = OffsetAmount().offsetAmount(amount = 900),
            type = "main-course")

        retrofitData.enqueue(object : Callback<RecipesData?> {
            override fun onResponse(call: Call<RecipesData?>, response: Response<RecipesData?>) {
                val responseBody = response.body()
                val recipes = responseBody!!.results

                recyclerView = view!!.findViewById(R.id.main_course_recycler_view)
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

    private fun getBreakfast() {

        val retrofitData = retrofitBuilder.getRecipes(
            apikey = MainActivity.API_KEY,
            number = MainActivity.ITEMS_SHOWN,
            offset = OffsetAmount().offsetAmount(amount = 199),
            type = "breakfast")

        retrofitData.enqueue(object : Callback<RecipesData?> {
            override fun onResponse(call: Call<RecipesData?>, response: Response<RecipesData?>) {
                val responseBody = response.body()
                val recipes = responseBody!!.results

                recyclerView = view!!.findViewById(R.id.breakfast_recycler_view)
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(requireActivity().application, RecyclerView.HORIZONTAL, false)
                adapter = RecipeAdapter(recipes, requireActivity().application)
                recyclerView.adapter = adapter
                getMealData(adapter)

            }

            override fun onFailure(call: Call<RecipesData?>, t: Throwable) {
                Toast.makeText(requireActivity().application, "$t", Toast.LENGTH_LONG).show()
                Log.d("MainActivity", "ERROR"+t.message)
            }
        })

    }

    private fun getSalad() {

        val retrofitData = retrofitBuilder.getRecipes(
            apikey = MainActivity.API_KEY,
            number = MainActivity.ITEMS_SHOWN,
            offset = OffsetAmount().offsetAmount(amount = 241),
            type = "salad")

        retrofitData.enqueue(object : Callback<RecipesData?> {
            override fun onResponse(call: Call<RecipesData?>, response: Response<RecipesData?>) {
                val responseBody = response.body()
                val recipes = responseBody!!.results

                recyclerView = view!!.findViewById(R.id.salad_recycler_view)
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(requireActivity().application, RecyclerView.HORIZONTAL, false)
                adapter = RecipeAdapter(recipes, requireActivity().application)
                recyclerView.adapter = adapter
                getMealData(adapter)

            }

            override fun onFailure(call: Call<RecipesData?>, t: Throwable) {
                Toast.makeText(requireActivity().application, "$t", Toast.LENGTH_LONG).show()
                Log.d("MainActivity", "ERROR"+t.message)
            }
        })

    }

    private fun getDessert() {

        val retrofitData = retrofitBuilder.getRecipes(
            apikey = MainActivity.API_KEY,
            number = MainActivity.ITEMS_SHOWN,
            offset = OffsetAmount().offsetAmount(amount = 274),
            type = "dessert")

        retrofitData.enqueue(object : Callback<RecipesData?> {
            override fun onResponse(call: Call<RecipesData?>, response: Response<RecipesData?>) {
                val responseBody = response.body()
                val recipes = responseBody!!.results

                recyclerView = view!!.findViewById(R.id.dessert_recycler_view)
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(requireActivity().application, RecyclerView.HORIZONTAL, false)
                adapter = RecipeAdapter(recipes, requireActivity().application)
                recyclerView.adapter = adapter
                getMealData(adapter)

            }

            override fun onFailure(call: Call<RecipesData?>, t: Throwable) {
                Toast.makeText(requireActivity().application, "$t", Toast.LENGTH_LONG).show()
                Log.d("MainActivity", "ERROR"+t.message)
            }
        })

    }

    private fun getBeverage() {

        val retrofitData = retrofitBuilder.getRecipes(
            apikey = MainActivity.API_KEY,
            number = MainActivity.ITEMS_SHOWN,
            offset = OffsetAmount().offsetAmount(amount = 61),
            type = "beverage")

        retrofitData.enqueue(object : Callback<RecipesData?> {
            override fun onResponse(call: Call<RecipesData?>, response: Response<RecipesData?>) {
                val responseBody = response.body()
                val recipes = responseBody!!.results

                recyclerView = view!!.findViewById(R.id.beverage_recycler_view)
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(requireActivity().application, RecyclerView.HORIZONTAL, false)
                adapter = RecipeAdapter(recipes, requireActivity().application)
                recyclerView.adapter = adapter
                getMealData(adapter)

            }

            override fun onFailure(call: Call<RecipesData?>, t: Throwable) {
                Toast.makeText(requireActivity().application, "$t", Toast.LENGTH_LONG).show()
                Log.d("MainActivity", "ERROR"+t.message)
            }
        })

    }

}