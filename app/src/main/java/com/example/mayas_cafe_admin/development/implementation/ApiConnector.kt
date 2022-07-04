package com.example.mayas_cafe_admin.development.implementation

import com.example.mayas_cafe_admin.Retrofite.api.Apis
import com.example.mayas_cafe_admin.development.interfaces.ApiConnect
import retrofit2.Retrofit

class ApiConnector(val retrofit: Retrofit) : ApiConnect {

    override val api: Apis
        get() = retrofit.create(api::class.java)
}