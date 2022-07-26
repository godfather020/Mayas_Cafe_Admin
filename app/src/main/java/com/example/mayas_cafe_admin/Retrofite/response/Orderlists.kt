package com.example.mayasfood.Retrofite.response

import com.google.gson.annotations.SerializedName

class Orderlists {

    @SerializedName("id"             ) var id             : Int?          = null

    @SerializedName("orderId"        ) var orderId        : Int?          = null

    @SerializedName("productId"      ) var productId      : Int?          = null

    @SerializedName("productpriceId" ) var productpriceId : Int?          = null

    @SerializedName("quantity"       ) var quantity       : String?       = null

    @SerializedName("totalAmount"    ) var totalAmount    : String?       = null

    @SerializedName("noItems"        ) var noItems        : String?       = null

    @SerializedName("orderStatus"    ) var orderStatus    : String?       = null

    @SerializedName("createdAt"      ) var createdAt      : String?       = null

    @SerializedName("updatedAt"      ) var updatedAt      : String?       = null

    @SerializedName("Productprice"   ) var Productprice   : Productprice? = Productprice()

    @SerializedName("Product"   ) var Product   : Product? = Product()
}