package com.example.mayas_cafe_admin.fragments.ViewModel

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mayas_cafe_admin.Retrofite.request.Request_CreateCategory
import com.example.mayas_cafe_admin.development.retrofit.RetrofitInstance
import com.example.mayasfood.Retrofite.request.Request_CategoryDetails
import com.example.mayasfood.Retrofite.response.Response_Common
import com.google.gson.Gson
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class MenuViewModel : ViewModel() {

    lateinit var activity: Fragment

    val commonResponse = MutableLiveData<Response_Common>()
    val commonResponse1 = MutableLiveData<Response_Common>()

    lateinit var loading : ProgressBar
    var token : String = ""

    fun getTotalCategories(activity : Fragment, token : String, loading : ProgressBar) : MutableLiveData<Response_Common>{

        this.activity = activity
        this.loading = loading
        this.token = token

        val requestCategory = Request_CategoryDetails()
        requestCategory.branchId = "1"

        getTotalCategoriesFromAPI(requestCategory)

        return commonResponse
    }

    fun createCategory(activity : Fragment, token : String, catName : String, catType : String) : MutableLiveData<Response_Common>{

        this.activity = activity
        this.token = token

        val requestCategory = Request_CreateCategory()
        requestCategory.categoryName = catName
        requestCategory.categoryType = catType

        createCategoriesFromAPI(requestCategory)

        return commonResponse1
    }

    private fun createCategoriesFromAPI(param: Request_CreateCategory) {

        val retrofitInstance: RetrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.createCategory(token ,param)

        retrofitData.enqueue(object : retrofit2.Callback<Response_Common> {

            override fun onResponse(
                call: Call<Response_Common>,
                response: Response<Response_Common>
            ) {
                if(response.isSuccessful) {

                    commonResponse1.value=response.body()!!

                }
                else{

                    loading.visibility = View.GONE
                    val element: JsonElement = Gson().fromJson(response.errorBody()!!.string(), JsonElement::class.java)
                    val jsonObject = element.asJsonObject

                    if(jsonObject.get("code").toString().equals("500")){
//                       if(jsonObject.get("message").asString.equals(activity.resources.getString(R.string.please_registered_your_number_),true)){
                        Toast.makeText(activity.context, jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show()
                    }


                }
            }

            override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")

                Toast.makeText(activity.context, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getTotalCategoriesFromAPI(param: Request_CategoryDetails) {

        val retrofitInstance: RetrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.getTotalCategories(token ,param)

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