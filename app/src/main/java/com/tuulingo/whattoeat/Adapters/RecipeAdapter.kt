package com.tuulingo.whattoeat.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tuulingo.whattoeat.Data.Result
import com.tuulingo.whattoeat.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.load.resource.bitmap.FitCenter



class RecipeAdapter(
    val foodList: List<Result>,
    private val context: Context,) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int){

        }
    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener
    }

    // create new views
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recipes_adapter, viewGroup, false)
        return ViewHolder(view, mListener)

    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val food = foodList[position]
        holder.foodName.text = food.title
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(FitCenter(), RoundedCorners(16))
        Glide.with(context)
            .load(food.image)
            .apply(requestOptions)
            .skipMemoryCache(true)//for caching the image url in case phone is offline
            .placeholder(R.drawable.placeholder)
            .into(holder.foodPicture)


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return foodList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(view: View, listener: onItemClickListener): RecyclerView.ViewHolder(view){
        val foodName: TextView
        val foodPicture: ImageView


        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

            foodName = view.findViewById(R.id.food_textView)
            foodPicture = view.findViewById(R.id.food_imageView)
        }

    }

}