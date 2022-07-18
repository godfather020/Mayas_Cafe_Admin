package com.example.lottry.data.remote.retrofit.request

import android.os.Parcel
import android.os.Parcelable
import com.example.mayas_cafe_admin.Retrofite.request.ProductPrice
import com.google.gson.annotations.SerializedName

class RequestUpdateProduct {

    @SerializedName("productId"     ) var productId     : String?                 = null

    @SerializedName("branchId"      ) var branchId      : String?                 = null

    @SerializedName("productName"   ) var productName   : String?                 = null

    @SerializedName("productDesc"   ) var productDesc   : String?                 = null

    @SerializedName("categoryId"    ) var categoryId    : String?                 = null

    @SerializedName("subcategoryId" ) var subcategoryId : String?                 = null

    @SerializedName("systemrating"  ) var systemrating  : String?                 = null

    @SerializedName("status"        ) var status        : String?                 = null

    @SerializedName("productPrice"  ) var productPrice  : ArrayList<ProductPrice> = arrayListOf()


}