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
import com.ubaya.ubayakuliner_160419003.model.Restaurant
import com.ubaya.ubayakuliner_160419003.model.User

class CheckoutViewModel(application: Application) : AndroidViewModel(application) {
    val UserLiveData = MutableLiveData<User>()
    val UserLoadErrorLiveData = MutableLiveData<Boolean>()
    val UserLoadingLiveData = MutableLiveData<Boolean>()
    val RestaurantLiveData = MutableLiveData<Restaurant>()
    val RestaurantLoadErrorLiveData = MutableLiveData<Boolean>()
    val RestaurantLoadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun fetch(userId: String, restoId:String) {
        UserLoadErrorLiveData.value = false
        UserLoadingLiveData.value = true
        RestaurantLoadErrorLiveData.value = false
        RestaurantLoadingLiveData.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://ubayakuliner.herokuapp.com/users/$userId"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val result = Gson().fromJson(it, User::class.java)
                UserLiveData.value = result
                UserLoadingLiveData.value = false
                Log.d("showvolley", it)
            },
            {
                UserLoadingLiveData.value = false
                UserLoadErrorLiveData.value = true
                Log.d("errorvolley", it.toString())
            }
        ).apply {
            tag = "TAG"
        }
        queue?.add(stringRequest)

        val url2 = "https://ubayakuliner.herokuapp.com/restaurants/$restoId"
        val stringRequest2 = StringRequest(
            Request.Method.GET, url2,
            {
                val result = Gson().fromJson(it, Restaurant::class.java)
                RestaurantLiveData.value = result
                RestaurantLoadingLiveData.value = false
                Log.d("showvolley", it)
            },
            {
                RestaurantLoadingLiveData.value = false
                RestaurantLoadErrorLiveData.value = true
                Log.d("errorvolley", it.toString())
            }
        ).apply {
            tag = "TAG"
        }
        queue?.add(stringRequest2)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}
