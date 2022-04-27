package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R

class PaymentHistory_frag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_payment_history_frag, container, false)
        MainActivity.isBackPressed = true
        return view
    }

}