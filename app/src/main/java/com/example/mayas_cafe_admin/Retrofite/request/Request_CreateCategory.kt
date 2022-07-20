package com.example.mayas_cafe_admin.Retrofite.request

import com.google.gson.annotations.SerializedName

class Request_CreateCategory {

    @SerializedName("categoryName")
    var categoryName:String=""

    @SerializedName("categoryType")
    var categoryType:String=""

}