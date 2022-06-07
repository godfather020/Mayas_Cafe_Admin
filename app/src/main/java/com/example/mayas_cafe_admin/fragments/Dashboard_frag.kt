package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
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
import org.eazegraph.lib.charts.BarChart

class Dashboard_frag : Fragment(){

    var weekSales : ArrayList<WeekSales> = ArrayList()
    private lateinit var chart: com.github.mikephil.charting.charts.BarChart
    lateinit var total_income : TextView
    lateinit var mainActivity: MainActivity
    lateinit var allOrders_card : CardView
    lateinit var newOrders_card : CardView
    lateinit var prepOrders_card : CardView
    lateinit var readyToDiliver : CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_dashboard_frag, container, false)
        mainActivity = (activity as MainActivity)

        mainActivity.toolbar_const.title = ""

        MainActivity.isBackPressed = false
        chart = view.findViewById(R.id.bar_chart)
        total_income = view.findViewById(R.id.target_amt)
        allOrders_card = view.findViewById(R.id.allorders_card)
        newOrders_card = view.findViewById(R.id.neworders_card)
        prepOrders_card = view.findViewById(R.id.preporders_card)
        readyToDiliver = view.findViewById(R.id.deliverorders_card)

        setData()

        allOrders_card.setOnClickListener {

            mainActivity.navigationView.setCheckedItem(R.id.AllOrders)
            mainActivity.loadFragment(fragmentManager, CurrentOrders_frag(), R.id.fragment_container, false, "New Orders", null)
            Constants.SET_ORDER_TAB = 0
        }

        newOrders_card.setOnClickListener {

            mainActivity.navigationView.setCheckedItem(R.id.AllOrders)
            mainActivity.loadFragment(fragmentManager, CurrentOrders_frag(), R.id.fragment_container, false, "New Orders", null)
            Constants.SET_ORDER_TAB = 0
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

        return view
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
}