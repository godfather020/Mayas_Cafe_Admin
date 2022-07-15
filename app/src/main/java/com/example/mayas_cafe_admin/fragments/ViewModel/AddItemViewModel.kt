package com.example.mayas_cafe_admin.fragments.ViewModel

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.Retrofite.request.ProductPrice
import com.example.mayas_cafe_admin.Retrofite.request.Request_AddItem
import com.example.mayas_cafe_admin.development.retrofit.RetrofitInstance
import com.example.mayasfood.Retrofite.response.Response_Common
import com.google.gson.Gson
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class AddItemViewModel : ViewModel() {

    lateinit var activity: Fragment

    val commonResponse = MutableLiveData<Response_Common>()

    lateinit var loading: ProgressBar
    var token: String = ""

    fun createProduct(
        activity: Fragment,
        token: String,
        loading: ProgressBar,
        itemName: String,
        itemDes: String,
        itemCategory: String,
        itemSize: ArrayList<String>,
        itemPrice: ArrayList<String>,
        itemOffer: ArrayList<String>
    ): MutableLiveData<Response_Common> {

        this.activity = activity
        this.loading = loading
        this.token = token

        val request_AddItem = Request_AddItem()

        request_AddItem.productName = itemName
        request_AddItem.productDesc = itemDes
        request_AddItem.categoryId = itemCategory
        request_AddItem.branchId = "1"

        for (i in itemSize.indices) {

            val productPrice = ProductPrice(itemPrice[i], itemOffer[i], "1", itemSize[i])

            request_AddItem.productPrice.add(productPrice)
        }

        createProductFromAPI(request_AddItem)


        return commonResponse
    }

    private fun createProductFromAPI(param: Request_AddItem) {

        val retrofitInstance: RetrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.createProduct(token, param)

        retrofitData.enqueue(object : retrofit2.Callback<Response_Common> {

            override fun onResponse(
                call: Call<Response_Common>,
                response: Response<Response_Common>
            ) {


                if (response.isSuccessful) {

                    commonResponse.value = response.body()!!

                } else {

                    loading.visibility = View.GONE
                    val element: JsonElement =
                        Gson().fromJson(response.errorBody()!!.string(), JsonElement::class.java)
                    val jsonObject = element.asJsonObject

                    if (jsonObject.get("code").toString().equals("500")) {
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