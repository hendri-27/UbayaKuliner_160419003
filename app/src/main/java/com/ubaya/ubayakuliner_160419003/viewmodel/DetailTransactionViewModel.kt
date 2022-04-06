package com.ubaya.ubayakuliner_160419003.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.ubayakuliner_160419003.model.DetailTransaction
import com.ubaya.ubayakuliner_160419003.model.Transaction

class DetailTransactionViewModel(application: Application) : AndroidViewModel(application)  {
    val detailTransactionLiveData = MutableLiveData<ArrayList<DetailTransaction>>()
    val detailTransactionLoadErrorLiveData = MutableLiveData<Boolean>()
    val detailTransactionLoadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun fetch(transaction: Transaction){
        detailTransactionLoadErrorLiveData.value = false
        detailTransactionLoadingLiveData.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://ubayakuliner.herokuapp.com/detail_transactions?transaction_historyId=${transaction.id}&_expand=food"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<DetailTransaction>>() {}.type
                val result = Gson().fromJson<ArrayList<DetailTransaction>>(it,sType)
                detailTransactionLiveData.value = result
                detailTransactionLoadingLiveData.value = false
                Log.d("showvolley",it)
            },
            {
                detailTransactionLoadingLiveData.value = false
                detailTransactionLoadErrorLiveData.value = true
                Log.d("errorvolley",it.toString())
            }
        ).apply {
            tag = "TAG"
        }
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}