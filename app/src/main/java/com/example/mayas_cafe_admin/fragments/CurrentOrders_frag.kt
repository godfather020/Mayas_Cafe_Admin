package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.utils.Constants
import com.google.android.material.tabs.TabLayout


class CurrentOrders_frag : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_current_orders_frag, container, false)
        mainActivity = (activity as MainActivity)

        //MainActivity.isBackPressed = true

        mainActivity.toolbar_const.title = "Current Orders"
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        viewPager = view.findViewById(R.id.viewPager)

        tabLayout = view.findViewById(R.id.orders_tab)

        val adapter = CustomViewAdapter(childFragmentManager)

        adapter.addFragment(New_Orders(), "New Orders")
        adapter.addFragment(Accepted_Orders(), "Accepted Orders")
        adapter.addFragment(BeingPrepared_orders(), "Being Prepared")
        adapter.addFragment(ReadyToPickUp(), "Ready to PickUp")

        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)

        if (Constants.SET_ORDER_TAB == 0){

            viewPager.setCurrentItem(0)
        }
        else if (Constants.SET_ORDER_TAB == 1){

            viewPager.setCurrentItem(1)
        }
        else if (Constants.SET_ORDER_TAB == 2){

            viewPager.setCurrentItem(2)
        }
        else{

            viewPager.setCurrentItem(3)
        }

        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return view
    }


    class CustomViewAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        val arrayFragment = ArrayList<Fragment>()
        val stringArray = ArrayList<String>()

        fun addFragment(fragment: Fragment, s: String) {

            this.arrayFragment.add(fragment)
            this.stringArray.add(s)
        }

        override fun getCount(): Int {
            return arrayFragment.size
        }

        override fun getItem(position: Int): Fragment {
            return arrayFragment.get(position)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return stringArray.get(position)
        }
    }
}