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
import com.example.mayas_cafe_admin.fragments.ViewModel.BeingPrepared_ViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_BP
import com.example.mayas_cafe_admin.utils.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class BeingPreparedOrders : Fragment() {

    private var recycleViewModels = ArrayList<RecycleModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recycleViewAdapterBP: RecycleView_BP
    private lateinit var search: MenuItem
    private lateinit var mainActivity: MainActivity
    private lateinit var beingPreparedView: BeingPrepared_ViewModel
    private lateinit var orderId: ArrayList<String>
    private lateinit var orderAmt: ArrayList<String>
    private lateinit var orderQuantity: ArrayList<String>
    private lateinit var orderPickTime: ArrayList<String>
    private lateinit var orderImg: ArrayList<String>
    private lateinit var loadingPrep: ProgressBar
    private lateinit var refreshPrep: SwipeRefreshLayout
    private var token: String? = ""

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
        val view: View = inflater.inflate(R.layout.fragment_being_prepared_orders, container, false)

        beingPreparedView = ViewModelProvider(this).get(BeingPrepared_ViewModel::class.java)

        mainActivity = activity as MainActivity

        recyclerView = view.findViewById(R.id.beingPre_rv)
        loadingPrep = view.findViewById(R.id.loading_prep)
        refreshPrep = view.findViewById(R.id.refresh_prep)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        setHasOptionsMenu(true)

        //setUpBeingPreOrderRv()

        init()

        refreshPrep.setOnRefreshListener {

            init()

            refreshPrep.isRefreshing = false
        }

        return view
    }

    private fun init() {

        loadingPrep.visibility = View.VISIBLE

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

        getBeingPreparedOrders()

    }

    private fun getBeingPreparedOrders() {

        if (token != null) {

            beingPreparedView.getBeingPrepared(this, token.toString(), loadingPrep)
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

                                if (it.getData()!!.ListOrderResponce!![i].orderStatus.equals("2") && it.getData()!!.ListOrderResponce!![i].cancelStatus == false) {

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

                                        orderImg.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![0].Productprice!!.productPic.toString())
                                    } else {

                                        orderImg.add("default.png")
                                    }
                                }
                            }
                            loadingPrep.visibility = View.GONE
                            setUpBeingPreOrderRv()
                        }

                    }
                }
        }
    }

    private fun setUpBeingPreOrderRv() {

        for (i in orderId.indices) {

            recycleViewModels.add(
                RecycleModel(
                    orderId[i],
                    orderPickTime[i],
                    orderAmt[i],
                    "Being Prepared",
                    orderQuantity[i],
                    orderImg[i]
                )
            )

            recycleViewAdapterBP = RecycleView_BP(activity, recycleViewModels)
            recyclerView.adapter = recycleViewAdapterBP
            recycleViewAdapterBP.notifyItemInserted(i)
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
                recycleViewAdapterBP.filter.filter(newText)
                return false
            }
        })
    }
}