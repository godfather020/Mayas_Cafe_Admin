package com.example.mayas_cafe_admin.fragments.ViewModel

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mayas_cafe_admin.development.retrofit.RetrofitInstance
import com.example.mayas_cafe_admin.utils.Constants
import com.example.mayasfood.Retrofite.response.Response_Common
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Profile_ViewModel : ViewModel() {

    lateinit var activity: Fragment

    val commonResponse = MutableLiveData<Response_Common>()

    lateinit var loading : ProgressBar


    fun getAdminProfile(activity:Fragment,token:String) : MutableLiveData<Response_Common>{

        this.activity = activity

        val retrofitInstance: RetrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.getAdminProfile(token)

        retrofitData.enqueue(object : Callback<Response_Common> {
            override fun onResponse(
                call: Call<Response_Common>,
                response: Response<Response_Common>
            ) {

                if (response.isSuccessful){

                    commonResponse.value = response.body()

                }
                else{
                    loading.visibility = View.GONE
                    Log.d("error", response.message())
                }

            }

            override fun onFailure(call: Call<Response_Common>, t: Throwable) {

                Log.d("userDetails", t.toString())
                loading.visibility = View.GONE

                Toast.makeText(activity.context, t.toString(), Toast.LENGTH_SHORT).show()

            }
        })

        return commonResponse
    }

}