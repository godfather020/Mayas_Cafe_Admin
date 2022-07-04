package com.example.mayasfood.Retrofite.response

import com.google.gson.annotations.SerializedName

class Subcategories {

    @SerializedName("id"              )
    var id              : Int?     = null

    @SerializedName("subcategoryName" )
    var subcategoryName : String?  = null

    @SerializedName("categoryId"      )
    var categoryId      : Int?     = null

    @SerializedName("status"          )
    var status          : Boolean? = null

    @SerializedName("createdBy"       )
    var createdBy       : String?  = null

    @SerializedName("updatedBy"       )
    var updatedBy       : String?  = null

    @SerializedName("createdAt"       )
    var createdAt       : String?  = null

    @SerializedName("updatedAt"       )
    var updatedAt       : String?  = null
}