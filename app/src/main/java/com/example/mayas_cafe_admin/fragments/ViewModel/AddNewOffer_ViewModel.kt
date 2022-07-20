package com.example.mayas_cafe_admin.fragments.ViewModel

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mayas_cafe_admin.Retrofite.request.Request_updateCoupon
import com.example.mayas_cafe_admin.Retrofite.response.Response_Update_Status
import com.example.mayas_cafe_admin.development.retrofit.RetrofitInstance
import com.example.mayasfood.Retrofite.response.Response_Common
import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

class AddNewOffer_ViewModel : ViewModel() {

    lateinit var activity: Fragment

    val commonResponse = MutableLiveData<Response_Common>()
    var updateStatus = MutableLiveData<Response_Common>()

    lateinit var loading: ProgressBar
    var token: String = ""
    var isActive: Boolean = false
    lateinit var itemImg: File


    fun createCoupon(
        activity: Fragment,
        token: String,
        loading: ProgressBar,
        name: String,
        title: String,
        des: String,
        code: String,
        startAt: String,
        endAt: String,
        calcType: String,
        upto: String,
        min: String,
        img: File,
        isActive: Boolean
    ): MutableLiveData<Response_Common> {

        this.activity = activity
        this.loading = loading
        this.token = token
        this.itemImg = img
        this.isActive = isActive

        val reqFile: RequestBody = RequestBody.create("image/jpg".toMediaTypeOrNull(), itemImg)

        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", itemImg.name, reqFile)

        val offerName: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            name
        )
        val offerTitle: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            title
        )
        val offerDes: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            des
        )
        val offerCode: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            code
        )
        val offerCalc: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            calcType
        )
        val offerStart: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            startAt
        )
        val offerEnd: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            endAt
        )
        val offerUpto: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            upto
        )
        val offerMin: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            min
        )
        val branchId: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            "1"
        )

        val retrofitInstance: RetrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.createOffer(
            token,
            body,
            offerName,
            offerCode,
            offerTitle,
            offerDes,
            offerCalc,
            offerUpto,
            offerMin,
            offerStart,
            offerEnd,
            branchId
        )

        retrofitData!!.enqueue(object : retrofit2.Callback<Response_Common> {

            override fun onResponse(
                call: Call<Response_Common>,
                response: Response<Response_Common>
            ) {


                if (response.isSuccessful) {

                    //Toast.makeText(activity.context, "Upload Success", Toast.LENGTH_SHORT).show()

                    //updateCouponStatus(response.body()!!.getData()!!.id.toString())

                    commonResponse.value = response.body()!!

                } else {

                    loading.visibility = View.GONE
                    val element: JsonElement =
                        Gson().fromJson(response.errorBody()!!.string(), JsonElement::class.java)
                    val jsonObject = element.asJsonObject

                    if (jsonObject.get("code").toString().equals("500")) {
//                       if(jsonObject.get("message").asString.equals(activity.resources.getString(R.string.please_registered_your_number_),true)){
                        Toast.makeText(
                            activity.context,
                            jsonObject.get("message").toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("resError", jsonObject.get("message").toString())
                    }


                }
            }

            override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")

                Toast.makeText(activity.context, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        return commonResponse
    }

    fun updateCouponStatus(id: String) : MutableLiveData<Response_Common>{

        val updateCoupon = Request_updateCoupon()

        updateCoupon.couponId = id
        updateCoupon.status = "1"
        updateCoupon.branchId = "1"

        val retrofitInstance: RetrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.updateCoupon(token, updateCoupon)

        retrofitData.enqueue(object : retrofit2.Callback<Response_Common> {

            override fun onResponse(
                call: Call<Response_Common>,
                response: Response<Response_Common>
            ) {


                if (response.isSuccessful) {

                    //Toast.makeText(activity.context, "Upload Success", Toast.LENGTH_SHORT).show()

                    //updateCouponStatus(response.body()!!.getData()!!.id.toString())

                    updateStatus.value = response.body()!!

                } else {

                    loading.visibility = View.GONE
                    val element: JsonElement =
                        Gson().fromJson(response.errorBody()!!.string(), JsonElement::class.java)
                    val jsonObject = element.asJsonObject

                    if (jsonObject.get("code").toString().equals("500")) {
//                       if(jsonObject.get("message").asString.equals(activity.resources.getString(R.string.please_registered_your_number_),true)){
                        Toast.makeText(
                            activity.context,
                            jsonObject.get("message").toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("resError", jsonObject.get("message").toString())
                    }
                }
            }

            override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")

                Toast.makeText(activity.context, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        return updateStatus
    }
}