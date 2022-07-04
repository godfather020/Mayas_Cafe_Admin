package com.example.mayasfood.Retrofite.response

import com.google.gson.annotations.SerializedName

class ListOrderResponce {

    @SerializedName("id"            ) var id            : Int?                  = null

    @SerializedName("userId"        ) var userId        : Int?                  = null

    @SerializedName("branchId"      ) var branchId      : Int?                  = null

    @SerializedName("toalQuantity"  ) var toalQuantity  : String?               = null

    @SerializedName("amount"        ) var amount        : String?               = null

    @SerializedName("transactionId" ) var transactionId : String?               = null

    @SerializedName("paymentMethod" ) var paymentMethod : String?               = null

    @SerializedName("paymentStatus" ) var paymentStatus : String?               = null

    @SerializedName("orderStatus"   ) var orderStatus   : String?               = null

    @SerializedName("orderRating"   ) var orderRating   : String?               = null

    @SerializedName("orderComment"  ) var orderComment  : String?               = null

    @SerializedName("cancelStatus"  ) var cancelStatus  : Boolean?              = null

    @SerializedName("cancelBy"      ) var cancelBy      : String?               = null

    @SerializedName("cancelReason"  ) var cancelReason  : String?               = null

    @SerializedName("cancelAt"      ) var cancelAt      : String?               = null

    @SerializedName("pickupAt"      ) var pickupAt      : String?               = null

    @SerializedName("createdAt"     ) var createdAt     : String?               = null

    @SerializedName("updatedAt"     ) var updatedAt     : String?               = null

    @SerializedName("Orderlists"    ) var Orderlists    : List<Orderlists>? = null
}