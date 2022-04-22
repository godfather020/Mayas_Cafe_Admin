package com.example.mayas_cafe_admin.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import android.widget.ImageButton
import app.futured.donut.DonutProgressView
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.FirebaseCloudMsg
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.compose.animation.slideInVertically
import androidx.compose.ui.graphics.Color
import androidx.core.view.GravityCompat
import androidx.core.view.get
import com.boyzdroizy.simpleandroidbarchart.SimpleBarChart
import com.example.mayas_cafe_admin.utils.Functions
import org.eazegraph.lib.charts.BarChart
import org.eazegraph.lib.models.BarModel
import java.util.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt
import java.util.Random as JavaUtilRandom

class Dashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var isBackPressed = false
    var toolbar_const: Toolbar? = null
    lateinit var navigationView: NavigationView
    var drawerLayout: DrawerLayout? = null
    var close: ImageButton? = null
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    lateinit var simpleBarChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setUpToolbar()
        val deviceToken = FirebaseCloudMsg.getToken(this)
        Log.d("Token", deviceToken)

        simpleBarChart = findViewById(R.id.bar_chart)


        /*val chartData = ( 7 downTo 1 ).map { nextInt(10, 100) }.toMutableList()
        val intervalData = (0 downTo 0).map { it }.toMutableList()

        simpleBarChart.setChartData(chartData, intervalData)
        simpleBarChart.setMaxValue(100)
        simpleBarChart.setMinValue(0)*/

        simpleBarChart.addBar(BarModel("Sun",2000f , 0xFFE82A36.toInt()));
        simpleBarChart.addBar(BarModel("Mon",1050f , 0xFFE82A36.toInt()));
        simpleBarChart.addBar(BarModel("Tue",32000f , 0xFFE82A36.toInt()));
        simpleBarChart.addBar(BarModel("Wed",230f , 0xFFE82A36.toInt()));
        simpleBarChart.addBar(BarModel("Thu",210f , 0xFFE82A36.toInt()));
        simpleBarChart.addBar(BarModel("Fri",180f , 0xFFE82A36.toInt()));
        simpleBarChart.addBar(BarModel("Sat",130f , 0xFFE82A36.toInt()));

        simpleBarChart.isShowValues = true
        simpleBarChart.isShowDecimal = true


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
    }

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
}