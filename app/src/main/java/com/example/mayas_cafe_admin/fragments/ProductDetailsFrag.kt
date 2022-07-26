package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.ProductDetails_ViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_PD
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_RTP
import com.example.mayas_cafe_admin.utils.Constants
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.ArrayList


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

        mainActivity.toolbar_const.setTitle("Product Details")
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        recyclerView = view.findViewById(R.id.productDetails_rv)
        loadingDetails = view.findViewById(R.id.loading_details)
        custImg = view.findViewById(R.id.custImg)
        custName = view.findViewById(R.id.customerName)
        custPhone = view.findViewById(R.id.customerPhone)

        loadingDetails.visibility = View.VISIBLE

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        orderId = view.findViewById(R.id.detailsOrderId)
        orderStatus = view.findViewById(R.id.detailsOrderStatus)
        orderPickUp = view.findViewById(R.id.detailsPickUp)

        orderId.text = Constants.orderId
        orderStatus.text = Constants.orderStatus
        orderPickUp.text = Constants.orderPickUp

        /*Picasso.get()
            .load(Constants.AdminProduct_Path+Constants.userPic)
            .into(custImg)*/

        //setUpDetailsRv()

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

                                        for (j in it.getData()!!.ListOrderResponce!![i].Orderlists!!.indices) {

                                            getUserDetails(it.getData()!!.ListOrderResponce!![i].userId.toString())

                                            orderImg.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![j].Productprice!!.productPic.toString())
                                            orderSize.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![j].Productprice!!.productsize.toString())
                                            orderAmt.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![j].totalAmount.toString())
                                            orderQuantity.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![j].noItems.toString())
                                            orderName.add("Butter Paneer")
                                        }
                                    }
                                }
                            }
                            loadingDetails.visibility = View.GONE
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


}