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
import com.ubaya.ubayakuliner_160419003.model.User

class ProfileViewModel(application: Application) : AndroidViewModel(application)  {
    val profileLiveData = MutableLiveData<User>()
    val profileLoadErrorLiveData = MutableLiveData<Boolean>()
    val profileLoadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun fetch(id:String){
        profileLoadErrorLiveData.value = false
        profileLoadingLiveData.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://ubayakuliner.herokuapp.com/users/$id"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val result = Gson().fromJson(it,User::class.java)
                profileLiveData.value = result
                profileLoadingLiveData.value = false
                Log.d("showvolley",it)
            },
            {
                profileLoadingLiveData.value = false
                profileLoadErrorLiveData.value = true
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