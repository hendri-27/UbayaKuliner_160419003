package com.ubaya.ubayakuliner_160419003.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cart (
    val userId:Int,
    var restaurantId:Int,
    var food:Food,
    @SerializedName("qty")
    var cartQty:Int
):Parcelable