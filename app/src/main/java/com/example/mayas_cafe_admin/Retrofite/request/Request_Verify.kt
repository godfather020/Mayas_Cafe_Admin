package com.example.mayas_cafe_admin.Retrofite.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Request_Verify {

    @SerializedName("phoneNumber")
    var phoneNumber:String=""

    @SerializedName("otp")
    var otp:String=""

    @SerializedName("deviceToken")
    var deviceToken:String = ""

    @SerializedName("deviceType")
    var deviceType:String = ""

    @SerializedName("deviceId")
    var deviceId : String = ""

    @SerializedName("deviceName")
    var deviceName : String = ""

    @SerializedName("osVersion")
    var osVersion : String = ""
}