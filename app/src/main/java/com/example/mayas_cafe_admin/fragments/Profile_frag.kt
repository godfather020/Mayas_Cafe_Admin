package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.platform.LocalDensity
import androidx.fragment.app.FragmentActivity
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import kotlin.concurrent.fixedRateTimer


class Profile_frag : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var edit_user_info : ImageView
    lateinit var edit_btn : ImageButton
    lateinit var userName : TextView
    lateinit var userNum : TextView
    lateinit var userEmail : TextView
    lateinit var userAddress : TextView
    lateinit var args : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_profile_frag, container, false)

        mainActivity = (activity as MainActivity)

        mainActivity.toolbar_const.title = "Profile"
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        MainActivity.isBackPressed = true

        edit_user_info  = view.findViewById(R.id.user_edit)
        edit_btn = view.findViewById(R.id.user_edit_btn)
        userName = view.findViewById(R.id.user_name)
        userNum = view.findViewById(R.id.user_num)
        userEmail = view.findViewById(R.id.user_email)
        userAddress = view.findViewById(R.id.user_address)

        if (arguments != null){

            args = arguments?.getString("userName").toString()
            userName.text = args
            Log.d("userNameP", args)

            args = arguments?.getString("userPhone").toString()
            userNum.text = args
            Log.d("userPhoneP", args)

            args = arguments?.getString("userEmail").toString()
            userEmail.text = args
            Log.d("userEmailP", args)

            args = arguments?.getString("userAddress").toString()
            userAddress.text = args
            Log.d("userAddressP", args)
        }

        val bundle = Bundle()
        bundle.putString("userName", userName.text.toString())
        bundle.putString("userPhone", userNum.text.toString())
        bundle.putString("userEmail", userEmail.text.toString())
        bundle.putString("userAddress", userAddress.text.toString())

        edit_user_info.setOnClickListener {


            mainActivity.loadFragment(fragmentManager, Edit_Profile_frag(), R.id.fragment_container, false, "Edit Profile", bundle)
        }

        edit_btn.setOnClickListener {

            mainActivity.loadFragment(fragmentManager, Edit_Profile_frag(), R.id.fragment_container, false, "Edit Profile", bundle)
        }

        return view
    }

}