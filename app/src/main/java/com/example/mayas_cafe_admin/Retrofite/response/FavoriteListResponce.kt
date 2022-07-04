package com.example.mayasfood.Retrofite.response

import com.google.gson.annotations.SerializedName

class FavoriteListResponce {

    @SerializedName("id"         )
    var id         : Int?     = null

    @SerializedName("productId"  )
    var productId  : Int?     = null

    @SerializedName("customerId" )
    var customerId : Int?     = null

    @SerializedName("branchId"   )
    var branchId   : Int?     = null

    @SerializedName("categoryId" )
    var categoryId : String?  = null

    @SerializedName("createdAt"  )
    var createdAt  : String?  = null

    @SerializedName("updatedAt"  )
    var updatedAt  : String?  = null

    @SerializedName("ProductId"  )
    var ProductIdF  : Int?     = null

    @SerializedName("Product"    )
    var Product    : Product? = Product()

}