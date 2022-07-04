package com.example.lottry.data.remote.retrofit.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Request_cancelOrder {

    @SerializedName("branchId")
    var branchId:String=""

    @SerializedName("orderId")
    var orderId:String=""

    @SerializedName("cancelReason")
    var cancelReason:String=""



}