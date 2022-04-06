package com.ubaya.ubayakuliner_160419003.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.ubayakuliner_160419003.R
import com.ubaya.ubayakuliner_160419003.viewmodel.DetailRestaurantViewModel
import com.ubaya.ubayakuliner_160419003.viewmodel.DetailTransactionViewModel
import kotlinx.android.synthetic.main.fragment_detail_restaurant.*
import kotlinx.android.synthetic.main.fragment_detail_transaction.*

/**
 * A simple [Fragment] subclass.
 * Use the [DetailTransactionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailTransactionFragment : Fragment() {
    private lateinit var viewModel: DetailTransactionViewModel
    private val detailFoodList = DetailTransactionAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(DetailTransactionViewModel::class.java)

        val transaction = DetailTransactionFragmentArgs.fromBundle(requireArguments()).transaction
        viewModel.fetch(transaction)

        textDetailTransRestoName.text = transaction.restaurant.name
        textDetailTransPaymentMethod.text = String.format("Rp%,d - %s", transaction.grandTotal,transaction.paymentMethod)
        textDetailDetailTransactionUserPhone.text = transaction.user.phoneNumber
        textDetailTransactionAddress.text = transaction.location
        textDetailTransRestoName2.text = transaction.restaurant.name
        textDetailTransSubtotal.text = String.format("Rp%,d",transaction.foodSubtotal)
        textDetailTransDeliveryFee.text = String.format("Rp%,d",transaction.deliveryFee)
        textDetailTransServiceFee.text = String.format("Rp%,d",transaction.serviceFee)
        textDetailTransGrandtotal.text = String.format("Rp%,d",transaction.grandTotal)
        textDetailTransactionID.text = transaction.id
        textDetailTransactionDate.text = transaction.date
        textDetailTransactionMethodPayment.text = transaction.paymentMethod

        recViewDetailTrans.layoutManager = LinearLayoutManager(context)
        recViewDetailTrans.adapter = detailFoodList

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.detailTransactionLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                textNoDataDetailTransaction.visibility = View.GONE
                detailFoodList.updateListDetailTransaction(it)
            }else {
                textNoDataDetailTransaction.visibility = View.VISIBLE
            }
        }
        viewModel.detailTransactionLoadErrorLiveData.observe(viewLifecycleOwner){
            textErrorDetailTransaction.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.detailTransactionLoadingLiveData.observe(viewLifecycleOwner){
            if (it){ //Sedang Load
                recViewDetailTrans.visibility = View.GONE
                progressLoadDetailTransaction.visibility = View.VISIBLE
            }else{
                recViewDetailTrans.visibility = View.VISIBLE
                progressLoadDetailTransaction.visibility = View.GONE
            }
        }
    }
}