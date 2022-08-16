package com.tuulingo.whattoeat

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView
import com.tuulingo.whattoeat.Adapters.RecipeAdapter
import com.tuulingo.whattoeat.Api.ApiInterface
import com.tuulingo.whattoeat.Api.Client
import com.tuulingo.whattoeat.Data.RecipesData
import com.tuulingo.whattoeat.Data.Result
import com.tuulingo.whattoeat.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecipeAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getMainCourse()
//        getBreakfast()


    }

    private fun init(){

    }

    private fun getMainCourse() {


        val retrofitBuilder = Client().getClient()!!.create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getRecipes(
            apikey = API_KEY,
            number = ITEMS_SHOWN,
            offset = OffsetAmount().offsetAmount(amount = 900),
            type = "main-course")

        retrofitData.enqueue(object : Callback<RecipesData?> {
            override fun onResponse(call: Call<RecipesData?>, response: Response<RecipesData?>) {
                val responseBody = response.body()
                val recipes = responseBody!!.results


                val firstAdapter = RecipeAdapter(recipes, applicationContext)
                recyclerView = findViewById(R.id.main_course_recycler_view)
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
                val firstManager = LinearLayoutManager(
                    applicationContext, LinearLayoutManager.HORIZONTAL, false
                )
                adapter = RecipeAdapter(recipes, applicationContext)
                recyclerView.adapter = adapter

                Log.d("MainActivity", "ei tulnddd error!!")
            }

            override fun onFailure(call: Call<RecipesData?>, t: Throwable) {
                Toast.makeText(applicationContext, "$t", Toast.LENGTH_LONG).show()
                Log.d("MainActivity", "tuli error!!"+t.message)
            }
        })

    }

    /*private fun getBreakfast() {

        val retrofitBuilder = Client().getClient()!!.create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getRecipes(
            apikey = API_KEY,
            number = ITEMS_SHOWN,
            offset = OffsetAmount().offsetAmount(amount = 199),
            type = "breakfast")

        retrofitData.enqueue(object : Callback<RecipesData?> {
            override fun onResponse(call: Call<RecipesData?>, response: Response<RecipesData?>) {
                val responseBody = response.body()


                for (data in responseBody!!.results)
                {
                    myStringBuilder.append(data.title)
                    myStringBuilder.append("\n")
                }

                binding.txtId.text = myStringBuilder

                Log.d("MainActivity", "ei tulnddd error!!")
            }

            override fun onFailure(call: Call<RecipesData?>, t: Throwable) {
                Toast.makeText(applicationContext, "$t", Toast.LENGTH_LONG).show()
                Log.d("MainActivity", "tuli error!!"+t.message)
            }
        })

    }*/

    companion object {
        const val ITEMS_SHOWN = "25"
        const val API_KEY = BuildConfig.apiKey
    }

}

