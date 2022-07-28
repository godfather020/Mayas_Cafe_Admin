package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.utils.Constants
import com.google.android.material.tabs.TabLayout

class CurrentOrdersFrag : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var loading : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_current_orders_frag, container, false)
        mainActivity = (activity as MainActivity)

        //MainActivity.isBackPressed = true

        mainActivity.toolbar_const.title = "Current Orders"
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        viewPager = view.findViewById(R.id.viewPager)
        loading = view.findViewById(R.id.loading_currentOrder)
        tabLayout = view.findViewById(R.id.orders_tab)

        loading.visibility = View.VISIBLE

        val adapter = CustomViewAdapter(childFragmentManager)

        adapter.addFragment(NewOrders(), "New Orders")
        adapter.addFragment(AcceptedOrders(), "Accepted Orders")
        adapter.addFragment(BeingPreparedOrders(), "Being Prepared")
        adapter.addFragment(ReadyToPickUp(), "Ready to PickUp")

        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)

        when (Constants.SET_ORDER_TAB) {
            0 -> {

                viewPager.currentItem = 0
                loading.visibility = View.GONE
            }
            1 -> {

                viewPager.currentItem = 1
                loading.visibility = View.GONE
            }
            2 -> {

                viewPager.currentItem = 2
                loading.visibility = View.GONE
            }
            else -> {

                viewPager.currentItem = 3
                loading.visibility = View.GONE
            }
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return view
    }


    class CustomViewAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val arrayFragment = ArrayList<Fragment>()
        private val stringArray = ArrayList<String>()

        fun addFragment(fragment: Fragment, s: String) {

            this.arrayFragment.add(fragment)
            this.stringArray.add(s)
        }

        override fun getCount(): Int {
            return arrayFragment.size
        }

        override fun getItem(position: Int): Fragment {
            return arrayFragment[position]
        }

        override fun getPageTitle(position: Int): CharSequence {
            return stringArray[position]
        }
    }
}