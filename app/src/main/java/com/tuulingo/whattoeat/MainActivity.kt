package com.tuulingo.whattoeat

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
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
    private lateinit var bottomNav : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNav.itemIconTintList = null

        // Ingredients for dev because home makes api calls
        replaceFragment(IngredientsFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.ingredients -> replaceFragment(IngredientsFragment())
                R.id.search -> replaceFragment(SearchFragment())
                R.id.favorites -> replaceFragment(FavoritesFragment())
                R.id.profile -> replaceFragment(ProfileFragment())

                else ->{

                }
            }
            true
        }


    }

    fun switchToRecipeFragment(){
        if(RECIPE_ID != 0){
            replaceFragment(SpecificFoodRecipeFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.bottomnav_frame_layout, fragment)
        // disabled because icon selector doesn't keep up
        /*fragmentTransaction.addToBackStack("tag")*/
        fragmentTransaction.commit()
    }


    companion object {
        const val ITEMS_SHOWN = "1"
        const val API_KEY = BuildConfig.apiKey
        var RECIPE_ID = 0
        const val INFORMATION = "information"
    }

}

