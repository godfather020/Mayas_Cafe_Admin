package com.example.mayas_cafe_admin.Retrofite.request

import com.google.gson.annotations.SerializedName

class RequestUpdateProductSize {

    @SerializedName("productId"     ) var productId     : String?                 = null

    @SerializedName("branchId"      ) var branchId      : String?                 = null

    @SerializedName("productPrice"  ) var productPrice  : ArrayList<ProductPrice> = arrayListOf()
}