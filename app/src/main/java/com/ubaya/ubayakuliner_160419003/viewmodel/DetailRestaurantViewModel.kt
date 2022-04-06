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
import com.ubaya.ubayakuliner_160419003.model.Food

class DetailRestaurantViewModel(application: Application) : AndroidViewModel(application)  {
    val foodLiveData = MutableLiveData<ArrayList<Food>>()
    val foodLoadErrorLiveData = MutableLiveData<Boolean>()
    val foodLoadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun fetch(id:String){
        foodLoadErrorLiveData.value = false
        foodLoadingLiveData.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://ubayakuliner.herokuapp.com/foods?restaurantId=$id"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Food>>() {}.type
                val result = Gson().fromJson<ArrayList<Food>>(it,sType)
                foodLiveData.value = result
                foodLoadingLiveData.value = false
                Log.d("showvolley",it)
            },
            {
                foodLoadingLiveData.value = false
                foodLoadErrorLiveData.value = true
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