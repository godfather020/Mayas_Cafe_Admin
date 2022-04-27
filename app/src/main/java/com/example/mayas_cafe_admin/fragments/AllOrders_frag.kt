package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R



class AllOrders_frag : Fragment() {

    lateinit var mainActivity : MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_all_orders_frag, container, false)
        mainActivity = (activity as MainActivity)

        MainActivity.isBackPressed = true

        mainActivity.toolbar_const.title = "All Orders"
        mainActivity.toolbar_const.titleMarginStart = 150
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        return view
    }

}