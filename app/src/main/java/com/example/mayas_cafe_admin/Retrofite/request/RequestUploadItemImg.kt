package com.example.mayas_cafe_admin.Retrofite.request

import com.google.gson.annotations.SerializedName

class RequestUploadItemImg {

    @SerializedName("image")
    var image: String = ""

    @SerializedName("productId")
    var productId: String = ""

}