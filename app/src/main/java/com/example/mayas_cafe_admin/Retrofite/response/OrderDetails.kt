package com.example.mayas_cafe_admin.Retrofite.response

import com.google.gson.annotations.SerializedName

class OrderDetails {

    @SerializedName("isFulfilled"  )
    var isFulfilled  : String? = null

    @SerializedName("isRejected"  )
    var isRejected  : String? = null
}