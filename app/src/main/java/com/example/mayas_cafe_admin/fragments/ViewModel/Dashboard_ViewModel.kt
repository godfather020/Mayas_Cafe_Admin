package com.example.mayas_cafe_admin.fragments.ViewModel

import android.net.DnsResolver
import android.os.Handler
import android.view.InputQueue
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mayas_cafe_admin.development.retrofit.RetrofitInstance
import com.example.mayasfood.Retrofite.request.Request_Branch
import com.example.mayasfood.Retrofite.request.Request_OrderDetails
import com.example.mayasfood.Retrofite.response.Response_Common
import com.google.gson.Gson
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Dashboard_ViewModel : ViewModel() {

    lateinit var activity: Fragment

    val commonResponse = MutableLiveData<Response_Common>()
    val commonResponse1 = MutableLiveData<Response_Common>()

    lateinit var loading : ProgressBar
    var token : String = ""

    fun getRecentOrders(activity : Fragment, token : String, loading : ProgressBar) : MutableLiveData<Response_Common>{

        this.activity = activity
        this.loading = loading
        this.token = token

        val request_branch = Request_Branch()
        request_branch.branchId = "1"

        getRecentOrdersFromAPI(request_branch)

        return commonResponse
    }

    private fun getRecentOrdersFromAPI(param: Request_Branch) {

        val retrofitInstance: RetrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.getAllOrders(token ,param)

        retrofitData.enqueue(object : retrofit2.Callback<Response_Common> {

            override fun onResponse(
                call: Call<Response_Common>,
                response: Response<Response_Common>
            ) {


                if(response.isSuccessful) {

                    commonResponse.value=response.body()!!

                }
                else{

                    loading.visibility = View.GONE
                    val element: JsonElement = Gson().fromJson(response.errorBody()!!.string(), JsonElement::class.java)
                    val jsonObject = element.asJsonObject

                    if(jsonObject.get("code").toString().equals("500")){
//                       if(jsonObject.get("message").asString.equals(activity.resources.getString(R.string.please_registered_your_number_),true)){

                    }

                    Toast.makeText(activity.context, "Update Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")

                Toast.makeText(activity.context, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })


    }

}