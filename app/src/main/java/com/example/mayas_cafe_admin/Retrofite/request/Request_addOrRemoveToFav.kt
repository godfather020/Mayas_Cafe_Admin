package com.example.lottry.data.remote.retrofit.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Request_addOrRemoveToFav {

    @SerializedName("productId")
    var productId:String=""

    @SerializedName("branchId")
    var branchId:String=""

}