package com.ubaya.ubayakuliner_160419003.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.ubayakuliner_160419003.R
import com.ubaya.ubayakuliner_160419003.viewmodel.DetailRestaurantViewModel
import kotlinx.android.synthetic.main.fragment_detail_restaurant.*

/**
 * A simple [Fragment] subclass.
 * Use the [DetailRestaurantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailRestaurantFragment : Fragment() {
    private lateinit var viewModel:DetailRestaurantViewModel
    private val foodListAdapter = ListFoodAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_restaurant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(DetailRestaurantViewModel::class.java)

        val restaurant = DetailRestaurantFragmentArgs.fromBundle(requireArguments()).restaurant
        viewModel.fetch(restaurant.id.toString())

        textDetailRestoName.text = restaurant.name
        textDetailRestoAddress.text = restaurant.address
        textDetailRestoPhone.text = restaurant.phoneNumber
        ratingBarReview.rating = restaurant.ratingTotal ?: 0.0f
        textDetailRestoRating.text = "${restaurant.ratingTotal ?: "New"} (See Reviews)"

        cardReview.setOnClickListener {
            val action = DetailRestaurantFragmentDirections.actionListReviewFragment(restaurant.id.toString())
            Navigation.findNavController(it).navigate(action)
        }
        buttonCart.setOnClickListener {
            val action = DetailRestaurantFragmentDirections.actionItemCart()
            Navigation.findNavController(it).navigate(action)
        }
        recFoodView.layoutManager = LinearLayoutManager(context)
        recFoodView.adapter = foodListAdapter

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.foodLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                textNoDataDetailResto.visibility = View.GONE
                foodListAdapter.updateListFood(it)
            }else {
                textNoDataDetailResto.visibility = View.VISIBLE
            }
        }
        viewModel.foodLoadErrorLiveData.observe(viewLifecycleOwner){
            textErrorDetailResto.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.foodLoadingLiveData.observe(viewLifecycleOwner){
            if (it){ //Sedang Load
                scrollViewDetailResto.visibility = View.GONE
                buttonCart.visibility = View.GONE
                progressLoadDetailResto.visibility = View.VISIBLE
            }else{
                scrollViewDetailResto.visibility = View.VISIBLE
                buttonCart.visibility = View.VISIBLE
                progressLoadDetailResto.visibility = View.GONE
            }
        }
    }
}