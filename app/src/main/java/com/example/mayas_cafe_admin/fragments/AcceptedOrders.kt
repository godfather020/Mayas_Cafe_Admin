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
import com.example.mayas_cafe_admin.fragments.ViewModel.AcceptedOrders_ViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_AO
import com.example.mayas_cafe_admin.utils.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AcceptedOrders : Fragment() {

    private var recycleViewModels = ArrayList<RecycleModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recycleViewAdapterAO: RecycleView_AO
    private lateinit var search: MenuItem
    private lateinit var acceptedView: AcceptedOrders_ViewModel
    private lateinit var mainActivity: MainActivity
    private var token: String? = ""
    private lateinit var loadingAcc: ProgressBar
    private lateinit var refreshAcc: SwipeRefreshLayout
    private lateinit var orderId: ArrayList<String>
    private lateinit var orderAmt: ArrayList<String>
    private lateinit var orderQuantity: ArrayList<String>
    private lateinit var orderPickTime: ArrayList<String>
    private lateinit var orderImg: ArrayList<String>

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
        val view: View = inflater.inflate(R.layout.fragment_accepted_orders, container, false)

        mainActivity = (activity as MainActivity)

        acceptedView = ViewModelProvider(this).get(AcceptedOrders_ViewModel::class.java)

        recyclerView = view.findViewById(R.id.acceptedOrders_rv)
        loadingAcc = view.findViewById(R.id.loading_acc)
        refreshAcc = view.findViewById(R.id.refresh_acc)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        setHasOptionsMenu(true)

        //setUpAcceptedOrderRv()

        init()

        refreshAcc.setOnRefreshListener {

            init()

            refreshAcc.isRefreshing = false
        }

        return view
    }

    private fun init() {

        loadingAcc.visibility = View.VISIBLE

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

        getAcceptedOrders()

    }

    private fun getAcceptedOrders() {

        if (token != null) {

            acceptedView.getNewOrders(this, token.toString(), loadingAcc)
                .observe(viewLifecycleOwner) {

                    if (it != null) {

                        if (it.getSuccess()!!) {

                            orderImg.clear()
                            orderAmt.clear()
                            orderQuantity.clear()
                            orderPickTime.clear()
                            orderId.clear()
                            recycleViewModels.clear()

                            for (i in it.getData()!!.ListOrderResponce!!.indices) {

                                if (it.getData()!!.ListOrderResponce!![i].orderStatus.equals("1") && it.getData()!!.ListOrderResponce!![i].cancelStatus == false) {

                                    orderId.add("#" + it.getData()!!.ListOrderResponce!![i].id.toString())
                                    orderAmt.add("$" + it.getData()!!.ListOrderResponce!![i].amount.toString())
                                    orderQuantity.add(it.getData()!!.ListOrderResponce!![i].toalQuantity.toString())

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
                            loadingAcc.visibility = View.GONE
                            setUpAcceptedOrderRv()
                        }
                    }
                }
        }

    }

    private fun setUpAcceptedOrderRv() {

        for (i in orderId.indices) {

            recycleViewModels.add(
                RecycleModel(
                    orderId[i],
                    orderPickTime[i],
                    orderAmt[i],
                    "Accepted Order",
                    orderQuantity[i],
                    orderImg[i]
                )
            )

            recycleViewAdapterAO = RecycleView_AO(activity, recycleViewModels)
            recyclerView.adapter = recycleViewAdapterAO
            recycleViewAdapterAO.notifyItemInserted(i)
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
                recycleViewAdapterAO.filter.filter(newText)
                return false
            }
        })
    }

}