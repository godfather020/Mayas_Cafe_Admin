package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.AllTransaction_ViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_AT
import com.example.mayas_cafe_admin.utils.Constants
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CurrentTransactions_frag : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    lateinit var recyclerView: RecyclerView
    lateinit var recycleView_adapter_AT: RecycleView_AT
    lateinit var search: MenuItem
    lateinit var mainActivity: MainActivity
    lateinit var allTransactionViewModel: AllTransaction_ViewModel
    var token: String? = null
    lateinit var loadingCurr: ProgressBar
    lateinit var orderId: ArrayList<String>
    lateinit var orderAmt: ArrayList<String>
    lateinit var orderPickAt: ArrayList<String>
    lateinit var paymentMethod: ArrayList<String>
    lateinit var transactionId: ArrayList<String>
    lateinit var userImg: ArrayList<String>
    lateinit var refresh : SwipeRefreshLayout


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
        val view: View =
            inflater.inflate(R.layout.fragment_current_transactions_frag, container, false)

        allTransactionViewModel = ViewModelProvider(this).get(AllTransaction_ViewModel::class.java)

        mainActivity = activity as MainActivity

        recyclerView = view.findViewById(R.id.currentTrans_rv)
        loadingCurr = view.findViewById(R.id.loading_curr)
        refresh = view.findViewById(R.id.refresh_curr)

        loadingCurr.visibility = View.VISIBLE

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        setHasOptionsMenu(true)

        //setUpCurrentTransRv()

        orderId = ArrayList()
        orderPickAt = ArrayList()
        orderAmt = ArrayList()
        transactionId = ArrayList()
        paymentMethod = ArrayList()
        userImg = ArrayList()

        init()

        refresh.setOnRefreshListener {

            init()


        }

        return view
    }

    private fun init(){

        getCurrentTransactions()
    }

    private fun getCurrentTransactions() {

        token =
            mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0)
                .getString(
                    Constants.sharedPrefrencesConstant.DEVICE_TOKEN, ""
                )

        if (token != null) {

            allTransactionViewModel.getAllOrders(this, token.toString(), loadingCurr)
                .observe(viewLifecycleOwner) {

                    if (it != null) {

                        if (it.getSuccess()!!) {

                            orderId.clear()
                            orderAmt.clear()
                            orderPickAt.clear()
                            transactionId.clear()
                            paymentMethod.clear()
                            userImg.clear()
                            recycleView_models.clear()

                            for (i in it.getData()!!.ListOrderResponce!!.indices) {

                                if (it.getData()!!.ListOrderResponce!![i].cancelStatus != true && it.getData()!!.ListOrderResponce!![i].paymentStatus.equals(
                                        "1"
                                    )
                                ) {

                                    orderId.add(it.getData()!!.ListOrderResponce!![i].id.toString())
                                    orderAmt.add(it.getData()!!.ListOrderResponce!![i].amount.toString())
                                    paymentMethod.add("By "+it.getData()!!.ListOrderResponce!![i].paymentMethod.toString())
                                    transactionId.add(it.getData()!!.ListOrderResponce!![i].transactionId.toString())

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


                                    orderPickAt.add(formatted)

                                    userImg.add(getUserDetails(it.getData()!!.ListOrderResponce!![i].userId.toString()))
                                }
                            }
                            loadingCurr.visibility = View.GONE
                            refresh.isRefreshing = false
                            setUpCurrentTransRv()
                        }
                    }
                }
        }
    }

    private fun getUserDetails(userId: String) : String {

        var userImage = ""

        allTransactionViewModel.getUserInfo(this, token.toString(), userId).observe(viewLifecycleOwner){

            if (it != null){

                if (it.getSuccess()!!){

                    userImage = it.getData()!!.user!!.profilePic
                }
            }
        }
        return userImage
    }

    private fun setUpCurrentTransRv() {

        for (i in orderId.indices){

            recycleView_models.add(
                RecycleModel(
                    orderId[i],
                    orderPickAt[i],
                    orderAmt[i],
                    userImg[i],
                    transactionId[i],
                    paymentMethod[i],
                    "Received",
                )
            )

        }

        recycleView_adapter_AT = RecycleView_AT(activity, recycleView_models)
        recyclerView.adapter = recycleView_adapter_AT
        recycleView_adapter_AT.notifyDataSetChanged()
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
                recycleView_adapter_AT.filter.filter(newText)
                return false
            }
        })
    }
}