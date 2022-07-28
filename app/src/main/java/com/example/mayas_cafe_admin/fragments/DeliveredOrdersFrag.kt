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
import com.example.mayas_cafe_admin.fragments.ViewModel.DeliveredOrders_ViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_DO
import com.example.mayas_cafe_admin.utils.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DeliveredOrdersFrag : Fragment() {

    private var recycleViewModels = ArrayList<RecycleModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recycleViewAdapterDO: RecycleView_DO
    private lateinit var search: MenuItem
    private lateinit var deliveredOrdersViewModel: DeliveredOrders_ViewModel
    private lateinit var mainActivity: MainActivity
    private var token: String? = ""
    private lateinit var loadingDelivered: ProgressBar
    private lateinit var refreshDelivered: SwipeRefreshLayout
    private lateinit var orderId: ArrayList<String>
    private lateinit var orderAmt: ArrayList<String>
    private lateinit var orderQuantity: ArrayList<String>
    private lateinit var orderPickTime: ArrayList<String>
    private lateinit var orderImg: ArrayList<String>
    private lateinit var payStatus : ArrayList<String>

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
        val view: View = inflater.inflate(R.layout.fragment_delivered_orders_frag, container, false)

        deliveredOrdersViewModel =
            ViewModelProvider(this).get(DeliveredOrders_ViewModel::class.java)

        mainActivity = activity as MainActivity

        recyclerView = view.findViewById(R.id.deliveredOrder_rv)
        loadingDelivered = view.findViewById(R.id.loading_delivered)
        refreshDelivered = view.findViewById(R.id.refresh_delivered)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        //setUpDeliveredOrderRv()

        setHasOptionsMenu(true)

        init()

        refreshDelivered.setOnRefreshListener {

            init()

            refreshDelivered.isRefreshing = false
        }

        return view
    }

    private fun init() {

        loadingDelivered.visibility = View.VISIBLE

        token =
            mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0)
                .getString(
                    Constants.sharedPrefrencesConstant.DEVICE_TOKEN, ""
                )

        orderId = ArrayList<String>()
        orderAmt = ArrayList<String>()
        orderQuantity = ArrayList<String>()
        orderPickTime = ArrayList<String>()
        orderImg = ArrayList<String>()
        payStatus = ArrayList()

        getDeliveredOrders()
    }

    private fun getDeliveredOrders() {

        if (token != null) {

            deliveredOrdersViewModel.getDeliveredOrders(this, token.toString(), loadingDelivered)
                .observe(viewLifecycleOwner) {

                    if (it != null) {

                        if (it.getSuccess()!!) {

                            orderImg.clear()
                            orderAmt.clear()
                            orderQuantity.clear()
                            orderPickTime.clear()
                            orderId.clear()
                            recycleViewModels.clear()
                            payStatus.clear()

                            for (i in it.getData()!!.ListOrderResponce!!.indices) {

                                if (it.getData()!!.ListOrderResponce!![i].orderStatus.equals("5") && it.getData()!!.ListOrderResponce!![i].cancelStatus == false) {

                                    orderId.add("#" + it.getData()!!.ListOrderResponce!![i].id.toString())
                                    orderAmt.add("$" + it.getData()!!.ListOrderResponce!![i].amount.toString())
                                    orderQuantity.add(it.getData()!!.ListOrderResponce!![i].toalQuantity.toString())
                                    Log.d("paystatus", it.getData()!!.ListOrderResponce!![i].paymentStatus.toString())
                                    payStatus.add(it.getData()!!.ListOrderResponce!![i].paymentStatus.toString())

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

                                        orderImg.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![0].Product!!.productPic.toString())
                                    } else {

                                        orderImg.add("default.png")
                                    }
                                }
                            }
                            loadingDelivered.visibility = View.GONE
                            setUpDeliveredOrderRv()
                        }
                    }
                }
        }
    }

    private fun setUpDeliveredOrderRv() {

        for (i in orderId.indices) {

            recycleViewModels.add(
                RecycleModel(
                    orderImg[i],
                    orderId[i],
                    orderPickTime[i],
                    orderAmt[i],
                    "Delivered",
                    orderQuantity[i],
                    orderImg[i],
                    payStatus[i]
                )
            )

            recycleViewAdapterDO = RecycleView_DO(activity, recycleViewModels)
            recyclerView.adapter = recycleViewAdapterDO
            recycleViewAdapterDO.notifyItemInserted(i)
        }
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
                recycleViewAdapterDO.filter.filter(newText)
                return false
            }
        })
    }
}