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
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_AO
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_AT
import com.example.mayas_cafe_admin.utils.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AllTransactions_frag : Fragment() {

    private var recycleView_models = ArrayList<RecycleModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recycleView_adapter_AT: RecycleView_AT
    private lateinit var search: MenuItem
    private lateinit var loadingAllTrans: ProgressBar
    private lateinit var refreshAllTrans: SwipeRefreshLayout
    private lateinit var noTransHistory: TextView
    private lateinit var allTransactionViewModel: AllTransaction_ViewModel
    private var token: String? = null
    private lateinit var mainActivity: MainActivity
    lateinit var orderId: ArrayList<String>
    lateinit var orderAmt: ArrayList<String>
    lateinit var orderPickAt: ArrayList<String>
    lateinit var paymentMethod: ArrayList<String>
    lateinit var transactionId: ArrayList<String>
    lateinit var userImg: ArrayList<String>

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
        val view: View = inflater.inflate(R.layout.fragment_all_transactions_frag, container, false)

        allTransactionViewModel = ViewModelProvider(this).get(AllTransaction_ViewModel::class.java)

        mainActivity = activity as MainActivity

        recyclerView = view.findViewById(R.id.all_trans_rv)
        loadingAllTrans = view.findViewById(R.id.loading_allTrans)
        refreshAllTrans = view.findViewById(R.id.refresh_allTrans)
        noTransHistory = view.findViewById(R.id.noTrans_history)

        loadingAllTrans.visibility = View.VISIBLE

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        setHasOptionsMenu(true)

        //setUpAllTransRv()

        orderId = ArrayList()
        orderPickAt = ArrayList()
        orderAmt = ArrayList()
        transactionId = ArrayList()
        paymentMethod = ArrayList()
        userImg = ArrayList()

        refreshAllTrans.setOnRefreshListener {

            init()
        }

        init()

        return view
    }

    private fun init() {

        getAllTransactions()

    }

    private fun getAllTransactions() {

        token =
            mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0)
                .getString(
                    Constants.sharedPrefrencesConstant.DEVICE_TOKEN, ""
                )

        if (token != null) {

            allTransactionViewModel.getAllOrders(this, token.toString(), loadingAllTrans)
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
                                    Log.d("DATE", "" + time)

                                    val c = Calendar.getInstance().time
                                    println("Current time => $c")

                                    val df = SimpleDateFormat("dd-MM-yy", Locale.getDefault())
                                    val formattedDate = df.format(c)

                                    Log.d("DATE", "" + formattedDate)

                                    orderId.add(it.getData()!!.ListOrderResponce!![i].id.toString())
                                    orderAmt.add(it.getData()!!.ListOrderResponce!![i].amount.toString())
                                    paymentMethod.add("By " + it.getData()!!.ListOrderResponce!![i].paymentMethod.toString())
                                    transactionId.add(it.getData()!!.ListOrderResponce!![i].transactionId.toString())

                                    orderPickAt.add(formatted)

                                    userImg.add(getUserDetails(it.getData()!!.ListOrderResponce!![i].userId.toString()))

                                }
                            }

                            if (orderId.size <= 0) {

                                noTransHistory.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                            } else {

                                noTransHistory.visibility = View.GONE
                                recyclerView.visibility = View.VISIBLE
                            }

                            loadingAllTrans.visibility = View.GONE
                            refreshAllTrans.isRefreshing = false
                            setUpAllTransRv()
                        }
                    }
                }
        }
    }

    private fun getUserDetails(userId: String): String {

        var userImage = ""

        allTransactionViewModel.getUserInfo(this, token.toString(), userId)
            .observe(viewLifecycleOwner) {

                if (it != null) {

                    if (it.getSuccess()!!) {

                        userImage = it.getData()!!.user!!.profilePic
                    }
                }
            }
        return userImage
    }

    private fun setUpAllTransRv() {

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