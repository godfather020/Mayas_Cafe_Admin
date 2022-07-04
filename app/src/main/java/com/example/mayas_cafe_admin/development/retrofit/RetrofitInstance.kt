package com.example.mayas_cafe_admin.development.retrofit

import com.example.mayas_cafe_admin.utils.Constants
import com.example.mayas_cafe_admin.Retrofite.api.Apis
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class RetrofitInstance {

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.API_DEVELOPMENT_URL)
        .build()
        .create(Apis::class.java)

}

