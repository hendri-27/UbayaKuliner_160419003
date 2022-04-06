package com.ubaya.ubayakuliner_160419003.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.ubayakuliner_160419003.R
import com.ubaya.ubayakuliner_160419003.model.Cart
import com.ubaya.ubayakuliner_160419003.util.arrPaymentMethod
import com.ubaya.ubayakuliner_160419003.util.userId
import com.ubaya.ubayakuliner_160419003.viewmodel.CheckoutViewModel
import kotlinx.android.synthetic.main.fragment_checkout.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 * Use the [CheckoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckoutFragment : Fragment() {
    private lateinit var viewModel:CheckoutViewModel
    private var subTotal:Int = 0
    private var serviceFee:Int = (0.1 * Random.nextInt(2000,10000)).toInt()
    private var deliveryFee:Int = (Random.nextDouble(0.1,1.0) * Random.nextInt(2000,10000)).toInt()
    private val listCartAdapter = CheckoutAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(CheckoutViewModel::class.java)

        subTotal = CheckoutFragmentArgs.fromBundle(requireArguments()).subtotal
        val listCart = CheckoutFragmentArgs.fromBundle(requireArguments()).cart
        viewModel.fetch(userId.toString(),listCart[0].restaurantId.toString())

        val adapter = ArrayAdapter(view.context, R.layout.myspinner_layout, arrPaymentMethod)
        adapter.setDropDownViewResource(R.layout.myspinner_item_layout)
        spinnerPaymentMethod.adapter = adapter

        recViewCheckout.layoutManager = LinearLayoutManager(context)
        recViewCheckout.adapter = listCartAdapter

        textDetailOrderSubtotal.text = String.format("Rp%,d", subTotal)
        textDetailOrderDeliveryFee.text = String.format("Rp%,d", deliveryFee)
        textDetailOrderServiceFee.text = String.format("Rp%,d", serviceFee)
        textDetailTransGrandtotal.text = String.format("Rp%,d", subTotal + deliveryFee + serviceFee)
        textOrderGrandtotal.text = String.format("Rp%,d", subTotal + deliveryFee + serviceFee)
        listCartAdapter.updateListCheckout(listCart.toList() as ArrayList<Cart>)

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.UserLiveData.observe(viewLifecycleOwner) {
            editCheckoutPhone.setText(it.phoneNumber)
        }
        viewModel.UserLoadErrorLiveData.observe(viewLifecycleOwner){
            textErrorCheckout.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.UserLoadingLiveData.observe(viewLifecycleOwner){
            if (it){
                scrollViewCheckout.visibility = View.GONE
                cardViewCheckout.visibility = View.GONE
                progressLoadCheckout.visibility = View.VISIBLE
            }else{
                scrollViewCheckout.visibility = View.VISIBLE
                cardViewCheckout.visibility = View.VISIBLE
                progressLoadCheckout.visibility = View.GONE
            }
        }
        viewModel.RestaurantLiveData.observe(viewLifecycleOwner) {
            textCheckoutRestoName.text = it.name
        }
        viewModel.RestaurantLoadErrorLiveData.observe(viewLifecycleOwner){
            textErrorCheckout.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.RestaurantLoadingLiveData.observe(viewLifecycleOwner){
            if (it){
                scrollViewCheckout.visibility = View.GONE
                cardViewCheckout.visibility = View.GONE
                progressLoadCheckout.visibility = View.VISIBLE
            }else{
                scrollViewCheckout.visibility = View.VISIBLE
                cardViewCheckout.visibility = View.VISIBLE
                progressLoadCheckout.visibility = View.GONE
            }
        }
    }
}