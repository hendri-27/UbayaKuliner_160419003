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
import com.ubaya.ubayakuliner_160419003.model.Cart

class ListCartViewModel(application: Application) : AndroidViewModel(application)  {
    val cartLiveData = MutableLiveData<ArrayList<Cart>>()
    val cartLoadErrorLiveData = MutableLiveData<Boolean>()
    val cartLoadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh(id:String) {
        cartLoadErrorLiveData.value = false
        cartLoadingLiveData.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://ubayakuliner.herokuapp.com/carts?userId=$id&_expand=restaurant&_expand=food"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Cart>>() {}.type
                val result = Gson().fromJson<ArrayList<Cart>>(it,sType)
                cartLiveData.value = result
                cartLoadingLiveData.value = false
                Log.d("showvolley",it)
            },
            {
                cartLoadingLiveData.value = false
                cartLoadErrorLiveData.value = true
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