package com.example.mayas_cafe_admin.Retrofite.request

import com.google.gson.annotations.SerializedName

class RequestDeleteProduct {

    @SerializedName("status")
    var status: String = ""

    @SerializedName("productId")
    var productId: String = ""

    @SerializedName("branchId")
    var branchId: String = ""
}