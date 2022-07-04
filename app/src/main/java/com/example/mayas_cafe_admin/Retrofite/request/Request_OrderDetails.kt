package com.example.mayasfood.Retrofite.request

import com.google.gson.annotations.SerializedName

class Request_OrderDetails {

    @SerializedName("toalQuantity"  ) var toalQuantity  : String?               = null

    @SerializedName("amount"        ) var amount        : String?               = null

    @SerializedName("paymentMethod" ) var paymentMethod : String?               = null

    @SerializedName("branchId"      ) var branchId      : String?               = null

    @SerializedName("pickupAt"      ) var pickupAt      : String?               = null

    @SerializedName("orderItems"    ) var orderItems    : ArrayList<OrderItems> = arrayListOf()


}