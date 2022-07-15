package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.Dashboard_ViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_RO
import com.example.mayas_cafe_admin.utils.Constants
import com.example.mayas_cafe_admin.utils.WeekSales
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class DashboardFrag : Fragment() {

    private var weekSales: ArrayList<WeekSales> = ArrayList()
    private var recycleViewModels = java.util.ArrayList<RecycleModel>()
    private lateinit var chart: com.github.mikephil.charting.charts.BarChart
    private lateinit var totalIncome: TextView
    private lateinit var mainActivity: MainActivity
    private lateinit var allOrdersCard: CardView
    private lateinit var newOrdersCard: CardView
    private lateinit var prepOrdersCard: CardView
    private lateinit var readyToDeliver: CardView
    private lateinit var totalCategories: CardView
    private lateinit var totalProducts: CardView
    private lateinit var recyclerView: RecyclerView
    private lateinit var recycleViewAdapterRO: RecycleView_RO
    private lateinit var userName: TextView
    private lateinit var dashboardView: Dashboard_ViewModel
    private lateinit var loadingDash: ProgressBar
    private lateinit var orderId: ArrayList<String>
    private lateinit var orderName: ArrayList<String>
    private lateinit var orderPrice: ArrayList<String>
    private lateinit var orderImg: ArrayList<String>
    private lateinit var newOrdersTxt: TextView
    private lateinit var acceptedTxt: TextView
    private lateinit var beingPrepTxt: TextView
    private lateinit var readyToTxt: TextView
    private lateinit var totalCatTxt: TextView
    private lateinit var totalProTxt: TextView
    private lateinit var userImg: CircleImageView
    private var newOrders = 0
    private var acceptedOrders = 0
    private var beingPre = 0
    private var ready = 0
    private lateinit var refresh: SwipeRefreshLayout
    private var token: String? = ""
    private lateinit var mHandler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_dashboard_frag, container, false)
        dashboardView = ViewModelProvider(this).get(Dashboard_ViewModel::class.java)

        mainActivity = (activity as MainActivity)

        mainActivity.toolbar_const.title = ""
        mainActivity.navigationView.setCheckedItem(R.id.invisible)

        mHandler = Handler()
        mHandler.postDelayed(m_Runnable,5000);

        MainActivity.isBackPressed = false
        chart = view.findViewById(R.id.bar_chart)
        totalIncome = view.findViewById(R.id.target_amt)
        allOrdersCard = view.findViewById(R.id.allorders_card)
        newOrdersCard = view.findViewById(R.id.neworders_card)
        prepOrdersCard = view.findViewById(R.id.preporders_card)
        readyToDeliver = view.findViewById(R.id.deliverorders_card)
        totalCategories = view.findViewById(R.id.category_card)
        totalProducts = view.findViewById(R.id.product_card)
        recyclerView = view.findViewById(R.id.recent_order_rv)
        userName = view.findViewById(R.id.user_name)
        loadingDash = view.findViewById(R.id.loading_dash)
        newOrdersTxt = view.findViewById(R.id.newOrder_txt)
        acceptedTxt = view.findViewById(R.id.accOrders_txt)
        beingPrepTxt = view.findViewById(R.id.beingPrep_txt)
        readyToTxt = view.findViewById(R.id.readyTo_txt)
        refresh = view.findViewById(R.id.refresh_dash)
        totalCatTxt = view.findViewById(R.id.totalCat_txt)
        totalProTxt = view.findViewById(R.id.totalPro_txt)
        userImg = view.findViewById(R.id.user_img)

        init()

        refresh.setOnRefreshListener {

            init()

            refresh.isRefreshing = false

        }

        allOrdersCard.setOnClickListener {

            mainActivity.navigationView.setCheckedItem(R.id.AllOrders)
            mainActivity.loadFragment(
                fragmentManager,
                CurrentOrdersFrag(),
                R.id.fragment_container,
                false,
                "New Orders",
                null
            )
            Constants.SET_ORDER_TAB = 0
        }

        newOrdersCard.setOnClickListener {

            mainActivity.navigationView.setCheckedItem(R.id.AllOrders)
            mainActivity.loadFragment(
                fragmentManager,
                CurrentOrdersFrag(),
                R.id.fragment_container,
                false,
                "New Orders",
                null
            )
            Constants.SET_ORDER_TAB = 1
        }

        prepOrdersCard.setOnClickListener {

            mainActivity.navigationView.setCheckedItem(R.id.AllOrders)
            mainActivity.loadFragment(
                fragmentManager,
                CurrentOrdersFrag(),
                R.id.fragment_container,
                false,
                "New Orders",
                null
            )
            Constants.SET_ORDER_TAB = 2
        }

        readyToDeliver.setOnClickListener {

            mainActivity.navigationView.setCheckedItem(R.id.AllOrders)
            mainActivity.loadFragment(
                fragmentManager,
                CurrentOrdersFrag(),
                R.id.fragment_container,
                false,
                "New Orders",
                null
            )
            Constants.SET_ORDER_TAB = 3
        }

        totalCategories.setOnClickListener {

            mainActivity.navigationView.setCheckedItem(R.id.Menu)
            mainActivity.loadFragment(
                fragmentManager,
                MenuFrag(),
                R.id.fragment_container,
                false,
                "New Orders",
                null
            )

        }

        totalProducts.setOnClickListener {


        }

        return view
    }

    private val m_Runnable: Runnable = object : Runnable {
        override fun run() {

            //Toast.makeText(context, "in runnable", Toast.LENGTH_SHORT).show()

            init()

            mHandler.postDelayed(this, 5000)
        }
    } //runnable

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacks(m_Runnable)
    }

    private fun init() {

        loadingDash.visibility = View.VISIBLE

        orderId = ArrayList<String>()
        orderName = ArrayList<String>()
        orderPrice = ArrayList<String>()
        orderImg = ArrayList<String>()

        val userPic =
            mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.USER_I, 0)
                .getString(Constants.sharedPrefrencesConstant.USER_I, "")
        val uName = mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.USER_N, 0)
            .getString(Constants.sharedPrefrencesConstant.USER_N, "")
        val isLogin = mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.LOGIN, 0)
            .getBoolean(Constants.sharedPrefrencesConstant.LOGIN, false)
        token =
            mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0)
                .getString(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, "")

        if (token != null) {
            Constants.DEVICE_TOKEN = token.toString()
        }

        if (userPic != null) {

            Picasso.get()
                .load(Constants.AdminProfile_Path + userPic)
                .into(userImg)
        }

        if (isLogin) {

            if (uName!!.isNotEmpty()) {

                userName.text = uName
            } else {

                userName.text = "Stranger"
            }
        } else {

            userName.text = "Stranger"
        }

        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        setData()
        getRecentOrders()
        getTotalCategories()
        getTotalProducts()
        //setUpRecentOrdersRV()

    }

    private fun getTotalProducts() {

        if (token != null) {

            dashboardView.getTotalProducts(this, "x-token", loadingDash)
                .observe(viewLifecycleOwner, Observer {

                    if (it != null) {

                        if (it.getSuccess()!!) {

                            val totalProducts = it.getData()!!.ListproductResponce!!.size
                            Log.d("catSize", totalProducts.toString())
                            totalProTxt.text = totalProducts.toString()
                        }
                    }
                })
        }
    }

    private fun getTotalCategories() {

        if (token != null) {

            dashboardView.getTotalCategories(this, token.toString(), loadingDash)
                .observe(viewLifecycleOwner, Observer {

                    if (it != null) {

                        if (it.getSuccess()!!) {

                            val totalCategory = it.getData()!!.ListcategoryResponce!!.size
                            Log.d("catSize", totalCategory.toString())
                            totalCatTxt.text = totalCategory.toString()
                        }
                    }

                })
        }
    }

    private fun getRecentOrders() {

        if (token != null) {

            dashboardView.getRecentOrders(this, token.toString(), loadingDash)
                .observe(viewLifecycleOwner, Observer {

                    if (it != null) {

                        if (it.getSuccess()!!) {

                            //Toast.makeText(context, "Recent Orders", Toast.LENGTH_SHORT).show()

                            orderImg.clear()
                            orderId.clear()
                            orderPrice.clear()
                            orderName.clear()
                            recycleViewModels.clear()
                            newOrders = 0
                            acceptedOrders = 0
                            beingPre = 0
                            ready = 0

                            for (i in it.getData()!!.ListOrderResponce!!.indices) {

                                if (it.getData()!!.ListOrderResponce!![i].orderStatus.equals("0") && it.getData()!!.ListOrderResponce!![i].cancelStatus == false) {

                                    newOrders++
                                } else if (it.getData()!!.ListOrderResponce!![i].orderStatus.equals(
                                        "1"
                                    ) && it.getData()!!.ListOrderResponce!![i].cancelStatus == false
                                ) {

                                    acceptedOrders++
                                } else if (it.getData()!!.ListOrderResponce!![i].orderStatus.equals(
                                        "2"
                                    ) && it.getData()!!.ListOrderResponce!![i].cancelStatus == false
                                ) {

                                    beingPre++
                                } else if (it.getData()!!.ListOrderResponce!![i].orderStatus.equals(
                                        "3"
                                    ) && it.getData()!!.ListOrderResponce!![i].cancelStatus == false
                                ) {

                                    ready++
                                } else {

                                    Log.d("nothing", "nothing")
                                }
                            }

                            if (it.getData()!!.ListOrderResponce!!.size > 3) {

                                for (i in 0..2) {

                                    orderId.add("OrderId : #" + it.getData()!!.ListOrderResponce!![i].id.toString())
                                    orderName.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![0].Productprice!!.createdBy.toString())
                                    orderPrice.add("$" + it.getData()!!.ListOrderResponce!![i].amount.toString())
                                    orderImg.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![0].Productprice!!.productPic.toString())

                                }
                            } else {

                                for (i in it.getData()!!.ListOrderResponce!!.indices) {

                                    orderId.add("OrderId : #" + it.getData()!!.ListOrderResponce!![i].id.toString())
                                    orderName.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![0].Productprice!!.createdBy.toString())
                                    orderPrice.add("$" + it.getData()!!.ListOrderResponce!![i].amount.toString())
                                    orderImg.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![0].Productprice!!.productPic.toString())
                                }
                            }

                            loadingDash.visibility = View.GONE

                            setUpRecentOrdersRV()
                        }
                    }
                })
        }
    }

    private fun setUpRecentOrdersRV() {

        newOrdersTxt.text = newOrders.toString()
        acceptedTxt.text = acceptedOrders.toString()
        beingPrepTxt.text = beingPre.toString()
        readyToTxt.text = ready.toString()

        for (i in orderId.indices) {

            recycleViewModels.add(
                RecycleModel(
                    orderId[i],
                    orderName[i],
                    orderPrice[i],
                    orderImg[i]
                )
            )

            recycleViewAdapterRO = RecycleView_RO(activity, recycleViewModels)
            recyclerView.adapter = recycleViewAdapterRO
            recycleViewAdapterRO.notifyItemInserted(i)
        }
    }

    private fun fillWeekSales() {

        weekSales.clear()

        weekSales.add(WeekSales("Sun", 240))
        weekSales.add(WeekSales("Mon", 150))
        weekSales.add(WeekSales("Tue", 290))
        weekSales.add(WeekSales("Wed", 440))
        weekSales.add(WeekSales("Thu", 180))
        weekSales.add(WeekSales("Fri", 320))
        weekSales.add(WeekSales("Sat", 220))

    }

    private fun setData() {

        val income: ArrayList<BarEntry> = ArrayList()
        val labelNames: ArrayList<String> = ArrayList()
        fillWeekSales()
        var totalSales = 0.00

        for (i in weekSales.indices) {

            val weeks = weekSales[i].weeks
            val sales = weekSales[i].sales

            totalSales += sales

            income.add(BarEntry(i.toFloat(), sales.toFloat()))
            labelNames.add(weeks)
        }

        totalIncome.text = "$$totalSales"

        val barDataSet: BarDataSet = BarDataSet(income, "Weekly Income")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        chart.description.text = "Weeks"
        barDataSet.valueTextColor = android.graphics.Color.BLACK
        barDataSet.valueTextSize = 8f

        val barData: BarData = BarData(barDataSet)

        chart.data = barData

        //Set xAxis Value Formatter
        val xAxis: XAxis = chart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labelNames)
        //Set xAxis Position
        xAxis.position = XAxis.XAxisPosition.TOP
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.granularity = 1f
        xAxis.labelCount = labelNames.size
        //xAxis.labelRotationAngle = 180F

        chart.animateY(2000)
        chart.invalidate()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.getItem(0).isVisible = false
    }

}