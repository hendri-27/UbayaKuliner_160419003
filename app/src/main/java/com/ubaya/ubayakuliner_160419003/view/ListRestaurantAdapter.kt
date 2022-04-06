package com.ubaya.ubayakuliner_160419003.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.ubayakuliner_160419003.R
import com.ubaya.ubayakuliner_160419003.model.Restaurant
import com.ubaya.ubayakuliner_160419003.util.loadImage
import kotlinx.android.synthetic.main.restaurant_list_item.view.*

class ListRestaurantAdapter(val listRestaurant:ArrayList<Restaurant>) : RecyclerView.Adapter<ListRestaurantAdapter.RestaurantViewHolder>() {
    class RestaurantViewHolder(var view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.restaurant_list_item, parent, false)

        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = listRestaurant[position]
        with(holder.view){
            textRestoName.text = restaurant.name
            textRatingTotal.text = (restaurant.ratingTotal ?: "New").toString()
            this.setOnClickListener {
                val action = ListRestaurantFragmentDirections.actionDetailRestaurant(restaurant)
                Navigation.findNavController(it).navigate(action)
            }
            imageResto.loadImage("https://hendri-27.github.io/ubayakuliner_db/images"+restaurant.photoURL,progressLoadingRestoPhoto)
        }
    }

    override fun getItemCount() = listRestaurant.size

    fun updateListRestaurant(newListRestaurant: ArrayList<Restaurant>){
        listRestaurant.clear()
        listRestaurant.addAll(newListRestaurant)
        notifyDataSetChanged()
    }
}