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
import com.google.android.material.tabs.TabLayout


class PastOrdersFrag : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout

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
        val view : View = inflater.inflate(R.layout.fragment_past_orders_frag, container, false)

        mainActivity = (activity as MainActivity)

        MainActivity.isBackPressed = true

        mainActivity.toolbar_const.title = "Past Orders"
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        viewPager = view.findViewById(R.id.viewPager)

        tabLayout = view.findViewById(R.id.orders_tab)

        val adapter = PastOrdersFrag.CustomViewAdapter(childFragmentManager)

        adapter.addFragment(DeliveredOrdersFrag(), "Delivered Orders")
        adapter.addFragment(CancelledOrderFrag(), "Cancelled Orders")

        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)

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