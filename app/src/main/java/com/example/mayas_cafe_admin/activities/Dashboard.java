package com.example.mayas_cafe_admin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mayas_cafe_admin.FirebaseCloudMsg;
import com.example.mayas_cafe_admin.R;
import com.example.mayas_cafe_admin.utils.Functions;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import app.futured.donut.DonutProgressView;
import app.futured.donut.DonutSection;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem;
import np.com.susanthapa.curved_bottom_navigation.CurvedBottomNavigationView;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private boolean isBackPressed = false;
    Toolbar toolbar_const;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ImageButton close;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DonutProgressView donutProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setUpToolbar();

        String deviceToken = FirebaseCloudMsg.getToken(this);

        Log.d("Token", deviceToken);

        donutProgressView = findViewById(R.id.earning_progress);

        donutProgressView.setCap(5f);
        donutProgressView.setMasterProgress(1f);
        donutProgressView.setGapAngleDegrees(270f);

       DonutSection sactions = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            sactions = new DonutSection("earning", Color.RED, 0.5f);
        }
        DonutSection sactions1 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            sactions1 = new DonutSection("earning1", R.color.Get_start_title, 1f);
        }
        DonutSection sactions2 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            sactions2 = new DonutSection("earning2", R.color.Category_title, 2f);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            donutProgressView.submitData(Arrays.asList(sactions, sactions1, sactions2));
        }
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

    public void setUpToolbar(){

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        Menu menu = navigationView.getMenu();
        toolbar_const = findViewById(R.id.toolbar_const);
        setSupportActionBar(toolbar_const);
        navigationView.setNavigationItemSelectedListener(this);
        Functions.setArrow(navigationView);
        //navigationView.setCheckedItem(R.id.homeNav);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar_const, R.string.app_name, R.string.app_name);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.group_8);

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                actionBarDrawerToggle.syncState();
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.group_8);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                actionBarDrawerToggle.syncState();
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.group_8);

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                actionBarDrawerToggle.syncState();
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.group_8);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                actionBarDrawerToggle.syncState();
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.group_8);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.notify){
            Toast.makeText(getApplicationContext(), "Notifications", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.setting){
            Toast.makeText(getApplicationContext(), "Setting", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if(isBackPressed){
            super.onBackPressed();
            return;
        }

        Toast.makeText(Dashboard.this, "Press again to exit", Toast.LENGTH_SHORT).show();
        isBackPressed = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        },2000);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            //go to home
            case R.id.AllOrders:
                break;

            case R.id.PayHistory:
                break;

            case R.id.Menu:

                break;

            case R.id.Offers:
                break;

            case R.id.logoutNav:
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}