package com.example.mayasfood.Retrofite.response

import com.google.gson.annotations.SerializedName

class Productprice{

    @SerializedName("id"          ) var id          : Int?     = null

    @SerializedName("productId"   ) var productId   : Int?     = null

    @SerializedName("productPic"  ) var productPic  : String?  = null

    @SerializedName("amount"      ) var amount      : String?  = null

    @SerializedName("offerAmount" ) var offerAmount : String?  = null

    @SerializedName("productsize" ) var productsize : String?  = null

    @SerializedName("avaibility"  ) var avaibility  : Boolean? = null

    @SerializedName("status"      ) var status      : Boolean? = null

    @SerializedName("createdBy"   ) var createdBy   : String?  = null

    @SerializedName("updatedBy"   ) var updatedBy   : String?  = null

    @SerializedName("createdAt"   ) var createdAt   : String?  = null

    @SerializedName("updatedAt"   ) var updatedAt   : String?  = null

}
