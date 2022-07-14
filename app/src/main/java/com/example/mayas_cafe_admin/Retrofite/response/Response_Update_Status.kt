package com.example.mayas_cafe_admin.Retrofite.response

import com.google.gson.annotations.SerializedName

class Response_Update_Status {

    @SerializedName("code"    ) var code    : Int?           = null

    @SerializedName("data"    ) var data    : ArrayList<Int> = arrayListOf()

    @SerializedName("message" ) var message : String?        = null

    @SerializedName("success" ) var success : Boolean?       = null
}