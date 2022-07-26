package com.example.mayas_cafe_admin.Retrofite.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class User {

    @SerializedName("id")
    var id : Int? = null

    @SerializedName("userName")
    var userName : String = ""

    @SerializedName("phoneNumber")
    var phoneNumber : String = ""

    @SerializedName("profilePic")
    var profilePic : String = ""

    @SerializedName("address")
    var address : String = ""

    @SerializedName("parmanentAddress")
    var parmanentAddress : String = ""

    @SerializedName("deviceId")
    var deviceId : String = ""

    @SerializedName("deviceName")
    var deviceName : String = ""

    @SerializedName("osVersion")
    var osVersion : String = ""

    @SerializedName("deviceType")
    var deviceType : String = ""

    @SerializedName("deviceToken")
    var deviceToken : String = ""

    @SerializedName("otp")
    var otp : Int? = null

    @SerializedName("otpExpire")
    var otpExpire : String = ""

    @SerializedName("isVerified")
    var isVerified : Boolean? = null

    @SerializedName("status")
    var status : Boolean? = null

    @SerializedName("branchId")
    var branchId : Int? = null

    @SerializedName("accessType")
    var accessType : String = ""

    @SerializedName("bankAccount")
    var bankAccount : String = ""

    @SerializedName("ifscCode")
    var ifscCode : String = ""

    @SerializedName("branchName")
    var branchName : String = ""

    @SerializedName("createdBy")
    var createdBy : String = ""

    @SerializedName("updatedBy")
    var updatedBy : String = ""

    @SerializedName("joinAt")
    var joinAt : String = ""

    @SerializedName("createdAt")
    var createdAt : String = ""

    @SerializedName("updatedAt")
    var updatedAt : String = ""

}
