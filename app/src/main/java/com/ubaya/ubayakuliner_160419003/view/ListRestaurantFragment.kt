package com.ubaya.ubayakuliner_160419003.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.ubayakuliner_160419003.R
import com.ubaya.ubayakuliner_160419003.viewmodel.ListRestaurantViewModel
import kotlinx.android.synthetic.main.fragment_list_restaurant.*

/**
 * A simple [Fragment] subclass.
 * Use the [ListRestaurantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListRestaurantFragment : Fragment() {
    private lateinit var viewModel:ListRestaurantViewModel
    private val restaurantListAdapter = ListRestaurantAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_restaurant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ListRestaurantViewModel::class.java)
        viewModel.refresh()

        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = restaurantListAdapter

        observeViewModel()

        refreshLayout.setOnRefreshListener {
            recView.visibility = View.GONE
            textError.visibility = View.GONE
            progressLoadResto.visibility = View.GONE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel(){
        viewModel.restaurantLiveData.observe(viewLifecycleOwner) {
            restaurantListAdapter.updateListRestaurant(it)
        }
        viewModel.restaurantLoadErrorLiveData.observe(viewLifecycleOwner){
            textError.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner){
            if (it){ //Sedang Load
                recView.visibility = View.GONE
                progressLoadResto.visibility = View.VISIBLE
            }else{
                recView.visibility = View.VISIBLE
                progressLoadResto.visibility = View.GONE
            }
        }
    }
}