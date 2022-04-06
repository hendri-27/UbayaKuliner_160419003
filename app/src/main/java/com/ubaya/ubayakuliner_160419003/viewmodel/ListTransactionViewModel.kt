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
import com.ubaya.ubayakuliner_160419003.model.Transaction

class ListTransactionViewModel(application: Application) : AndroidViewModel(application)  {
    val transactionLiveData = MutableLiveData<ArrayList<Transaction>>()
    val transactionLoadErrorLiveData = MutableLiveData<Boolean>()
    val transactionloadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh(id:String) {
        transactionLoadErrorLiveData.value = false
        transactionloadingLiveData.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://ubayakuliner.herokuapp.com/transaction_histories?userId=$id&_sort=date&_order=desc&_expand=user&_expand=restaurant"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Transaction>>() {}.type
                val result = Gson().fromJson<ArrayList<Transaction>>(it,sType)
                transactionLiveData.value = result
                transactionloadingLiveData.value = false
                Log.d("showvolley",it)
            },
            {
                transactionloadingLiveData.value = false
                transactionLoadErrorLiveData.value = true
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