package com.example.mayasfood.Retrofite.response

import com.google.gson.annotations.SerializedName

class Response_cancelOrder {

    @SerializedName("code"    ) var code    : Int?           = null

    @SerializedName("data"    ) var data    : ArrayList<Int> = arrayListOf()

    @SerializedName("message" ) var message : String?        = null

    @SerializedName("success" ) var success : Boolean?       = null
}