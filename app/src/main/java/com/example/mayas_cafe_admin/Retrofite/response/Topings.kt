package com.example.mayasfood.Retrofite.response

import com.google.gson.annotations.SerializedName

class Topings {

    @SerializedName("id" )
    var id             : Int?    = null

    @SerializedName("topingName")
    var topingName     : String? = null

    @SerializedName("topingDesc")
    var topingDesc     : String? = null

    @SerializedName("topingQuantity")
    var topingQuantity : String? = null

    @SerializedName("productId")
    var productId : Int? = null

    @SerializedName("createdBy")
    var createdBy      : String? = null

    @SerializedName("updatedBy")
    var updatedBy      : String? = null

    @SerializedName("createdAt")
    var createdAt      : String? = null

    @SerializedName("updatedAt")
    var updatedAt      : String? = null

}