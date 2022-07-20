package com.example.mayas_cafe_admin.fragments.ViewModel

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mayas_cafe_admin.development.retrofit.RetrofitInstance
import com.example.mayasfood.Retrofite.request.Request_notification
import com.example.mayasfood.Retrofite.response.Response_Common
import com.example.mayasfood.Retrofite.response.Response_Notification
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Notification_ViewModel : ViewModel() {

    lateinit var activity: Fragment
    lateinit var loading : ProgressBar

    var commonResponse = MutableLiveData<Response_Common>()
    var commonResponse1 = MutableLiveData<Response_Notification>()
    var commonResponse2 = MutableLiveData<Response_Notification>()
    var token : String = ""

    fun getNotificationData(activity: Fragment, loading : ProgressBar, token : String): MutableLiveData<Response_Common> {

        this.activity = activity
        this.token = token
        this.loading = loading

        getNotificationDataApi()

        return commonResponse
    }

    fun removeNotification(activity: Fragment, notifyId : String): MutableLiveData<Response_Notification>{

        this.activity = activity

        val request_notification : Request_notification = Request_notification()

        request_notification.notificationId = notifyId

        removeNotificationAPI(request_notification)

        return commonResponse1
    }

    fun removeAllNotifications(activity: Fragment): MutableLiveData<Response_Notification>{

        this.activity = activity

        loading.visibility = View.VISIBLE

        removeAllNotificationAPI()

        return commonResponse2
    }

    private fun removeAllNotificationAPI() {

        val retrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.removeAllNotification(token)

        retrofitData.enqueue(object : Callback<Response_Notification?> {
            override fun onResponse(
                call: Call<Response_Notification?>,
                response: Response<Response_Notification?>
            ) {
                if (response.isSuccessful) {

                    commonResponse2.value = response.body()
                    loading.visibility = View.GONE
                    Log.d("Dashboard", "success")
                    Toast.makeText(activity.requireContext(), "Success", Toast.LENGTH_SHORT).show();
                }
                else{
                    // loading.visibility = View.GONE
                    Log.d("Dashboard", "failed")
                    loading.visibility = View.GONE
                    Toast.makeText(activity.requireContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            override fun onFailure(call: Call<Response_Notification?>, t: Throwable) {
                Toast.makeText(activity.requireContext(), t.toString(), Toast.LENGTH_SHORT).show()
                //   loading.visibility = View.GONE
                loading.visibility = View.GONE
            }
        })
    }

    private fun removeNotificationAPI(param: Request_notification) {

        val retrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.removeNotification(token, param)

        //val retrofitData = retrofitInstance.retrofit.removeNotification("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7InVzZXJJZCI6NTQsInBob25lTnVtYmVyIjoiKzkxNzAwMDEwNzg3NiIsImNyZWF0ZWRBdCI6IjIwMjItMDUtMjlUMDc6MTU6MjAuNjYyWiJ9LCJpYXQiOjE2NTM4MDg1MjB9.ut7rbdY-NlDANUJomlbRO5JFwCouktCcyoy7Rk9d6Hg", param)

        retrofitData.enqueue(object : Callback<Response_Notification?> {
            override fun onResponse(
                call: Call<Response_Notification?>,
                response: Response<Response_Notification?>
            ) {
                if (response.isSuccessful) {

                    commonResponse1.value = response.body()

                    Log.d("Dashboard", "success")
                    //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                }
                else{
                    // loading.visibility = View.GONE
                    Log.d("Dashboard", "failed")
                }
            }

            override fun onFailure(call: Call<Response_Notification?>, t: Throwable) {
                Toast.makeText(activity.requireContext(), t.toString(), Toast.LENGTH_SHORT).show()
                //   loading.visibility = View.GONE
            }
        })

    }

    private fun getNotificationDataApi() {

        val retrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.getNotificaiton(token)

        //val retrofitData = retrofitInstance.retrofit.getNotificaiton("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7InVzZXJJZCI6NTQsInBob25lTnVtYmVyIjoiKzkxNzAwMDEwNzg3NiIsImNyZWF0ZWRBdCI6IjIwMjItMDUtMjlUMDc6MTU6MjAuNjYyWiJ9LCJpYXQiOjE2NTM4MDg1MjB9.ut7rbdY-NlDANUJomlbRO5JFwCouktCcyoy7Rk9d6Hg")

        retrofitData.enqueue(object : Callback<Response_Common?> {
            override fun onResponse(
                call: Call<Response_Common?>,
                response: Response<Response_Common?>
            ) {
                if (response.isSuccessful) {

                    commonResponse.value = response.body()

                    Log.d("Dashboard", "success")
                    //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                }
                else{
                    loading.visibility = View.GONE
                    Log.d("Dashboard", "failed")
                }
            }

            override fun onFailure(call: Call<Response_Common?>, t: Throwable) {
                Toast.makeText(activity.requireContext(), t.toString(), Toast.LENGTH_SHORT).show()
                loading.visibility = View.GONE
            }
        })

    }
}