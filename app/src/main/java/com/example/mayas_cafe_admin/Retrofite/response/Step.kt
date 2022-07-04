package com.example.lottry.data.remote.retrofit.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Step: Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String = ""

    @SerializedName("description")
    @Expose
    var description: String = ""

    @SerializedName("winningAmount")
    @Expose
    var winningAmount: Int? = null

    @SerializedName("lotteryId")
    @Expose
    var lotteryId: Int? = null

}