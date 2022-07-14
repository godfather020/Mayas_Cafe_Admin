package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.BeingPrepared_ViewModel
import com.example.mayas_cafe_admin.fragments.ViewModel.ReadyToPickUp_ViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_BP
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_RTP
import com.example.mayas_cafe_admin.utils.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ReadyToPickUp : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    lateinit var recyclerView: RecyclerView
    lateinit var recycleView_adapter_RTP: RecycleView_RTP
    lateinit var search: MenuItem
    lateinit var mainActivity: MainActivity
    lateinit var readyToPickUp_view: ReadyToPickUp_ViewModel
    lateinit var orderId: ArrayList<String>
    lateinit var orderAmt: ArrayList<String>
    lateinit var orderQuantity: ArrayList<String>
    lateinit var orderPickTime: ArrayList<String>
    lateinit var orderImg: ArrayList<String>
    lateinit var loading_ready: ProgressBar
    lateinit var refreshReady: SwipeRefreshLayout
    var token: String? = ""

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
        val view: View = inflater.inflate(R.layout.fragment_ready_to_pick_up, container, false)

        readyToPickUp_view = ViewModelProvider(this).get(ReadyToPickUp_ViewModel::class.java)

        mainActivity = activity as MainActivity

        recyclerView = view.findViewById(R.id.pickUp_rv)
        loading_ready = view.findViewById(R.id.loading_ready)
        refreshReady = view.findViewById(R.id.refresh_ready)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        //setPickUpRv()

        setHasOptionsMenu(true)

        init()

        refreshReady.setOnRefreshListener {

            init()

            refreshReady.isRefreshing = false
        }

        return view
    }

    private fun init() {

        loading_ready.visibility = View.VISIBLE

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

        getReadyOrders()

    }

    private fun getReadyOrders() {

        if (token != null) {

            readyToPickUp_view.getReadyToPickUp(this, token.toString(), loading_ready)
                .observe(viewLifecycleOwner, Observer {

                    if (it != null) {

                        if (it.getSuccess()!!) {

                            orderImg.clear()
                            orderAmt.clear()
                            orderQuantity.clear()
                            orderPickTime.clear()
                            orderId.clear()
                            recycleView_models.clear()

                            for (i in it.getData()!!.ListOrderResponce!!.indices) {

                                if (it.getData()!!.ListOrderResponce!![i].orderStatus.equals("3") && it.getData()!!.ListOrderResponce!![i].cancelStatus == false) {

                                    orderId.add("#" + it.getData()!!.ListOrderResponce!![i].id.toString())
                                    orderAmt.add("$" + it.getData()!!.ListOrderResponce!![i].amount.toString())
                                    orderQuantity.add(it.getData()!!.ListOrderResponce!![i].toalQuantity.toString())

                                    val pickTime =
                                        it.getData()!!.ListOrderResponce!![i].pickupAt.toString()

                                    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                    val output = SimpleDateFormat("dd-MM-yy hh:mm a")

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

                                        orderImg.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![0].Productprice!!.productPic.toString())
                                    } else {

                                        orderImg.add("default.png")
                                    }
                                }
                            }
                            loading_ready.visibility = View.GONE
                            setPickUpRv()
                        }
                    }

                })

        }
    }

    private fun setPickUpRv() {

        for (i in orderId.indices) {

            recycleView_models.add(
                RecycleModel(
                    orderId[i],
                    orderPickTime[i],
                    orderAmt[i],
                    "Ready to PickUp",
                    orderQuantity[i],
                    orderImg[i]
                )
            )
        }

        recycleView_adapter_RTP = RecycleView_RTP(activity, recycleView_models)
        recyclerView.adapter = recycleView_adapter_RTP
        recycleView_adapter_RTP.notifyDataSetChanged()

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
                recycleView_adapter_RTP.filter.filter(newText)
                return false
            }
        })
    }
}