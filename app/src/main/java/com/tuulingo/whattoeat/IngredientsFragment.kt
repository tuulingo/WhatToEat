package com.tuulingo.whattoeat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class IngredientsFragment : Fragment() {
    private lateinit var menu: Menu


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(isVisible){
            menu.findItem(R.id.ingredients).icon = ContextCompat.getDrawable(requireActivity().application, R.drawable.ic_baseline_ingredients_24)
        }

        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }
}