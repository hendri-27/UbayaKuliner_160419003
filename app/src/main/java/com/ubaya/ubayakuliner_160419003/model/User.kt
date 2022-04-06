package com.ubaya.ubayakuliner_160419003.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id:Int,
    var username:String,
    var name:String,
    var gender:String,
    @SerializedName("birth_date")
    var birthDate:String,
    @SerializedName("phone_number")
    var phoneNumber:String,
    var email:String,
    var password:String,
    @SerializedName("photo_url")
    var photoURL:String?
):Parcelable
