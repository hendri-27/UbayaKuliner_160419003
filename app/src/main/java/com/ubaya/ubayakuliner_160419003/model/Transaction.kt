package com.ubaya.ubayakuliner_160419003.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
    val id:String,
    var user:User,
    var restaurant:Restaurant,
    var date:String,
    var location:String,
    @SerializedName("payment_method")
    var paymentMethod:String,
    @SerializedName("delivery_fee")
    var deliveryFee:Int,
    @SerializedName("service_fee")
    var serviceFee:Int,
    @SerializedName("food_subtotal")
    var foodSubtotal:Int,
    @SerializedName("grand_total")
    var grandTotal:Int,
    var status:String,
    var rate:String?
):Parcelable
