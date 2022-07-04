package com.example.mayasfood.Retrofite.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Request_myCoupons {

    @SerializedName("branchId")
    var branchId:String= ""

    @SerializedName("name")
    var name:String= ""

    @SerializedName("code")
    var code:String=""

    @SerializedName("title")
    var title:String=""

}