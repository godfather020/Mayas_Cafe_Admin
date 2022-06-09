package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.google.android.material.tabs.TabLayout

class PaymentHistory_frag : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_payment_history_frag, container, false)

        mainActivity = activity as MainActivity

        MainActivity.isBackPressed = true

        mainActivity.toolbar_const.setTitle("Payment History")
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        viewPager = view.findViewById(R.id.payment_viewPager)

        tabLayout = view.findViewById(R.id.payments_tab)

        val adapter = CurrentOrders_frag.CustomViewAdapter(childFragmentManager)

        adapter.addFragment(AllTransactions_frag(), "All Transactions")
        adapter.addFragment(CurrentTransactions_frag(), "Current Transactions")
        adapter.addFragment(WeklyTransactions_frag(), "Weekly Transactions")

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