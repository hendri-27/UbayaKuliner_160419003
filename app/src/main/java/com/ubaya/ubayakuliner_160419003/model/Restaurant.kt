package com.ubaya.ubayakuliner_160419003.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant(
    val id:Int,
    var name:String,
    var address:String,
    @SerializedName("phone_number")
    var phoneNumber:String,
    @SerializedName("photo_url")
    var photoURL:String?,
    @SerializedName("rating_total")
    var ratingTotal:Float?
):Parcelable
