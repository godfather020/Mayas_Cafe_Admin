package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.BeingPrepared_ViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_AO
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_BP
import com.example.mayas_cafe_admin.utils.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class BeingPrepared_orders : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    lateinit var recyclerView: RecyclerView
    lateinit var recycleView_adapter_BP: RecycleView_BP
    lateinit var search: MenuItem
    lateinit var mainActivity: MainActivity
    lateinit var beingPrepared_view: BeingPrepared_ViewModel
    lateinit var orderId: ArrayList<String>
    lateinit var orderAmt: ArrayList<String>
    lateinit var orderQuantity: ArrayList<String>
    lateinit var orderPickTime: ArrayList<String>
    lateinit var orderImg: ArrayList<String>
    lateinit var loading_prep: ProgressBar
    lateinit var refreshPrep: SwipeRefreshLayout
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
        val view: View = inflater.inflate(R.layout.fragment_being_prepared_orders, container, false)

        beingPrepared_view = ViewModelProvider(this).get(BeingPrepared_ViewModel::class.java)

        mainActivity = activity as MainActivity

        recyclerView = view.findViewById(R.id.beingPre_rv)
        loading_prep = view.findViewById(R.id.loading_prep)
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

        loading_prep.visibility = View.VISIBLE

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

            beingPrepared_view.getBeingPrepared(this, token.toString(), loading_prep)
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

                                if (it.getData()!!.ListOrderResponce!![i].orderStatus.equals("2") && it.getData()!!.ListOrderResponce!![i].cancelStatus == false) {

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
                            loading_prep.visibility = View.GONE
                            setUpBeingPreOrderRv()
                        }

                    }
                })
        }
    }

    private fun setUpBeingPreOrderRv() {

        for (i in orderId.indices) {

            recycleView_models.add(
                RecycleModel(
                    orderId[i],
                    orderPickTime[i],
                    orderAmt[i],
                    "Being Prepared",
                    orderQuantity[i],
                    orderImg[i]
                )
            )
        }

        recycleView_adapter_BP = RecycleView_BP(activity, recycleView_models)
        recyclerView.adapter = recycleView_adapter_BP
        recycleView_adapter_BP.notifyDataSetChanged()
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
                recycleView_adapter_BP.filter.filter(newText)
                return false
            }
        })
    }
}