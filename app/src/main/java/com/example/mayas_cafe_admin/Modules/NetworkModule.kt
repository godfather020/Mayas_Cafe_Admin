package com.example.mayas_cafe_admin.Modules

import com.example.mayas_cafe_admin.Retrofite.api.Apis
import com.example.mayas_cafe_admin.development.implementation.ConnectionApiEndPoint
import com.example.mayas_cafe_admin.development.interfaces.EndPoint
import com.example.mayas_cafe_admin.utils.Constants
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class NetworkModule {



    @Provides
    internal fun provideEndPoint(@Named(Constants.API_DEVELOPMENT_URL)URL:String): EndPoint =
    ConnectionApiEndPoint().setEndPoint(URL)

    @Provides
    internal fun provideRetrofit(endPoint: EndPoint): Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(endPoint.getUrl())
            .client(OkHttpClient())
            .build()

    }

    @Provides
    internal fun providRetrofitApi(retrofit: Retrofit): Apis {

        return retrofit.create(Apis::class.java)
    }


}