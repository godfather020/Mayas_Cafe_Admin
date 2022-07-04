package com.example.mayasfood.Retrofite.response

import com.example.mayasfood.Retrofite.response.Response_Data
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Response_Notification {

    @SerializedName("code")
    @Expose
    private var code: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("error")
    @Expose
    private var error: String? = null

    @SerializedName("data")
    @Expose
    private var data: String? = null

    @SerializedName("success")
    @Expose
    var success: Boolean? = null
}