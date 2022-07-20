package com.example.mayas_cafe_admin.Retrofite.response

import com.google.gson.annotations.SerializedName

class CouponDetails {

    @SerializedName("isFulfilled" )
    var isFulfilled : Boolean? = null

    @SerializedName("isRejected"  )
    var isRejected  : Boolean? = null

}