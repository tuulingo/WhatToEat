package com.tuulingo.whattoeat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.R.*
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tuulingo.whattoeat.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getMyData()

    }

    fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

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
    }

}

