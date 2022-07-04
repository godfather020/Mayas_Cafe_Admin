package com.example.mayasfood.Retrofite.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Request_updateProfile {

    @SerializedName("userName")
    var userName:String=""

    @SerializedName("address")
    var address:String = ""

    @SerializedName("email")
     var email:String=""

   /* @SerializedName("referralCoins")
    var referralCoins:String=""

    @SerializedName("walletCoins")
    var walletCoins:String=""*/

}