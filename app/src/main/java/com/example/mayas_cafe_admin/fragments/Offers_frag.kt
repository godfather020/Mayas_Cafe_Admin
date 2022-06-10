package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.google.android.material.tabs.TabLayout


class Offers_frag : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout
    lateinit var addNewOffer : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_offers_frag, container, false)

        mainActivity = (activity as MainActivity)

        MainActivity.isBackPressed = true

        mainActivity.toolbar_const.title = "My Offers"
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        viewPager = view.findViewById(R.id.viewPager)

        tabLayout = view.findViewById(R.id.offers_tab)

        addNewOffer = view.findViewById(R.id.add_new_offer)

        val adapter = CurrentOrders_frag.CustomViewAdapter(childFragmentManager)

        adapter.addFragment(ActiveOffers(), "Active Offers")
        adapter.addFragment(InactiveOffers_frag(), "Inactive Offers")
        adapter.addFragment(ExpiredOffers_frag(), "Expired Offers")

        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)

        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        addNewOffer.setOnClickListener {

            mainActivity.loadFragment(fragmentManager, AddNewOffer(), R.id.fragment_container, false, "Add New Offer", null)
        }

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