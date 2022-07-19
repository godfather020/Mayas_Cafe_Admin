package com.example.mayas_cafe_admin.Retrofite.api

import com.example.lottry.data.remote.retrofit.request.*
import com.example.mayas_cafe_admin.Retrofite.request.*
import com.example.mayas_cafe_admin.Retrofite.response.Response_Update_Status
import com.example.mayas_cafe_admin.utils.Constants
import com.example.mayasfood.Retrofite.request.*
import com.example.mayasfood.Retrofite.response.Response_Common
import com.example.mayasfood.Retrofite.response.Response_Notification
import com.example.mayasfood.Retrofite.response.Response_cancelOrder
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Apis {

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.DASHBOARD)
    fun getDashboardItems(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_Branch
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.CATEGORY)
    fun getTotalCategories(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_CategoryDetails
    ):Call<Response_Common>


    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.REMOVE_NOTIFICATION)
    fun removeNotification(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_notification
    ):Call<Response_Notification>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @GET(Constants.ApiConstant.REMOVE_ALL_NOTIFICATION)
    fun removeAllNotification(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String
    ):Call<Response_Notification>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.VERIFY)
    fun getVerifyOtp(
        @Body body: Request_Verify
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.GET_OTP)
    fun getOtp(
        @Body body: Request_OTP
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.REGISTER)
    fun getRegistraion(
        @Body body: Request_Registration
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.DEVICE_INFO)
    fun sendDeviceDetail(
        @Body body: Request_DeviceInfo
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.USER_PROFILE)
    fun getAdminProfile(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.UPDATE_PROFILE)
    fun updateAdminProfile(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_updateProfile
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.MY_COUPONS)
    fun myCoupons(
        @Body body: Request_myCoupons
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.POPULAR_FOOD)
    fun getPopularFood(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_Branch
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.RESTAURANT_CHOICES)
    fun getRestaurantChoices(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_Branch
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.LIST_PRODUCTS)
    fun getTotalProducts(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_CategoryDetails
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.ADD_REMOVE_FAV)
    fun addOrRemoveToFav(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_addOrRemoveToFav
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.NOTIFICATION)
    fun getNotificaiton(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.FAVORITE_LIST)
    fun getFavList(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_Branch
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.CREATE_PRODUCT)
    fun createProduct(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_AddItem
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.GET_ORDERS)
    fun getAllOrders(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_Branch
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.GET_OFFERS)
    fun getOffers(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_Branch
    ):Call<Response_Common>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.UPDATE_ORDER)
    fun updateOrder(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_UpdateOrder
    ):Call<Response_Update_Status>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.UPDATE_PRODUCT)
    fun updateProduct(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: RequestUpdateProduct
    ):Call<Response_Update_Status>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.UPDATE_PRODUCT_SIZE)
    fun updateProductSize(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: RequestUpdateProductSize
    ):Call<Response_Update_Status>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.UPDATE_PRODUCT)
    fun deleteProduct(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: RequestDeleteProduct
    ):Call<Response_Update_Status>

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.CANCEL_ORDER)
    fun cancelOrder(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN) x_tocken: String,
        @Body body: Request_cancelOrder
    ):Call<Response_cancelOrder>

    @Multipart
    @POST(Constants.ApiConstant.SET_PROFILE_IMAGE)
    fun setProfileImg(@Header (Constants.sharedPrefrencesConstant.X_TOKEN) x_tocken:String,
                      @Part  image: MultipartBody.Part
    ): Call<Response_Common>?

    @Multipart
    @POST(Constants.ApiConstant.UPLOAD_ITEM_IMG)
    fun uploadItemImg(@Header (Constants.sharedPrefrencesConstant.X_TOKEN) x_tocken:String,
                      @Part image: MultipartBody.Part,
                      @Part("productId") productId: RequestBody
    ): Call<Response_Common>?

    @Headers("Content-Type:application/json", "Accept:application/json")
    @POST(Constants.ApiConstant.GET_PRODUCT_DETAILS)
    fun getProductDetail(
        @Header (Constants.sharedPrefrencesConstant.X_TOKEN)x_tocken:String,
        @Body body: Request_ProductDetails
    ):Call<Response_Common>

}