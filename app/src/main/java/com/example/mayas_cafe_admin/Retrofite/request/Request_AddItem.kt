package com.example.mayas_cafe_admin.Retrofite.request

import com.example.mayas_cafe_admin.Retrofite.request.ProductPrice
import com.google.gson.annotations.SerializedName

class Request_AddItem {

    @SerializedName("productName"  ) var productName  : String?               = null

    @SerializedName("productDesc"        ) var productDesc        : String?               = null

    @SerializedName("categoryId" ) var categoryId : String?               = null

    @SerializedName("branchId"      ) var branchId      : String?               = null

    //@SerializedName("pickupAt"      ) var pickupAt      : String?               = null

    @SerializedName("productPrice"    ) var productPrice    : ArrayList<ProductPrice> = arrayListOf()


}