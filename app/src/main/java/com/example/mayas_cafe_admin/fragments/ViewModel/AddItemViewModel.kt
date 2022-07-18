package com.example.mayas_cafe_admin.fragments.ViewModel

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lottry.data.remote.retrofit.request.RequestUpdateProduct
import com.example.mayas_cafe_admin.Retrofite.request.ProductPrice
import com.example.mayas_cafe_admin.Retrofite.request.RequestUpdateProductSize
import com.example.mayas_cafe_admin.Retrofite.request.RequestUploadItemImg
import com.example.mayas_cafe_admin.Retrofite.request.Request_AddItem
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


class AddItemViewModel : ViewModel() {

    lateinit var activity: Fragment

    val commonResponse = MutableLiveData<Response_Common>()
    var updateStatus = MutableLiveData<Response_Update_Status>()

    lateinit var loading: ProgressBar
    var token: String = ""
    lateinit var itemImg: File
    var isEdit : String = ""

    fun createProduct(
        activity: Fragment,
        token: String,
        loading: ProgressBar,
        itemName: String,
        itemDes: String,
        itemCategory: String,
        itemSize: ArrayList<String>,
        itemPrice: ArrayList<String>,
        itemOffer: ArrayList<String>,
        itemImg: File
    ): MutableLiveData<Response_Update_Status> {

        this.activity = activity
        this.loading = loading
        this.token = token
        this.itemImg = itemImg

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


        return updateStatus
    }

    fun editProduct (
        activity: Fragment,
        token: String,
        loading: ProgressBar,
        itemName: String,
        itemDes: String,
        itemCategory: String,
        itemSize: ArrayList<String>,
        itemPrice: ArrayList<String>,
        itemOffer: ArrayList<String>,
        itemImg: File,
        itemId : String,
        edit : String
    ): MutableLiveData<Response_Update_Status> {

        this.activity = activity
        this.loading = loading
        this.token = token
        this.itemImg = itemImg
        this.isEdit = edit

        val updateProduct = RequestUpdateProduct()

        updateProduct.productId = itemId
        updateProduct.branchId = "1"
        updateProduct.status = "1"
        updateProduct.productName = itemName
        updateProduct.productDesc = itemDes
        updateProduct.categoryId = itemCategory

        for (i in itemSize.indices) {

            val productPrice = ProductPrice(itemPrice[i], itemOffer[i], "1", itemSize[i])

            updateProduct.productPrice.add(productPrice )
        }

        if (!itemImg.name.contains("temp")) {

            uploadItemImg(itemId)
        }

        updateProductFromAPI(updateProduct)

        if (itemSize.size > 0) {

            val requestUpdateProductSize = RequestUpdateProductSize()

            requestUpdateProductSize.productId = itemId
            requestUpdateProductSize.branchId = "1"

            for (i in itemSize.indices) {

                val productPrice = ProductPrice(itemPrice[i], itemOffer[i], "1", itemSize[i])

                requestUpdateProductSize.productPrice.add(productPrice )
            }

            updateProductSize(requestUpdateProductSize)
        }

        //updateProductFromAPI(updateProduct)

        return updateStatus
    }

    private fun updateProductSize(param: RequestUpdateProductSize) {

        val retrofitInstance: RetrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.updateProductSize(token, param)

        retrofitData.enqueue(object : retrofit2.Callback<Response_Update_Status> {

            override fun onResponse(
                call: Call<Response_Update_Status>,
                response: Response<Response_Update_Status>
            ) {


                if (response.isSuccessful) {

                    //uploadItemImg(param.productId.toString())

                    updateStatus.value = response.body()!!
                    //commonResponse.value = response.body()!!
                    //updateProduct(response.body()!!.getData()!!.id.toString())

                } else {

                    loading.visibility = View.GONE
                    val element: JsonElement =
                        Gson().fromJson(response.errorBody()!!.string(), JsonElement::class.java)
                    val jsonObject = element.asJsonObject

                    if (jsonObject.get("code").toString().equals("500")) {
//                       if(jsonObject.get("message").asString.equals(activity.resources.getString(R.string.please_registered_your_number_),true)){
                        Toast.makeText(activity.context, jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show()
                    }

                    //Toast.makeText(activity.context, "Update Failed 12", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Response_Update_Status>, t: Throwable) {
//               TODO("Not yet implemented")

                Toast.makeText(activity.context, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun updateProduct(productId: String): MutableLiveData<Response_Common> {

        val updateProduct = RequestUpdateProduct()

        updateProduct.productId = productId
        updateProduct.branchId = "1"
        updateProduct.status = "1"

        updateProductFromAPI(updateProduct)


        return commonResponse
    }

    fun uploadItemImg(productId: String) {

        val uploadItemImg = RequestUploadItemImg()

        uploadItemImg.productId = productId

        val reqFile: RequestBody = RequestBody.create("image/jpg".toMediaTypeOrNull(), itemImg)

        val id: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            productId
        )

        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", itemImg.name, reqFile)
        //val pId: MultipartBody.Part = MultipartBody.Part.createFormData("productId", productId)

        val retrofitInstance: RetrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.uploadItemImg(token, body, id)

        retrofitData!!.enqueue(object : retrofit2.Callback<Response_Common> {

            override fun onResponse(
                call: Call<Response_Common>,
                response: Response<Response_Common>
            ) {


                if (response.isSuccessful) {

                    //Toast.makeText(activity.context, "Upload Success", Toast.LENGTH_SHORT).show()

                    if (isEdit == "yes"){

                        //updateProductFromAPI()
                    }
                    else {

                        updateProduct(productId)
                    }
                    //updateStatus.value = response.body()!!

                    //commonResponse.value = response.body()!!
                    //updateProduct(response.body()!!.getData()!!.id.toString())
                    //Toast.makeText(activity.context, "Upload Success", Toast.LENGTH_SHORT).show()

                } else {

                    loading.visibility = View.GONE
                    val element: JsonElement =
                        Gson().fromJson(response.errorBody()!!.string(), JsonElement::class.java)
                    val jsonObject = element.asJsonObject

                    if (jsonObject.get("code").toString().equals("500")) {
//                       if(jsonObject.get("message").asString.equals(activity.resources.getString(R.string.please_registered_your_number_),true)){

                    }

                    Toast.makeText(activity.context, "Upload Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Response_Common>, t: Throwable) {
//               TODO("Not yet implemented")

                Toast.makeText(activity.context, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun updateProductFromAPI(param: RequestUpdateProduct) {

        val retrofitInstance: RetrofitInstance = RetrofitInstance()
        val retrofitData = retrofitInstance.retrofit.updateProduct(token, param)

        retrofitData.enqueue(object : retrofit2.Callback<Response_Update_Status> {

            override fun onResponse(
                call: Call<Response_Update_Status>,
                response: Response<Response_Update_Status>
            ) {


                if (response.isSuccessful) {

                    //uploadItemImg(param.productId.toString())
                    updateStatus.value = response.body()!!

                    //commonResponse.value = response.body()!!
                    //updateProduct(response.body()!!.getData()!!.id.toString())

                } else {

                    loading.visibility = View.GONE
                    val element: JsonElement =
                        Gson().fromJson(response.errorBody()!!.string(), JsonElement::class.java)
                    val jsonObject = element.asJsonObject

                    if (jsonObject.get("code").toString().equals("500")) {
//                       if(jsonObject.get("message").asString.equals(activity.resources.getString(R.string.please_registered_your_number_),true)){

                    }

                    //Toast.makeText(activity.context, "Update Failed 12", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Response_Update_Status>, t: Throwable) {
//               TODO("Not yet implemented")

                Toast.makeText(activity.context, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
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

                    //commonResponse.value = response.body()!!
                    uploadItemImg(response.body()!!.getData()!!.id.toString())
                    //updateProduct(response.body()!!.getData()!!.id.toString())

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