package com.example.mayasfood.Retrofite.response

import com.google.gson.annotations.SerializedName

class ListcategoryResponce {

    @SerializedName("id"            )
    var id            : Int?                     = null

    @SerializedName("categoryName"  )
    var categoryName  : String?                  = null

    @SerializedName("categoryType"  )
    var categoryType  : String?                  = null

    @SerializedName("status"        )
    var status        : Boolean?                 = null

    @SerializedName("createdBy"     )
    var createdBy     : String?                  = null

    @SerializedName("updatedBy"     )
    var updatedBy     : String?                  = null

    @SerializedName("createdAt"     )
    var createdAt     : String?                  = null

    @SerializedName("updatedAt"     )
    var updatedAt     : String?                  = null

    @SerializedName("Subcategories" )
    var Subcategories : List<Subcategories>? = null
}