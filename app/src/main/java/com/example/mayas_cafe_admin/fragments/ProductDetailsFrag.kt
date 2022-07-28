package com.example.mayas_cafe_admin.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.Retrofite.request.Request_UpdateOrder
import com.example.mayas_cafe_admin.Retrofite.request.Request_updateOrderDetails
import com.example.mayas_cafe_admin.Retrofite.response.Response_Update_Status
import com.example.mayas_cafe_admin.development.retrofit.RetrofitInstance
import com.example.mayas_cafe_admin.fragments.ViewModel.ProductDetails_ViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_PD
import com.example.mayas_cafe_admin.utils.Constants
import com.example.mayasfood.Retrofite.response.Response_Common
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductDetailsFrag : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    lateinit var recyclerView: RecyclerView
    lateinit var recycleView_adapter_PD: RecycleView_PD
    lateinit var orderId: TextView
    lateinit var orderStatus: TextView
    lateinit var orderPickUp: TextView
    lateinit var mainActivity: MainActivity
    lateinit var productdetailsViewmodel: ProductDetails_ViewModel
    var token: String? = ""
    lateinit var loadingDetails: ProgressBar
    lateinit var orderName: ArrayList<String>
    lateinit var orderAmt: ArrayList<String>
    lateinit var orderQuantity: ArrayList<String>
    lateinit var orderSize: ArrayList<String>
    lateinit var orderImg: ArrayList<String>
    lateinit var custImg : CircleImageView
    lateinit var custName : TextView
    lateinit var custPhone : TextView
    lateinit var payTxt : TextView
    lateinit var payRadioGroup : RadioGroup
    lateinit var payCash : RadioButton
    lateinit var payCard : RadioButton
    lateinit var completeOrderBtn : Button
    lateinit var args : String
    lateinit var transactionTxt : TextView
    lateinit var transactionId : TextView
    lateinit var payStatus : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_product_details_frag, container, false)

        productdetailsViewmodel = ViewModelProvider(this).get(ProductDetails_ViewModel::class.java)

        mainActivity = activity as MainActivity

        mainActivity.toolbar_const.setTitle("Order Details")
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        recyclerView = view.findViewById(R.id.productDetails_rv)
        loadingDetails = view.findViewById(R.id.loading_details)
        custImg = view.findViewById(R.id.custImg)
        custName = view.findViewById(R.id.customerName)
        custPhone = view.findViewById(R.id.customerPhone)
        payTxt = view.findViewById(R.id.payTxt)
        payRadioGroup = view.findViewById(R.id.payRadioGroup)
        completeOrderBtn = view.findViewById(R.id.completeOrder_btn)
        payCard = view.findViewById(R.id.payCard)
        payCash = view.findViewById(R.id.payCash)
        payStatus = view.findViewById(R.id.payStatus_details)
        transactionTxt = view.findViewById(R.id.transaction_txt)
        transactionId = view.findViewById(R.id.transaction_id)

        loadingDetails.visibility = View.VISIBLE

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        orderId = view.findViewById(R.id.detailsOrderId)
        orderStatus = view.findViewById(R.id.detailsOrderStatus)
        orderPickUp = view.findViewById(R.id.detailsPickUp)

        orderId.text = Constants.orderId
        orderStatus.text = Constants.orderStatus
        orderPickUp.text = Constants.orderPickUp


        if (arguments != null){

            args = arguments?.getString("isComplete", "").toString()

            if (args == "complete"){

                payRadioGroup.visibility = View.VISIBLE
                payTxt.visibility = View.VISIBLE
                completeOrderBtn.visibility = View.VISIBLE
            }
            else{

                payRadioGroup.visibility = View.GONE
                payTxt.visibility = View.GONE
                completeOrderBtn.visibility = View.GONE
            }
        }
        else{

            payRadioGroup.visibility = View.GONE
            payTxt.visibility = View.GONE
            completeOrderBtn.visibility = View.GONE
        }

        token =
            mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0)
                .getString(
                    Constants.sharedPrefrencesConstant.DEVICE_TOKEN, ""
                )

        orderName = ArrayList<String>()
        orderAmt = ArrayList<String>()
        orderQuantity = ArrayList<String>()
        orderSize = ArrayList<String>()
        orderImg = ArrayList<String>()

        completeOrderBtn.setOnClickListener {

            updateOrderStatus()
        }

        getOrderDetails()

        return view
    }

    private fun getOrderDetails() {

        if (token != null) {

            productdetailsViewmodel.getOrders(this, token.toString(), loadingDetails)
                .observe(viewLifecycleOwner, Observer {

                    if (it != null) {

                        if (it.getSuccess()!!) {

                            orderImg.clear()
                            orderAmt.clear()
                            orderQuantity.clear()
                            orderSize.clear()
                            orderName.clear()
                            recycleView_models.clear()

                            for (i in it.getData()!!.ListOrderResponce!!.indices) {

                                Log.d("id", Constants.orderId.substring(1))

                                if (it.getData()!!.ListOrderResponce!![i].id!!.toString() == Constants.orderId.substring(
                                        1
                                    )
                                ) {

                                    if (it.getData()!!.ListOrderResponce!![i].Orderlists!!.isNotEmpty()) {

                                        val tId = it.getData()!!.ListOrderResponce!![i].transactionId.toString()
                                        val payS = it.getData()!!.ListOrderResponce!![i].paymentStatus.toString()

                                        if (tId.isNotEmpty() && tId != "null"){

                                            transactionTxt.visibility = View.VISIBLE
                                            transactionId.visibility = View.VISIBLE
                                            transactionId.text = tId
                                        }
                                        else {

                                            transactionTxt.visibility = View.GONE
                                            transactionId.visibility = View.GONE
                                        }

                                        if (payS == "1"){

                                            payTxt.visibility = View.GONE
                                            payRadioGroup.visibility = View.GONE
                                            payStatus.text = "Paid"
                                            payStatus.setTextColor(resources.getColor(R.color.Green))
                                        }
                                        else {

                                            payStatus.text = "UnPaid"
                                            payStatus.setTextColor(resources.getColor(R.color.Red))
                                        }

                                        for (j in it.getData()!!.ListOrderResponce!![i].Orderlists!!.indices) {

                                            getUserDetails(it.getData()!!.ListOrderResponce!![i].userId.toString())

                                            orderImg.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![j].Product!!.productPic.toString())
                                            orderSize.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![j].Productprice!!.productsize.toString())
                                            orderAmt.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![j].totalAmount.toString())
                                            orderQuantity.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![j].noItems.toString())
                                            orderName.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![j].Product!!.productName.toString())
                                        }
                                    }
                                }
                            }

                            setUpDetailsRv()
                        }
                    }
                })
        }
    }

    private fun getUserDetails(userId: String) {

        productdetailsViewmodel.getUserInfo(this, token.toString(), userId).observe(viewLifecycleOwner){

            if (it != null){

                if (it.getSuccess()!!){

                    custName.text = it.getData()!!.user!!.userName
                    custPhone.text = it.getData()!!.user!!.phoneNumber

                    Picasso.get()
                        .load(Constants.AdminProfile_Path+it.getData()!!.user!!.profilePic.toString())
                        .into(custImg)

                    loadingDetails.visibility = View.GONE
                }
            }
        }
    }

    private fun setUpDetailsRv() {

        for (i in orderName.indices) {

            recycleView_models.add(RecycleModel(orderAmt[i], orderImg[i], orderName[i], orderSize[i], orderQuantity[i]))
        }

        recycleView_adapter_PD = RecycleView_PD(activity, recycleView_models)
        recyclerView.adapter = recycleView_adapter_PD
        recycleView_adapter_PD.notifyDataSetChanged()
    }

    private fun updateOrderStatus() {
        val token =
            mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0)
                .getString(
                    Constants.sharedPrefrencesConstant.DEVICE_TOKEN, ""
                )
        val request_updateOrder = Request_updateOrderDetails()
        request_updateOrder.orderStatus = "5"
        val orderId1: String = orderId.text.toString()
        Log.d("Id", orderId1.substring(1))
        request_updateOrder.orderId = orderId1.substring(1)
        request_updateOrder.branchId = "1"
        request_updateOrder.orderStatus = "5"
        request_updateOrder.paymentStatus = "1"
        var payMethod = ""
        if (payCash.isChecked){
            payMethod = "Cash"
        }
        else{

            payMethod = "Card"
        }
        request_updateOrder.paymentMethod = payMethod
        val retrofitInstance = RetrofitInstance()
        if (token != null) {
            val retrofitInstance1 =
                retrofitInstance.retrofit.updateOrderDetails(token, request_updateOrder)
            retrofitInstance1.enqueue(object : Callback<Response_Common?> {
                override fun onResponse(
                    call: Call<Response_Common?>,
                    response: Response<Response_Common?>
                ) {
                    if (response.isSuccessful) {

                        Toast.makeText(context, "Order Complete", Toast.LENGTH_SHORT).show()

                        Log.d("error1", response.message())
                        mainActivity.supportFragmentManager.popBackStackImmediate()
                    } else {
                        Log.d("error", response.message())
                    }
                }

                override fun onFailure(call: Call<Response_Common?>, t: Throwable) {
                    Log.d("error", t.toString())
                }
            })
        }
    }
}