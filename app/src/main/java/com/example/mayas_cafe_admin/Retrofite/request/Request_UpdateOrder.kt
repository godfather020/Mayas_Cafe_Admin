package com.example.mayas_cafe_admin.Retrofite.request

import com.google.gson.annotations.SerializedName

class Request_UpdateOrder {

    @SerializedName("status")
    var orderStatus: String = ""

    @SerializedName("orderId")
    var orderId: String = ""

    @SerializedName("branchId")
    var branchId: String = ""
}