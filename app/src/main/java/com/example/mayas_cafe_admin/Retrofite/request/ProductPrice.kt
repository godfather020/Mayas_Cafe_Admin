package com.example.mayas_cafe_admin.Retrofite.request

import com.google.gson.annotations.SerializedName

class ProductPrice {

    @SerializedName("productId"      ) var productId      : String? = null

    @SerializedName("productpriceId" ) var productpriceId : String? = null

    @SerializedName("status"         ) var status         : String? = null

    @SerializedName("amount"      ) var amount      : String? = null

    @SerializedName("offerAmount"    ) var offerAmount    : String? = null

    @SerializedName("availibility"       ) var availibility       : String? = null

    @SerializedName("productsize"        ) var productsize        : String? = null

    //@SerializedName("productpriceId" ) var productpriceId : String? = null

    constructor(
        amount: String?,
        offerAmount: String?,
        availibility: String?,
        productsize: String?,
       // productpriceId: String?
    ) {
        this.amount = amount
        this.offerAmount = offerAmount
        this.availibility = availibility
        this.productsize = productsize
       // this.productpriceId = productpriceId
    }

    constructor(
        productId : String?,
        productpriceId : String,
        amount: String?,
        offerAmount: String?,
        availibility: String?,
        productsize: String?,
        // productpriceId: String?
    ) {
        this.amount = amount
        this.offerAmount = offerAmount
        this.availibility = availibility
        this.productsize = productsize
        this.productId = productId
        this.productpriceId = productpriceId
        // this.productpriceId = productpriceId
    }
}