package com.tuulingo.whattoeat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.tuulingo.whattoeat.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getMainCourse()


    }

    private fun getMainCourse() {

        var offsetAmount = OffsetAmount().offsetAmount(amount = 900)
        Toast.makeText(applicationContext, "${offsetAmount}", Toast.LENGTH_LONG).show()

        val retrofitData = retrofitBuilder.getMainCourse(
            number = ITEMS_SHOWN,
            offset = offsetAmount,
            type = "main-course")

        retrofitData.enqueue(object : Callback<RecipesData?> {
            override fun onResponse(call: Call<RecipesData?>, response: Response<RecipesData?>) {
                val responseBody = response.body()

                val myStringBuilder = StringBuilder()
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

    }

    companion object {
        const val BASE_URL = "https://api.spoonacular.com/"
        const val ITEMS_SHOWN = "25"
    }

}

