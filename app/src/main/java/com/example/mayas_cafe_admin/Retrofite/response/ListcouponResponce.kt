package com.example.mayasfood.Retrofite.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListcouponResponce {

    @SerializedName("id"            )
    @Expose
    var id            : Int?     = null

    @SerializedName("branchId"      )
    @Expose
    var branchId      : Int?     = null

    @SerializedName("name"          )
    @Expose
    var name          : String?  = null

    @SerializedName("code"          )
    @Expose
    var code          : String?  = null

    @SerializedName("title"         )
    @Expose
    var title         : String?  = null

    @SerializedName("desc"          )
    @Expose
    var desc          : String?  = null

    @SerializedName("calculateType" )
    @Expose
    var calculateType : String?  = null

    @SerializedName("uptoDiscount"  )
    @Expose
    var uptoDiscount  : String?  = null

    @SerializedName("minimumAmount" )
    @Expose
    var minimumAmount : String?  = null

    @SerializedName("status"        )
    @Expose
    var status        : Boolean? = null

    @SerializedName("startAt"       )
    @Expose
    var startAt       : String?  = null

    @SerializedName("stopAt"        )
    @Expose
    var stopAt        : String?  = null

    @SerializedName("bannerImage"   )
    @Expose
    var bannerImage   : String?  = null

    @SerializedName("createdAt"     )
    @Expose
    var createdAt     : String?  = null

    @SerializedName("updatedAt"     )
    @Expose
    var updatedAt     : String?  = null

    @SerializedName("BranchId")
    @Expose
    var BranchId : String? = null

}