package com.example.mayas_cafe_admin.activities.ViewModel

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mayas_cafe_admin.FirebaseCloudMsg
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.Retrofite.request.Request_OTP
import com.example.mayas_cafe_admin.Retrofite.request.Request_Verify
import com.example.mayas_cafe_admin.activities.Login
import com.example.mayas_cafe_admin.development.retrofit.RetrofitInstance
import com.example.mayasfood.Retrofite.response.Response_Common
import com.google.gson.Gson
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OTP_ViewModel : ViewModel() {

    lateinit var activity: AppCompatActivity

    val commonResponse = MutableLiveData<Response_Common>()

    lateinit var loading : ProgressBar

    fun verify_otp(activity:AppCompatActivity,phoneNumber:String, otp: String,deviceId: String, deviceName: String, deviceOS: String):MutableLiveData<Response_Common>{

        this.activity=activity

        loading = activity.findViewById(R.id.loading_otp)

        val token = FirebaseCloudMsg.getToken(activity)
        Log.d("OTPToken1", token.toString())

        val requestVerify= Request_Verify()

        requestVerify.phoneNumber=phoneNumber
        requestVerify.deviceType = "Android"
        requestVerify.deviceId = deviceId
        requestVerify.deviceName = deviceName
        requestVerify.osVersion = deviceOS
        requestVerify.deviceToken = token.toString()
        requestVerify.otp = otp
        //requestVerify.deviceToken = token
        //Log.d("deviceToken", Constant.ApiConstant.DEVICE_TOKEN )
        //Log.d("deviceTokensh", Constant.sharedPrefrencesConstant.DEVICE_TOKEN)

        getResposneOfVerifyOtpFromApi(requestVerify)

        return commonResponse
    }

    fun getResposneOfVerifyOtpFromApi(param: Request_Verify){


        val retrofitInstance: RetrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.getVerifyOtp(param)

        retrofitData.enqueue(object : Callback<Response_Common> {

            override fun onResponse(
                call: Call<Response_Common>,
                response: Response<Response_Common>
            ) {
//               TODO("Not yet implemented")
                if(response.isSuccessful) {

//                   sharedPreferencesUtil.saveString(Constant.sharedPrefrencesConstant.X_TOKEN,response.body()!!.getData()!!.token)

                    commonResponse.value=response.body()!!

                    //Toast.makeText(activity, response.body()!!.message, Toast.LENGTH_SHORT).show()

                    /*var userDetail=UserDetail()

                    userDetail.profilePic = response.body()!!.getData()!!.result!!.profilePic

                    sharedPreferences.saveUserData(userDetail)*/

                }
                else{

                    loading.visibility = View.GONE
                    val element: JsonElement = Gson().fromJson(response.errorBody()!!.string(), JsonElement::class.java)
                    val jsonObject = element.asJsonObject

                    if(jsonObject.get("code").toString().equals("500")){
//                       if(jsonObject.get("message").asString.equals(activity.resources.getString(R.string.please_registered_your_number_),true)){



                        /*val intent : Intent = Intent(activity, Login::class.java)
                        intent.putExtra("userPhone", param.phoneNumber)
                        activity.startActivity(intent)
                        activity.finish()*/

                    }

                    Toast.makeText(activity, "Wrong OTP", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")

                Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

}