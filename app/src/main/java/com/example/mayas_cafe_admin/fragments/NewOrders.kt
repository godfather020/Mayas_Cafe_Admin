package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.NewOrders_ViewModel
import com.example.mayas_cafe_admin.fragments.ViewModel.ProductDetails_ViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_NO
import com.example.mayas_cafe_admin.utils.Constants
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewOrders : Fragment() {

    private var recycleViewModels = ArrayList<RecycleModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recycleViewAdapterNO: RecycleView_NO
    private lateinit var search: MenuItem
    private lateinit var newOrdersView: NewOrders_ViewModel
    private lateinit var mainActivity: MainActivity
    private var token: String? = ""
    private lateinit var loadingNew: ProgressBar
    private lateinit var orderId: ArrayList<String>
    private lateinit var orderAmt: ArrayList<String>
    private lateinit var orderQuantity: ArrayList<String>
    private lateinit var orderPickTime: ArrayList<String>
    private lateinit var custImg : ArrayList<String>
    private lateinit var orderImg: ArrayList<String>
    private lateinit var refreshNew: SwipeRefreshLayout
    lateinit var productdetailsViewmodel: ProductDetails_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_new_orders, container, false)

        mainActivity = (activity as MainActivity)

        newOrdersView = ViewModelProvider(this).get(NewOrders_ViewModel::class.java)
        productdetailsViewmodel = ViewModelProvider(this).get(ProductDetails_ViewModel::class.java)

        recyclerView = view.findViewById(R.id.runOrder_rv)
        loadingNew = view.findViewById(R.id.loading_new)
        refreshNew = view.findViewById(R.id.refresh_new)

        loadingNew.visibility = View.VISIBLE

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        setHasOptionsMenu(true)

        init()

        refreshNew.setOnRefreshListener {

            init()


        }

        return view
    }

    private fun init() {

        token =
            mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0)
                .getString(
                    Constants.sharedPrefrencesConstant.DEVICE_TOKEN, ""
                )

        loadingNew.visibility = View.VISIBLE

        orderId = ArrayList()
        orderAmt = ArrayList()
        orderQuantity = ArrayList()
        orderPickTime = ArrayList()
        orderImg = ArrayList()
        custImg = ArrayList()

        getNewOrders()

    }

    private fun getNewOrders() {

        if (token != null) {

            newOrdersView.getNewOrders(this, token.toString(), loadingNew)
                .observe(viewLifecycleOwner) {

                    if (it != null) {

                        if (it.getSuccess()!!) {

                            orderImg.clear()
                            orderAmt.clear()
                            orderQuantity.clear()
                            orderPickTime.clear()
                            orderId.clear()
                            recycleViewModels.clear()
                            custImg.clear()

                            for (i in it.getData()!!.ListOrderResponce!!.indices) {

                                if (it.getData()!!.ListOrderResponce!![i].orderStatus.equals("0") && it.getData()!!.ListOrderResponce!![i].cancelStatus == false) {

                                    //custImg.add(getUserImg(it.getData()!!.ListOrderResponce!![i].userId.toString()))
                                    orderId.add("#" + it.getData()!!.ListOrderResponce!![i].id.toString())
                                    orderAmt.add("$" + it.getData()!!.ListOrderResponce!![i].amount.toString())
                                    orderQuantity.add(it.getData()!!.ListOrderResponce!![i].toalQuantity.toString())

                                    Log.d("custImg", custImg.size.toString())

                                    val pickTime =
                                        it.getData()!!.ListOrderResponce!![i].pickupAt.toString()

                                    val input = SimpleDateFormat(
                                        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                                        Locale.getDefault()
                                    )
                                    val output =
                                        SimpleDateFormat("dd-MM-yy hh:mm a", Locale.getDefault())

                                    var d: Date? = null
                                    try {
                                        d = input.parse(pickTime)
                                    } catch (e: ParseException) {
                                        e.printStackTrace()
                                    }
                                    val formatted: String = output.format(d!!)
                                    Log.d("DATE", "" + formatted)

                                    orderPickTime.add(formatted)

                                    if (it.getData()!!.ListOrderResponce!![i].Orderlists!!.isNotEmpty()) {
                                        custImg.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![0].Product!!.productPic.toString())
                                        orderImg.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![0].Product!!.productPic.toString())
                                    } else {
                                        orderImg.add("default.png")
                                        custImg.add("default.png")
                                    }
                                }
                            }
                            loadingNew.visibility = View.GONE

                            setUpRunOrderRv()
                        }
                    }
                }
        }
    }

    private fun getUserImg(userId: String): String {

        var userImg = ""

        productdetailsViewmodel.getUserInfo(this, token.toString(), userId).observe(viewLifecycleOwner){

            if (it != null){

                if (it.getSuccess()!!){

                    //custImg.add(it.getData()!!.user!!.profilePic.toString())
                    userImg =  it.getData()!!.user!!.profilePic.toString()
                }
            }
        }

        return userImg
    }

    private fun setUpRunOrderRv() {

        for (i in orderId.indices) {

            recycleViewModels.add(
                RecycleModel(
                    custImg[i],
                    orderId[i],
                    orderPickTime[i],
                    orderAmt[i],
                    "New Order",
                    orderQuantity[i],
                    orderImg[i]
                )
            )

            recycleViewAdapterNO = RecycleView_NO(activity, recycleViewModels)
            recyclerView.adapter = recycleViewAdapterNO
            recycleViewAdapterNO.notifyItemInserted(i)
        }

        refreshNew.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        search = menu.findItem(R.id.search)
        val searchView: androidx.appcompat.widget.SearchView =
            search.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                recycleViewAdapterNO.filter.filter(newText)
                return false
            }
        })
    }
}