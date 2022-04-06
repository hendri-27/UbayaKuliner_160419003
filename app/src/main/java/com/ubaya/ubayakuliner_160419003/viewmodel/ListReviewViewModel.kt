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
import com.ubaya.ubayakuliner_160419003.model.Review

class ListReviewViewModel(application: Application) : AndroidViewModel(application)  {
    val reviewLiveData = MutableLiveData<ArrayList<Review>>()
    val reviewLoadErrorLiveData = MutableLiveData<Boolean>()
    val reviewloadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh(id:String) {
        reviewLoadErrorLiveData.value = false
        reviewloadingLiveData.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://ubayakuliner.herokuapp.com/reviews?restaurantId=$id&_expand=user"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Review>>() {}.type
                val result = Gson().fromJson<ArrayList<Review>>(it,sType)
                reviewLiveData.value = result
                reviewloadingLiveData.value = false
                Log.d("showvolley",it)
            },
            {
                reviewloadingLiveData.value = false
                reviewLoadErrorLiveData.value = true
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