package com.ubaya.ubayakuliner_160419003.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
    val id:String,
    val restaurantId:String,
    var name:String,
    var stock:Int,
    var price:Int,
    @SerializedName("photo_url")
    var photoURL:String?
):Parcelable
