package com.example.mayasfood.Retrofite.request

import com.google.gson.annotations.SerializedName

class OrderItems {

    @SerializedName("productId"      ) var productId      : String? = null

    @SerializedName("totalAmount"    ) var totalAmount    : String? = null

    @SerializedName("quantity"       ) var quantity       : String? = null

    @SerializedName("noItems"        ) var noItems        : String? = null

    @SerializedName("productpriceId" ) var productpriceId : String? = null

    constructor(
        productId: String?,
        totalAmount: String?,
        quantity: String?,
        noItems: String?,
        productpriceId: String?
    ) {
        this.productId = productId
        this.totalAmount = totalAmount
        this.quantity = quantity
        this.noItems = noItems
        this.productpriceId = productpriceId
    }
}