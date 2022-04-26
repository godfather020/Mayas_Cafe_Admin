package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.text.createTextLayoutResult
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R



class Menu_frag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_menu_frag, container, false)

        MainActivity.isBackPressed = true

        return view
    }

}