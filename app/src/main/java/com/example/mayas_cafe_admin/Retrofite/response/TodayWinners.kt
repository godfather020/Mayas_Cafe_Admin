package com.example.lottry.data.remote.retrofit.response

import com.example.mayas_cafe_admin.Retrofite.response.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TodayWinners : Serializable{

    @SerializedName("ticketId")
    @Expose
    var ticketId: String = ""

    @SerializedName("createdAt")
    @Expose
    var createdAt: String = ""

    @SerializedName("Step")
    @Expose
    var step: Step? = null

    @SerializedName("User")
    @Expose
    var user: User? = null
}