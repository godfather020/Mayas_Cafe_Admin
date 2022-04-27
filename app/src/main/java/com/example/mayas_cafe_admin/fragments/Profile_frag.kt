package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import kotlin.concurrent.fixedRateTimer


class Profile_frag : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var edit_user_info : ImageView
    lateinit var edit_btn : ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_profile_frag, container, false)

        mainActivity = (activity as MainActivity)

        mainActivity.toolbar_const.title = "Profile"
        mainActivity.toolbar_const.titleMarginStart = 150
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        MainActivity.isBackPressed = true

        edit_user_info  = view.findViewById(R.id.user_edit)
        edit_btn = view.findViewById(R.id.user_edit_btn)

        edit_user_info.setOnClickListener {

            mainActivity.loadFragment(fragmentManager, Edit_Profile_frag(), R.id.fragment_container, false, "Edit Profile", null)
        }

        edit_btn.setOnClickListener {

            mainActivity.loadFragment(fragmentManager, Edit_Profile_frag(), R.id.fragment_container, false, "Edit Profile", null)
        }

        return view
    }

}