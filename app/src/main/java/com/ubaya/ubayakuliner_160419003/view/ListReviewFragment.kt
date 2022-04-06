package com.ubaya.ubayakuliner_160419003.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.ubayakuliner_160419003.R
import com.ubaya.ubayakuliner_160419003.viewmodel.ListReviewViewModel
import kotlinx.android.synthetic.main.fragment_list_review.*

//import kotlinx.android.synthetic.main.fragment_list_restaurant.*

/**
 * A simple [Fragment] subclass.
 * Use the [ListReviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListReviewFragment : Fragment() {
    private lateinit var viewModel:ListReviewViewModel
    private val reviewListAdapter = ListReviewAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ListReviewViewModel::class.java)

        val restaurantId = ListReviewFragmentArgs.fromBundle(requireArguments()).restaurantId
        viewModel.refresh(restaurantId)

        recViewReview.layoutManager = LinearLayoutManager(context)
        recViewReview.adapter = reviewListAdapter

        observeViewModel()

        refreshLayout.setOnRefreshListener {
            recViewReview.visibility = View.GONE
            textErrorReview.visibility = View.GONE
            progressLoadReview.visibility = View.GONE
            viewModel.refresh(restaurantId)
            refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel(){
        viewModel.reviewLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                textNoDataListReview.visibility = View.GONE
                reviewListAdapter.updateListReview(it)
            }else {
                textNoDataListReview.visibility = View.VISIBLE
            }
        }
        viewModel.reviewLoadErrorLiveData.observe(viewLifecycleOwner){
            textErrorReview.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.reviewloadingLiveData.observe(viewLifecycleOwner){
            if (it){ //Sedang Load
                recViewReview.visibility = View.GONE
                progressLoadReview.visibility = View.VISIBLE
            }else{
                recViewReview.visibility = View.VISIBLE
                progressLoadReview.visibility = View.GONE
            }
        }
    }
}