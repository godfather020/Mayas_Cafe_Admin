package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.Profile_ViewModel
import com.example.mayas_cafe_admin.utils.Constants
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
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
    lateinit var profileViewModel: Profile_ViewModel
    lateinit var loading_profile: ProgressBar
    lateinit var user_img: CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_profile_frag, container, false)

        mainActivity = (activity as MainActivity)

        profileViewModel = ViewModelProvider(this).get(Profile_ViewModel::class.java)

        mainActivity.toolbar_const.title = "Profile"
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        MainActivity.isBackPressed = true

        edit_user_info  = view.findViewById(R.id.user_edit)
        edit_btn = view.findViewById(R.id.user_edit_btn)
        userName = view.findViewById(R.id.user_name)
        userNum = view.findViewById(R.id.user_num)
        userEmail = view.findViewById(R.id.user_email)
        userAddress = view.findViewById(R.id.user_address)
        loading_profile = view.findViewById(R.id.profile_loading)
        user_img = view.findViewById(R.id.user_img);

        loading_profile.visibility = View.VISIBLE

        getAdminProfile()

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

        edit_user_info.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("userName", userName.text.toString())
            bundle.putString("userPhone", userNum.text.toString())
            bundle.putString("userEmail", userEmail.text.toString())
            bundle.putString("userAddress", userAddress.text.toString())

            mainActivity.loadFragment(fragmentManager, Edit_Profile_frag(), R.id.fragment_container, false, "Edit Profile", bundle)
        }

        edit_btn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("userName", userName.text.toString())
            bundle.putString("userPhone", userNum.text.toString())
            bundle.putString("userEmail", userEmail.text.toString())
            bundle.putString("userAddress", userAddress.text.toString())

            mainActivity.loadFragment(fragmentManager, Edit_Profile_frag(), R.id.fragment_container, false, "Edit Profile", bundle)
        }

        return view
    }

    private fun getAdminProfile() {

        val token = mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0).getString(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, "")

        if (token!!.isNotEmpty()) {

            profileViewModel.getAdminProfile(this, token).observe(viewLifecycleOwner, Observer {

                if (it != null){

                    if (it.getSuccess() == true){

                        val user_Email = mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.USER_E, 0).getString(Constants.sharedPrefrencesConstant.USER_E, "")

                        userName.text = it.getData()!!.user!!.userName
                        userNum.text = it.getData()!!.user!!.phoneNumber
                        if (user_Email != null) {
                            userEmail.text = user_Email
                        }
                        userAddress.text = it.getData()!!.user!!.address

                        Picasso.get()
                            .load(Constants.AdminProfile_Path+it.getData()!!.user!!.profilePic)
                            .into(user_img)

                        mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.USER_I, 0).edit().putString(Constants.sharedPrefrencesConstant.USER_I, it.getData()!!.user!!.profilePic).apply()

                        loading_profile.visibility = View.GONE

                        //Toast.makeText(context, "Admin Profile Fetched", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

    }

}