package com.example.mayasfood.Retrofite.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Request_CategoryDetails {

    @SerializedName("categoryId")
    var categoryId:String=""

    @SerializedName("branchId")
    var branchId:String = ""

}