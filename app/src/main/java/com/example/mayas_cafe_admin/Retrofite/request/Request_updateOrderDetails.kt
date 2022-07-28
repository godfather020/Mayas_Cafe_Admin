package com.example.mayas_cafe_admin.Retrofite.request

import com.google.gson.annotations.SerializedName

class Request_updateOrderDetails {

    @SerializedName("orderStatus")
    var orderStatus: String = ""

    @SerializedName("orderId")
    var orderId: String = ""

    @SerializedName("paymentMethod")
    var paymentMethod: String = ""

    @SerializedName("paymentStatus")
    var paymentStatus: String = ""

    @SerializedName("branchId")
    var branchId: String = ""
}