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
import com.ubaya.ubayakuliner_160419003.model.Cart
import com.ubaya.ubayakuliner_160419003.util.userId
import com.ubaya.ubayakuliner_160419003.viewmodel.ListCartViewModel
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.refreshLayout
import kotlinx.android.synthetic.main.fragment_detail_restaurant.*
import kotlinx.android.synthetic.main.fragment_list_restaurant.*

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    private lateinit var viewModel:ListCartViewModel
    private val cartListAdapter = ListCartAdapter(arrayListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ListCartViewModel::class.java)

        viewModel.refresh(userId.toString())

        recViewListCart.layoutManager = LinearLayoutManager(context)
        recViewListCart.adapter = cartListAdapter

        observeViewModel()

        buttonCartCheckout.setOnClickListener {
            val action = CartFragmentDirections.actionCheckoutFragment(cartListAdapter.listCart.toTypedArray(),cartListAdapter.getSubTotal())
            Navigation.findNavController(it).navigate(action)
        }

        refreshLayout.setOnRefreshListener {
            recViewListCart.visibility = View.GONE
            cardViewCheckout.visibility = View.GONE
            textErrorCart.visibility = View.GONE
            progressLoadCart.visibility = View.GONE
            viewModel.refresh(userId.toString())
            refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel(){
        viewModel.cartLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                textNoDataListCart.visibility = View.GONE
                cartListAdapter.updateListFood(it)
                var subTotal = 0
                for (cart in it){
                    subTotal += (cart.food.price * cart.cartQty)
                }
                textCartSubtotal.text = String.format("Rp%,d", subTotal)
                cartListAdapter.setSubTotal(subTotal)
            }else {
                textNoDataListCart.visibility = View.VISIBLE
            }
        }
        viewModel.cartLoadErrorLiveData.observe(viewLifecycleOwner){
            textErrorCart.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.cartLoadingLiveData.observe(viewLifecycleOwner){
            if (it){ //Sedang Load
                recViewListCart.visibility = View.GONE
                cardViewCheckout.visibility = View.GONE
                progressLoadCart.visibility = View.VISIBLE
            }else{
                recViewListCart.visibility = View.VISIBLE
                cardViewCheckout.visibility = View.VISIBLE
                progressLoadCart.visibility = View.GONE
            }
        }
    }
}