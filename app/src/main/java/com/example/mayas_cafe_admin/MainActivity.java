package com.example.mayas_cafe_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayas_cafe_admin.activities.Login;
import com.example.mayas_cafe_admin.fragments.CurrentOrdersFrag;
import com.example.mayas_cafe_admin.fragments.DashboardFrag;
import com.example.mayas_cafe_admin.fragments.Menu_frag;
import com.example.mayas_cafe_admin.fragments.Notification_frag;
import com.example.mayas_cafe_admin.fragments.Offers_frag;
import com.example.mayas_cafe_admin.fragments.PastOrdersFrag;
import com.example.mayas_cafe_admin.fragments.PaymentHistory_frag;
import com.example.mayas_cafe_admin.fragments.ProfileFrag;
import com.example.mayas_cafe_admin.utils.Constants;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    public static Boolean isBackPressed = false;
    ImageButton nav_close;
    TextView nav_userName;
    CircleImageView adminPic;
    ActionBarDrawerToggle actionBarDrawerToggle;
    public Toolbar toolbar_const;
    public NavigationView navigationView;
    Boolean isLogin = false;
    String userPic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        toolbar_const = findViewById(R.id.toolbar_const);

        isLogin = getSharedPreferences(Constants.sharedPrefrencesConstant.LOGIN, MODE_PRIVATE).getBoolean(Constants.sharedPrefrencesConstant.LOGIN, false);

        Constants.isLogin = isLogin;

        String token = getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, MODE_PRIVATE).getString(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, "");

        if (token != null){

            Constants.DEVICE_TOKEN = token;

        }

        setUpToolbar();

        ConstraintLayout fragmentContainer = findViewById(R.id.fragment_container);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFrag()).commit();
    }

    private void setUpToolbar() {

        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        Menu menu = navigationView.getMenu();
        setSupportActionBar(toolbar_const);
        navigationView.setNavigationItemSelectedListener(this);
        //Functions.setArrow(navigationView);

        if (isLogin){

            navigationView.getMenu().getItem(7).setTitle("Logout");
        }
        else {

            navigationView.getMenu().getItem(7).setTitle("Login");
        }

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar_const,
                R.string.app_name,
                R.string.app_name
        ){

            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);

                nav_close = findViewById(R.id.nav_close_img);
                nav_userName = findViewById(R.id.nav_userName);
                adminPic = findViewById(R.id.adminPic);

                String userName = getSharedPreferences(Constants.sharedPrefrencesConstant.USER_N, MODE_PRIVATE).getString(Constants.sharedPrefrencesConstant.USER_N, "");
                userPic = getSharedPreferences(Constants.sharedPrefrencesConstant.USER_I, MODE_PRIVATE).getString(Constants.sharedPrefrencesConstant.USER_I, "");

                if (isLogin) {

                    if (userName.isEmpty()) {

                        nav_userName.setText("Stranger");
                    } else {

                        nav_userName.setText(userName);
                    }

                    if (!userPic.isEmpty()){

                        Picasso.get()
                                .load(Constants.AdminProfile_Path+userPic)
                                .into(adminPic);
                    }
                }
                else {

                    nav_userName.setText("Stranger");
                }


                nav_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drawerLayout.isDrawerOpen(GravityCompat.START);
                        {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                    }
                });
            }
        };

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_const.getNavigationIcon().setTint(getResources().getColor(R.color.black));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);

                    toolbar_const.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getSupportFragmentManager().popBackStack();
                        }
                    });


                } else {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    actionBarDrawerToggle.syncState();
                    toolbar_const.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            drawerLayout.openDrawer(GravityCompat.START);
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.notify) {
            //Toast.makeText(getApplicationContext(), "Notifications", Toast.LENGTH_SHORT).show();

            loadFragment(getSupportFragmentManager(), new Notification_frag(), R.id.fragment_container, false, "Notifications", null);

        } else if (id == R.id.setting) {
            Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.scanner) {
            Toast.makeText(getApplicationContext(), "Scanner", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if (isBackPressed) {
            super.onBackPressed();
            finish();
            return;
        }

            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            isBackPressed = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isBackPressed = false;
                }
            }, 2000);
        }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.Profile:

                loadFragment(getSupportFragmentManager(), new ProfileFrag(), R.id.fragment_container, false, "Profile", null );
                break;

            case R.id.AllOrders:
                Constants.SET_ORDER_TAB = 0;
                loadFragment(getSupportFragmentManager(), new CurrentOrdersFrag(), R.id.fragment_container, false, "Profile", null );
                break;

            case R.id.pastOrders:
                loadFragment(getSupportFragmentManager(), new PastOrdersFrag(), R.id.fragment_container, false, "Past Orders", null);
                break;

            case R.id.Menu:
                loadFragment(getSupportFragmentManager(), new Menu_frag(), R.id.fragment_container, false, "Profile", null );
                break;

            case R.id.PayHistory:
                loadFragment(getSupportFragmentManager(), new PaymentHistory_frag(), R.id.fragment_container, false, "Profile", null );
                break;

            case R.id.Offers:
                loadFragment(getSupportFragmentManager(), new Offers_frag(), R.id.fragment_container, false, "Profile", null );
                break;

            case R.id.notify:
                loadFragment(getSupportFragmentManager(), new Notification_frag(), R.id.fragment_container, false, "Notifications", null);
                break;

            case R.id.logoutNav:

                if (isLogin) {

                    drawerLayout.closeDrawer(GravityCompat.START);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle("Do you want to logout");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            getSharedPreferences(Constants.sharedPrefrencesConstant.LOGIN, MODE_PRIVATE).edit().putBoolean(Constants.sharedPrefrencesConstant.LOGIN, false).apply();
                            startActivity(new Intent(MainActivity.this, Login.class));
                            finish();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    Dialog alertDialog = builder.create();
                    alertDialog.show();


                }
                else {

                    drawerLayout.closeDrawer(GravityCompat.START);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle("Do you want to login");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(MainActivity.this, Login.class));
                            finish();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    Dialog alertDialog = builder.create();
                    alertDialog.show();

                }

                break;
        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);

                    toolbar_const.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getSupportFragmentManager().popBackStack();
                        }
                    });


                } else {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    actionBarDrawerToggle.syncState();
                    toolbar_const.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            drawerLayout.openDrawer(GravityCompat.START);
                        }
                    });
                }
            }
        });

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    public void loadFragment(
            FragmentManager fragmentManager,
            Fragment fragment,
            int containerId,
            Boolean shouldRemovePreviousFragments,
            CharSequence currentTitle,
            Bundle arg
    ) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (shouldRemovePreviousFragments) {
            if (fragmentManager.getBackStackEntryCount() > 0) {
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                    fragmentManager.popBackStackImmediate();
                }
            }
        } else transaction.addToBackStack(currentTitle.toString());
        if (arg != null) {
            Log.d("bundle==", arg.toString());
            fragment.setArguments(arg);
        }
       /* transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )*/
        transaction.replace(containerId, fragment, currentTitle.toString()).commit();
    }
}