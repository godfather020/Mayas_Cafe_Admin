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
import com.example.mayas_cafe_admin.fragments.ViewModel.NewOrders_ViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_NO
import com.example.mayas_cafe_admin.utils.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class New_Orders : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    lateinit var  recyclerView: RecyclerView
    lateinit var recycleView_adapter_NO : RecycleView_NO
    lateinit var search : MenuItem
    lateinit var newOrders_view : NewOrders_ViewModel
    lateinit var mainActivity : MainActivity
    var token : String? = ""
    lateinit var loading_new : ProgressBar
    lateinit var orderId : ArrayList<String>
    lateinit var orderAmt : ArrayList<String>
    lateinit var orderQuantity : ArrayList<String>
    lateinit var orderPickTime : ArrayList<String>
    lateinit var orderImg : ArrayList<String>
    lateinit var refresh_new : SwipeRefreshLayout

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
        val view : View = inflater.inflate(R.layout.fragment_new_orders, container, false)

        mainActivity = (activity as MainActivity)

        newOrders_view = ViewModelProvider(this).get(NewOrders_ViewModel::class.java)

        recyclerView= view.findViewById(R.id.runOrder_rv)
        loading_new = view.findViewById(R.id.loading_new)
        refresh_new = view.findViewById(R.id.refresh_new)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        setHasOptionsMenu(true)

        init()

        refresh_new.setOnRefreshListener {

            init()

            refresh_new.isRefreshing = false
        }

        return view
    }

    private fun init(){

        token =
            mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0).getString(
                Constants.sharedPrefrencesConstant.DEVICE_TOKEN, "")

        loading_new.visibility = View.VISIBLE

        orderId = ArrayList<String>()
        orderAmt = ArrayList<String>()
        orderQuantity = ArrayList<String>()
        orderPickTime = ArrayList<String>()
        orderImg = ArrayList<String>()

        //setUpRunOrderRv()

        getNewOrders()

    }

    private fun getNewOrders() {

        if (token != null) {

            newOrders_view.getNewOrders(this, token.toString(), loading_new)
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

                                if (it.getData()!!.ListOrderResponce!![i].orderStatus.equals("0") && it.getData()!!.ListOrderResponce!![i].cancelStatus == false) {

                                    orderId.add("#"+it.getData()!!.ListOrderResponce!![i].id.toString())
                                    orderAmt.add("$"+it.getData()!!.ListOrderResponce!![i].amount.toString())
                                    orderQuantity.add(it.getData()!!.ListOrderResponce!![i].toalQuantity.toString())

                                    val pickTime = it.getData()!!.ListOrderResponce!![i].pickupAt.toString()

                                    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                    val output = SimpleDateFormat("hh:mm a")

                                    var d: Date? = null
                                    try {
                                        d = input.parse(pickTime)
                                    } catch (e: ParseException) {
                                        e.printStackTrace()
                                    }
                                    val formatted: String = output.format(d!!)
                                    Log.d("DATE", "" + formatted)

                                    orderPickTime.add(formatted)

                                    if(it.getData()!!.ListOrderResponce!![i].Orderlists!!.isNotEmpty()) {

                                        orderImg.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![0].Productprice!!.productPic.toString())
                                    }
                                    else{

                                        orderImg.add("default.png")
                                    }
                                }
                            }
                            loading_new.visibility = View.GONE
                            setUpRunOrderRv()
                        }
                    }
                })
        }
    }

    private fun setUpRunOrderRv() {

        for (i in orderId.indices){

            recycleView_models.add(RecycleModel(orderId[i],orderPickTime[i], orderAmt[i], "New Order", orderQuantity[i], orderImg[i]))
        }

        recycleView_adapter_NO = RecycleView_NO(activity, recycleView_models)
        recyclerView.adapter = recycleView_adapter_NO
        recycleView_adapter_NO.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        search = menu.findItem(R.id.search)
        val searchView : androidx.appcompat.widget.SearchView = search.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                recycleView_adapter_NO.filter.filter(newText)
                return false
            }
        })
    }
}