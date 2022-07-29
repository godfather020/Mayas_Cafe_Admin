package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.AllTransaction_ViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_AT
import com.example.mayas_cafe_admin.utils.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class WeklyTransactions_frag : Fragment() {

    private var recycleView_models = ArrayList<RecycleModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recycleView_adapter_AT: RecycleView_AT
    private lateinit var search: MenuItem
    private lateinit var loading: ProgressBar
    private lateinit var refresh: SwipeRefreshLayout
    private lateinit var noWeekTrans: TextView
    private lateinit var mainActivity: MainActivity
    private lateinit var allTransactionViewModel: AllTransaction_ViewModel
    var token: String? = null
    lateinit var orderId: ArrayList<String>
    lateinit var orderAmt: ArrayList<String>
    lateinit var orderPickAt: ArrayList<String>
    lateinit var paymentMethod: ArrayList<String>
    lateinit var transactionId: ArrayList<String>
    lateinit var userImg: ArrayList<String>
    lateinit var userName: ArrayList<String>
    lateinit var userPhone: ArrayList<String>

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
            inflater.inflate(R.layout.fragment_wekly_transactions_frag, container, false)

        allTransactionViewModel = ViewModelProvider(this).get(AllTransaction_ViewModel::class.java)

        mainActivity = activity as MainActivity

        recyclerView = view.findViewById(R.id.weeklyTrans_rv)
        loading = view.findViewById(R.id.loading_weekly)
        refresh = view.findViewById(R.id.refresh_weeklyTrans)
        noWeekTrans = view.findViewById(R.id.noWeekTrans)

        loading.visibility = View.VISIBLE
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
        userName = ArrayList()
        userPhone = ArrayList()

        init()

        refresh.setOnRefreshListener {

            init()
        }

        return view
    }

    private fun init() {

        getWeekTransactions()
    }

    private fun getWeekTransactions() {

        token =
            mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0)
                .getString(
                    Constants.sharedPrefrencesConstant.DEVICE_TOKEN, ""
                )

        if (token != null) {

            allTransactionViewModel.getAllOrders(this, token.toString(), loading)
                .observe(viewLifecycleOwner) {

                    if (it != null) {

                        if (it.getSuccess()!!) {

                            orderId.clear()
                            orderAmt.clear()
                            orderPickAt.clear()
                            transactionId.clear()
                            paymentMethod.clear()
                            userImg.clear()
                            userPhone.clear()
                            userName.clear()
                            recycleView_models.clear()

                            for (i in it.getData()!!.ListOrderResponce!!.indices) {

                                if (it.getData()!!.ListOrderResponce!![i].cancelStatus != true && it.getData()!!.ListOrderResponce!![i].paymentStatus.equals(
                                        "1"
                                    )
                                ) {

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

                                    val time = formatted.substring(0, 8)
                                    Log.d("DatePickTime", "" + time)

                                    val c = Calendar.getInstance().time
                                    println("Current time => $c")

                                    val df = SimpleDateFormat("dd-MM-yy", Locale.getDefault())
                                    val currentDate = df.format(c)

                                    Log.d("DateCurrentTime", "" + currentDate)

                                    var date: Date? = null

                                    try {
                                        date = df.parse(currentDate)
                                        Log.e("formated date ", date.toString() + "")
                                    } catch (e: ParseException) {
                                        e.printStackTrace()
                                    }

                                    val c1 = Calendar.getInstance()
                                    if (date != null) {
                                        c1.time = date
                                    }
                                    c1.add(Calendar.DATE, -7)
                                    val expDate = c1.time
                                    val before7Days =
                                        SimpleDateFormat("dd-MM-yy", Locale.US).format(expDate)

                                    Log.d("Date7Before", before7Days)


                                    //Log.d("created", getMyTime.toString());
                                    val sdf = SimpleDateFormat("dd-MM-yy")
                                    try {
                                        val strDate = sdf.parse(time)
                                        Log.d("DatePick", strDate.toString());
                                        if (strDate != null && (strDate == Date() || strDate == expDate || (strDate.before(
                                                Date()
                                            ) && strDate.after(expDate)))
                                        ) {

                                            Log.d("date", time)

                                            orderId.add(it.getData()!!.ListOrderResponce!![i].id.toString())
                                            orderAmt.add(it.getData()!!.ListOrderResponce!![i].amount.toString())
                                            paymentMethod.add("By " + it.getData()!!.ListOrderResponce!![i].paymentMethod.toString())
                                            transactionId.add(it.getData()!!.ListOrderResponce!![i].transactionId.toString())

                                            orderPickAt.add(formatted)

                                            Log.d("userId33333", it.getData()!!.ListOrderResponce!![i].userId.toString())

                                            userImg.add( "img")
                                            userName.add(it.getData()!!.ListOrderResponce!![i].userId.toString())
                                            userPhone.add("phone")

                                        }
                                    } catch (e: ParseException) {
                                        e.printStackTrace()
                                    }
                                }
                            }

                            if (orderId.size <= 0) {

                                noWeekTrans.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                            } else {

                                noWeekTrans.visibility = View.GONE
                                recyclerView.visibility = View.VISIBLE
                            }

                            loading.visibility = View.GONE
                            refresh.isRefreshing = false
                            setUpCurrentTransRv()
                        }
                    }
                }
        }
    }

    private fun getUserDetails(userId: String, value : String): String {

        var userImage = ""
        var userName = ""
        var userPhone = ""

        allTransactionViewModel.getUserInfo(this, token.toString(), userId)
            .observe(viewLifecycleOwner) {

                if (it != null) {

                    if (it.getSuccess()!!) {

                        userImage = it.getData()!!.user!!.profilePic
                        userName = it.getData()!!.user!!.userName
                        userPhone = it.getData()!!.user!!.phoneNumber

                        Log.d("userName33333", userName)
                    }
                }
            }

        return when (value) {
            "name" -> {

                userName
            }
            "phone" -> {

                userPhone
            }
            else -> {
                userImage
            }
        }
    }

    private fun setUpCurrentTransRv() {

        for (i in orderId.indices) {

            recycleView_models.add(
                RecycleModel(
                    orderId[i],
                    orderPickAt[i],
                    orderAmt[i],
                    userImg[i],
                    transactionId[i],
                    paymentMethod[i],
                    "Received",
                    userName[i],
                    userPhone[i]
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