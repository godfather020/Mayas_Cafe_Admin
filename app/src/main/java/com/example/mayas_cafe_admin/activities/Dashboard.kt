package com.example.mayas_cafe_admin.activities

import android.graphics.RectF
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.example.mayas_cafe_admin.FirebaseCloudMsg
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.utils.Functions
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.material.navigation.NavigationView

class Dashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    OnChartValueSelectedListener {
    private var isBackPressed = false
    var toolbar_const: Toolbar? = null
    lateinit var navigationView: NavigationView
    var drawerLayout: DrawerLayout? = null
    var close: ImageButton? = null
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null

    //lateinit var simpleBarChart: com.github.mikephil.charting.charts.BarChart
    private lateinit var chart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setUpToolbar()
        val deviceToken = FirebaseCloudMsg.getToken(this)
        Log.d("Token", deviceToken)

        //  simpleBarChart = findViewById(R.id.bar_chart)

        chart = findViewById(R.id.bar_chart)
        chart.setOnChartValueSelectedListener(this)

        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)

        chart.getDescription().setEnabled(false)

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(true)

        chart.setDrawGridBackground(false)


        // add a nice and smooth animation
        chart.animateY(1500)

        chart.legend.isEnabled = false

        setData()

    }

    private fun setData(){

        val values = java.util.ArrayList<BarEntry>()

        for (i in 0 until 8) {
            val multi: Float = (100 + 1).toFloat()
            val `val` = (Math.random() * multi).toFloat() + multi / 3
            values.add(BarEntry(i.toFloat(),`val`))
        }

        val set1: BarDataSet

        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            set1 = chart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "Data Set")
            set1.setColors(*ColorTemplate.VORDIPLOM_COLORS)
            set1.setDrawValues(false)
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)

            data.setValueTextSize(10f)
            data.barWidth = 0.9f
            chart.data = data
            chart.setFitBars(true)
        }

        chart.invalidate()

    }


    /*val chartData = ( 7 downTo 1 ).map { nextInt(10, 100) }.toMutableList()
    val intervalData = (0 downTo 0).map { it }.toMutableList()

    simpleBarChart.setChartData(chartData, intervalData)
    simpleBarChart.setMaxValue(100)
    simpleBarChart.setMinValue(0)*/

       /* simpleBarChart.addBar(BarModel("Sun",2000f , 0xFFE82A36.toInt()));
        simpleBarChart.addBar(BarModel("Mon",1050f , 0xFFE82A36.toInt()));
        simpleBarChart.addBar(BarModel("Tue",32000f , 0xFFE82A36.toInt()));
        simpleBarChart.addBar(BarModel("Wed",230f , 0xFFE82A36.toInt()));
        simpleBarChart.addBar(BarModel("Thu",210f , 0xFFE82A36.toInt()));
        simpleBarChart.addBar(BarModel("Fri",180f , 0xFFE82A36.toInt()));
        simpleBarChart.addBar(BarModel("Sat",130f , 0xFFE82A36.toInt()));

        simpleBarChart.isShowValues = true
        simpleBarChart.isShowDecimal = true*/


        //getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new Dashboard_frag()).commit();

        /*CurvedBottomNavigationView cbn = findViewById(R.id.chip_nav);
        CbnMenuItem dashboard = new CbnMenuItem(R.drawable.mdi___view_grid_outline, R.drawable.dashboard_anim, 0);
        CbnMenuItem search = new CbnMenuItem(R.drawable.icon_feather_search_r, R.drawable.search_anim, 0);
        CbnMenuItem favorite = new CbnMenuItem(R.drawable.icon_feather_heart_red, R.drawable.avd_anim, 0);
        CbnMenuItem profile = new CbnMenuItem(R.drawable.icon_feather_user_red, R.drawable.profile_anim,0);
        CbnMenuItem[] navigation_items = {dashboard,search,favorite,profile};
        cbn.setMenuItems(navigation_items, 0);

        cbn.setOnMenuItemClickListener(new Function2<CbnMenuItem, Integer, Unit>() {
            @Override
            public Unit invoke(CbnMenuItem cbnMenuItem, Integer integer) {

                switch (cbn.getSelectedIndex()){

                    case 0:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new Dashboard_frag()).commit();
                        break;
                    case 1:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new Search_frag()).commit();
                        break;
                    case 2:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new Favorite_frag()).commit();
                        break;
                    case 3:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new Profile_frag()).commit();
                        break;
                }
                return null;
            }
        });*/

    fun setUpToolbar() {

        drawerLayout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.nav_view)
        navigationView.bringToFront()
        val menu = navigationView.menu
        toolbar_const = findViewById(R.id.toolbar_const)
        setSupportActionBar(toolbar_const)
        navigationView.setNavigationItemSelectedListener(this)
        Functions.setArrow(navigationView)
        //navigationView.setCheckedItem(R.id.homeNav);
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar_const,
            R.string.app_name,
            R.string.app_name
        )
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.group_8)
        drawerLayout?.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                actionBarDrawerToggle!!.syncState()
                supportActionBar!!.setHomeAsUpIndicator(R.drawable.group_8)
            }

            override fun onDrawerOpened(drawerView: View) {
                actionBarDrawerToggle!!.syncState()
                supportActionBar!!.setHomeAsUpIndicator(R.drawable.group_8)
            }

            override fun onDrawerClosed(drawerView: View) {
                actionBarDrawerToggle!!.syncState()
                supportActionBar!!.setHomeAsUpIndicator(R.drawable.group_8)
            }

            override fun onDrawerStateChanged(newState: Int) {
                actionBarDrawerToggle!!.syncState()
                supportActionBar!!.setHomeAsUpIndicator(R.drawable.group_8)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.notify) {
            Toast.makeText(applicationContext, "Notifications", Toast.LENGTH_SHORT).show()
        } else if (id == R.id.setting) {
            Toast.makeText(applicationContext, "Setting", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onBackPressed() {
        if (isBackPressed) {
            super.onBackPressed()
            return
        }
        Toast.makeText(this@Dashboard, "Press again to exit", Toast.LENGTH_SHORT).show()
        isBackPressed = true
        Handler().postDelayed({ isBackPressed = false }, 2000)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.AllOrders -> {}
            R.id.PayHistory -> {}
            R.id.Menu -> {}
            R.id.Offers -> {}
            R.id.logoutNav -> {}
        }
        drawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }

    private val onValueSelectedRectF = RectF()

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e == null) return

        val bounds: RectF = onValueSelectedRectF
        chart.getBarBounds(e as BarEntry?, bounds)
        val position = chart.getPosition(e, AxisDependency.LEFT)

        Log.i("bounds", bounds.toString())
        Log.i("position", position.toString())

        Log.i(
            "x-index",
            "low: " + chart.lowestVisibleX + ", high: "
                    + chart.highestVisibleX
        )

        MPPointF.recycleInstance(position)
    }

    override fun onNothingSelected() {
    }

}

