package com.example.mayas_cafe_admin.activities.ViewModel

import android.content.Intent
import android.telecom.Call
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mayas_cafe_admin.Retrofite.request.Request_OTP
import com.example.mayas_cafe_admin.development.retrofit.RetrofitInstance
import com.example.mayasfood.Retrofite.response.Response_Common
import retrofit2.Response
import javax.security.auth.callback.Callback

class Login_ViewModel : ViewModel() {

    lateinit var activity: AppCompatActivity

    val commonResponse = MutableLiveData<Response_Common>()

    fun get_otp(activity:AppCompatActivity,phoneNumber:String):MutableLiveData<Response_Common>{

        this.activity=activity

        val requestLogin= Request_OTP()

        requestLogin.phoneNumber=phoneNumber

        getResposneOfLoginFromApi(requestLogin)

        return commonResponse
    }

    fun getResposneOfLoginFromApi(param:Request_OTP){


        val phoneNumber= param.phoneNumber

        val retrofitInstance: RetrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.getOtp(param)


        retrofitData.enqueue(object : retrofit2.Callback<Response_Common> {

            override fun onResponse(
                call: retrofit2.Call<Response_Common>,
                response: Response<Response_Common>
            ) {
//               TODO("Not yet implemented")
                if(response.isSuccessful) {

                    commonResponse.value=response.body()!!
                    Log.d("OTP", commonResponse.value!!.getData()!!.otp)

                    Toast.makeText(activity, response.body()!!.message, Toast.LENGTH_SHORT).show()

                }else {

                   /* val intent: Intent = Intent(activity, Registration::class.java)
                    intent.putExtra("UserPhone", phoneNumber)
                    startActivity(activity, intent, null)
                    activity.finish()*/

                    Toast.makeText(activity, "User not found please contact Admin", Toast.LENGTH_SHORT).show()

                    }

                }

            override fun onFailure(call: retrofit2.Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")

                Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

}