package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.transition.Visibility
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.Dashboard_ViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_AO
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_RO
import com.example.mayas_cafe_admin.utils.Constants
import com.example.mayas_cafe_admin.utils.Functions
import com.example.mayas_cafe_admin.utils.WeekSales
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.eazegraph.lib.charts.BarChart

class Dashboard_frag : Fragment(){

    var weekSales : ArrayList<WeekSales> = ArrayList()
    var recycleView_models = java.util.ArrayList<RecycleModel>()
    private lateinit var chart: com.github.mikephil.charting.charts.BarChart
    lateinit var total_income : TextView
    lateinit var mainActivity: MainActivity
    lateinit var allOrders_card : CardView
    lateinit var newOrders_card : CardView
    lateinit var prepOrders_card : CardView
    lateinit var readyToDiliver : CardView
    lateinit var totalCategories : CardView
    lateinit var totalProducts : CardView
    lateinit var recyclerView : RecyclerView
    lateinit var recycleView_adapter_RO : RecycleView_RO
    lateinit var userName : TextView
    lateinit var dashboard_view : Dashboard_ViewModel
    lateinit var loading_dash : ProgressBar
    lateinit var orderId : ArrayList<String>
    lateinit var orderName : ArrayList<String>
    lateinit var orderPrice : ArrayList<String>
    lateinit var orderImg : ArrayList<String>
    lateinit var newOrders_txt : TextView
    lateinit var accepted_txt : TextView
    lateinit var beingPrep_txt : TextView
    lateinit var readyTo_txt : TextView
    lateinit var totalCat_txt : TextView
    lateinit var totalPro_txt : TextView
    lateinit var userImg : CircleImageView
    var newOrders = 0
    var acceptedOrders = 0
    var beingPre = 0
    var ready = 0
    lateinit var refresh : SwipeRefreshLayout
    var token : String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_dashboard_frag, container, false)
        dashboard_view = ViewModelProvider(this).get(Dashboard_ViewModel::class.java)

        mainActivity = (activity as MainActivity)

        mainActivity.toolbar_const.title = ""
        mainActivity.navigationView.setCheckedItem(R.id.invisible)

        MainActivity.isBackPressed = false
        chart = view.findViewById(R.id.bar_chart)
        total_income = view.findViewById(R.id.target_amt)
        allOrders_card = view.findViewById(R.id.allorders_card)
        newOrders_card = view.findViewById(R.id.neworders_card)
        prepOrders_card = view.findViewById(R.id.preporders_card)
        readyToDiliver = view.findViewById(R.id.deliverorders_card)
        totalCategories = view.findViewById(R.id.category_card)
        totalProducts = view.findViewById(R.id.product_card)
        recyclerView = view.findViewById(R.id.recent_order_rv)
        userName = view.findViewById(R.id.user_name)
        loading_dash = view.findViewById(R.id.loading_dash)
        newOrders_txt = view.findViewById(R.id.newOrder_txt)
        accepted_txt = view.findViewById(R.id.accOrders_txt)
        beingPrep_txt = view.findViewById(R.id.beingPrep_txt)
        readyTo_txt = view.findViewById(R.id.readyTo_txt)
        refresh = view.findViewById(R.id.refresh_dash)
        totalCat_txt = view.findViewById(R.id.totalCat_txt)
        totalPro_txt = view.findViewById(R.id.totalPro_txt)
        userImg = view.findViewById(R.id.user_img)

        init()

        refresh.setOnRefreshListener {

            init()

            refresh.isRefreshing = false

        }

        allOrders_card.setOnClickListener {

            mainActivity.navigationView.setCheckedItem(R.id.AllOrders)
            mainActivity.loadFragment(fragmentManager, CurrentOrders_frag(), R.id.fragment_container, false, "New Orders", null)
            Constants.SET_ORDER_TAB = 0
        }

        newOrders_card.setOnClickListener {

            mainActivity.navigationView.setCheckedItem(R.id.AllOrders)
            mainActivity.loadFragment(fragmentManager, CurrentOrders_frag(), R.id.fragment_container, false, "New Orders", null)
            Constants.SET_ORDER_TAB = 1
        }

        prepOrders_card.setOnClickListener {

            mainActivity.navigationView.setCheckedItem(R.id.AllOrders)
            mainActivity.loadFragment(fragmentManager, CurrentOrders_frag(), R.id.fragment_container, false, "New Orders", null)
            Constants.SET_ORDER_TAB = 2
        }

        readyToDiliver.setOnClickListener {

            mainActivity.navigationView.setCheckedItem(R.id.AllOrders)
            mainActivity.loadFragment(fragmentManager, CurrentOrders_frag(), R.id.fragment_container, false, "New Orders", null)
            Constants.SET_ORDER_TAB = 3
        }

        totalCategories.setOnClickListener {

            mainActivity.navigationView.setCheckedItem(R.id.Menu)
            mainActivity.loadFragment(fragmentManager, Menu_frag(), R.id.fragment_container, false, "New Orders", null)

        }

        totalProducts.setOnClickListener {


        }

        return view
    }

    private fun init() {

        loading_dash.visibility = View.VISIBLE

        orderId = ArrayList<String>()
        orderName = ArrayList<String>()
        orderPrice = ArrayList<String>()
        orderImg = ArrayList<String>()

        val userPic = mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.USER_I, 0).getString(Constants.sharedPrefrencesConstant.USER_I, "")
        val uName = mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.USER_N, 0).getString(Constants.sharedPrefrencesConstant.USER_N, "")
        val isLogin = mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.LOGIN, 0).getBoolean(Constants.sharedPrefrencesConstant.LOGIN, false)
        token =
            mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0).getString(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, "")

        if (userPic != null){

            Picasso.get()
                .load(Constants.AdminProfile_Path+userPic)
                .into(userImg)
        }

        if (isLogin){

            if (uName!!.isNotEmpty()){

                userName.text = uName
            }
            else {

                userName.text = "Stranger"
            }
        }
        else{

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

        if (token != null){

            dashboard_view.getTotalProducts(this, "x-token", loading_dash).observe(viewLifecycleOwner, Observer {

                if (it != null){

                    if (it.getSuccess()!!){

                        val totalProducts = it.getData()!!.ListproductResponce!!.size
                        Log.d("catSize", totalProducts.toString())
                        totalPro_txt.text = totalProducts.toString()
                    }
                }
            })
        }
    }

    private fun getTotalCategories() {

        if (token != null){

            dashboard_view.getTotalCategories(this, token.toString(), loading_dash).observe(viewLifecycleOwner, Observer {

                if (it != null){

                    if (it.getSuccess()!!){

                        val totalCategory = it.getData()!!.ListcategoryResponce!!.size
                        Log.d("catSize", totalCategory.toString())
                        totalCat_txt.text = totalCategory.toString()
                    }
                }

            })
        }
    }

    private fun getRecentOrders() {

        if (token != null){

            dashboard_view.getRecentOrders(this, token.toString(), loading_dash).observe(viewLifecycleOwner, Observer {

                if (it != null){

                    if (it.getSuccess()!!){

                        //Toast.makeText(context, "Recent Orders", Toast.LENGTH_SHORT).show()

                        orderImg.clear()
                        orderId.clear()
                        orderPrice.clear()
                        orderName.clear()
                        recycleView_models.clear()
                        newOrders = 0
                        acceptedOrders = 0
                        beingPre = 0
                        ready = 0

                        for (i in it.getData()!!.ListOrderResponce!!.indices){

                            if (it.getData()!!.ListOrderResponce!![i].orderStatus.equals("0") && it.getData()!!.ListOrderResponce!![i].cancelStatus == false){

                                newOrders++
                            }
                            else if (it.getData()!!.ListOrderResponce!![i].orderStatus.equals("1") && it.getData()!!.ListOrderResponce!![i].cancelStatus == false){

                                acceptedOrders++
                            }
                            else if (it.getData()!!.ListOrderResponce!![i].orderStatus.equals("2") && it.getData()!!.ListOrderResponce!![i].cancelStatus == false){

                                beingPre++
                            }
                            else if (it.getData()!!.ListOrderResponce!![i].orderStatus.equals("3") && it.getData()!!.ListOrderResponce!![i].cancelStatus == false){

                                ready++
                            }
                            else{
                                
                            }
                        }

                        if (it.getData()!!.ListOrderResponce!!.size > 3){

                            for (i in 0..2){

                                orderId.add("OrderId : #"+it.getData()!!.ListOrderResponce!![i].id.toString())
                                orderName.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![0].Productprice!!.createdBy.toString())
                                orderPrice.add("$"+it.getData()!!.ListOrderResponce!![i].amount.toString())
                                orderImg.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![0].Productprice!!.productPic.toString())

                            }
                        }
                        else{

                            for (i in it.getData()!!.ListOrderResponce!!.indices){

                                orderId.add("OrderId : #"+it.getData()!!.ListOrderResponce!![i].id.toString())
                                orderName.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![0].Productprice!!.createdBy.toString())
                                orderPrice.add("$"+it.getData()!!.ListOrderResponce!![i].amount.toString())
                                orderImg.add(it.getData()!!.ListOrderResponce!![i].Orderlists!![0].Productprice!!.productPic.toString())
                            }
                        }

                        loading_dash.visibility = View.GONE

                        setUpRecentOrdersRV()
                    }
                }
            })
        }
    }

    private fun setUpRecentOrdersRV() {


        Log.d("orders", newOrders.toString())
        Log.d("orders", acceptedOrders.toString())
        Log.d("orders", beingPre.toString())
        Log.d("orders", ready.toString())

        newOrders_txt.text = newOrders.toString()
        accepted_txt.text = acceptedOrders.toString()
        beingPrep_txt.text = beingPre.toString()
        readyTo_txt.text = ready.toString()

        for (i in orderId.indices){

            recycleView_models.add(RecycleModel(orderId[i],orderName[i], orderPrice[i], orderImg[i]))
        }


       // recycleView_models.add(RecycleModel("OrderId : #4545","Ramu kaka", "$4", "asda"))
       // recycleView_models.add(RecycleModel("OrderId : #4545","Ramu kaka", "$7", "asda"))

        recycleView_adapter_RO = RecycleView_RO(activity, recycleView_models)
        recyclerView.adapter = recycleView_adapter_RO
        recycleView_adapter_RO.notifyDataSetChanged()
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

    private fun setData(){

        val income : ArrayList<BarEntry> = ArrayList()
        val labelNames : ArrayList<String> = ArrayList()
        fillWeekSales()
        var totalSales = 0.00

        for (i in weekSales.indices){

            val weeks = weekSales.get(i).weeks
            val sales = weekSales.get(i).sales

            totalSales += sales

            income.add(BarEntry(i.toFloat(), sales.toFloat()))
            labelNames.add(weeks)
        }

        total_income.text = "$"+totalSales.toString()

        val barDataSet : BarDataSet = BarDataSet(income, "Weekly Income")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        chart.description.text = "Weeks"
        barDataSet.valueTextColor = android.graphics.Color.BLACK
        barDataSet.valueTextSize = 8f

        val barData : BarData = BarData(barDataSet)

        chart.data = barData

        //Set xAxis Value Formatter
        val xAxis : XAxis = chart.xAxis
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

        menu.getItem(0).setVisible(false)
    }

}