package com.example.mayas_cafe_admin.Retrofite.request

import com.google.gson.annotations.SerializedName

class Request_updateCoupon {

    @SerializedName("status")
    var status: String = ""

    @SerializedName("couponId")
    var couponId: String = ""

    @SerializedName("branchId")
    var branchId: String = ""
}