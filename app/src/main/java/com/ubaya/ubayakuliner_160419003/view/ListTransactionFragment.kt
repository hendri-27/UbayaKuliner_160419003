package com.ubaya.ubayakuliner_160419003.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.ubayakuliner_160419003.R
import com.ubaya.ubayakuliner_160419003.util.userId
import com.ubaya.ubayakuliner_160419003.viewmodel.ListTransactionViewModel
import kotlinx.android.synthetic.main.fragment_list_transaction.*
import kotlinx.android.synthetic.main.fragment_list_transaction.refreshLayout

/**
 * A simple [Fragment] subclass.
 * Use the [ListTransactionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListTransactionFragment : Fragment() {
    private lateinit var viewModel: ListTransactionViewModel
    private val transactionListAdapter = ListTransactionAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ListTransactionViewModel::class.java)

        viewModel.refresh(userId.toString())

        recViewListTransaction.layoutManager = LinearLayoutManager(context)
        recViewListTransaction.adapter = transactionListAdapter

        observeViewModel()

        refreshLayout.setOnRefreshListener {
            recViewListTransaction.visibility = View.GONE
            textErrorListTransaction.visibility = View.GONE
            progressLoadListTransaction.visibility = View.GONE
            viewModel.refresh(userId.toString())
            refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel(){
        viewModel.transactionLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                textNoDataListTransaction.visibility = View.GONE
                transactionListAdapter.updateListTransaction(it)
            }else {
                textNoDataListTransaction.visibility = View.VISIBLE
            }
        }
        viewModel.transactionLoadErrorLiveData.observe(viewLifecycleOwner){
            textErrorListTransaction.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.transactionloadingLiveData.observe(viewLifecycleOwner){
            if (it){ //Sedang Load
                recViewListTransaction.visibility = View.GONE
                progressLoadListTransaction.visibility = View.VISIBLE
            }else{
                recViewListTransaction.visibility = View.VISIBLE
                progressLoadListTransaction.visibility = View.GONE
            }
        }
    }
}