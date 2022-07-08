package com.example.mayas_cafe_admin.fragments.ViewModel

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lottry.data.remote.retrofit.response.UserDetail
import com.example.mayas_cafe_admin.FirebaseCloudMsg
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.Retrofite.request.Request_Verify
import com.example.mayas_cafe_admin.development.retrofit.RetrofitInstance
import com.example.mayas_cafe_admin.utils.Constants
import com.example.mayasfood.Retrofite.request.Request_updateProfile
import com.example.mayasfood.Retrofite.response.Response_Common
import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class Edit_Profile_ViewModel : ViewModel() {

    lateinit var activity: Fragment

    val commonResponse = MutableLiveData<Response_Common>()
    val commonResponse1 = MutableLiveData<Response_Common>()

    lateinit var loading : ProgressBar

    var token:String = ""

    fun update_profile(activity: Fragment, userName: String, userEmail : String, userAddress: String, token:String, loading:ProgressBar):MutableLiveData<Response_Common>{

        this.activity=activity

        this.loading = loading
        this.token = token

        val requestUpdate = Request_updateProfile()

        requestUpdate.userName = userName
        requestUpdate.email = userEmail
        requestUpdate.address = userAddress

        getResposneOfUpdateProfileFromApi(requestUpdate)

        return commonResponse
    }

    fun getResposneOfUpdateProfileFromApi(param: Request_updateProfile){


        val retrofitInstance: RetrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.updateAdminProfile(token ,param)

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

                    Toast.makeText(activity.context, "Update Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")

                Toast.makeText(activity.context, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun set_profileImage(activity:Fragment,imgUrl: File, loading: ProgressBar):MutableLiveData<Response_Common>{

        this.activity=activity
        this.loading = loading

        //val reqFile: RequestBody = imgUrl.asRequestBody(".png".toMediaTypeOrNull())
        val reqFile: RequestBody = RequestBody.create("image/jpg".toMediaTypeOrNull(), imgUrl);

        val body: MultipartBody.Part = MultipartBody.Part.createFormData("image",imgUrl.name,reqFile)
        Log.d("imageFile", imgUrl.toString())
        Log.d("imageFile", reqFile.toString())
        Log.d("imageFile", body.toString())
        Log.d("imageFile", Constants.DEVICE_TOKEN)

        val retrofitInstance: RetrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.setProfileImg(Constants.DEVICE_TOKEN,body)


        retrofitData!!.enqueue(object : Callback<Response_Common> {

            override fun onResponse(
                call: Call<Response_Common>,
                response: Response<Response_Common>
            ) {
//               TODO("Not yet implemented")
                if(response.isSuccessful) {
                    Log.d("imgUploaded", "ImgUploaded")
//                   binding.progessBar.visibility= View.GONE
                    commonResponse1.value=response.body()!!
                    var userDetail= UserDetail()

                    if(response.body()!!.getData()!!.result!!.profilePic!=null) {
                        userDetail.profilePic = response.body()!!.getData()!!.result!!.profilePic
                    }
                    Log.d("imgUpload", userDetail.profilePic)

                }else {
                    val element: JsonElement = Gson().fromJson(response.errorBody()!!.string(), JsonElement::class.java)
                    val jsonObject = element.asJsonObject
                    loading.visibility = View.GONE
                    if(jsonObject.get("code").toString().equals("500")){
//                       if(jsonObject.get("message").asString.equals(activity.resources.getString(R.string.please_registered_your_number_),true)){
                        //activity.showToast(jsonObject.get("message").asString)


                        Toast.makeText(activity.context, jsonObject.get("message").asString, Toast.LENGTH_SHORT).show()
                        Log.d("imgUploaded", jsonObject.get("message").asString)
//                       }
                    }

                }
            }

            override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")
//               binding.progessBar.visibility= View.GONE
                Toast.makeText(activity.context, t.toString(), Toast.LENGTH_SHORT).show()
                loading.visibility = View.GONE
                Log.d("imgUploaded", t.toString())
            }
        })

        return commonResponse1
    }

}