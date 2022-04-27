package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R


class Edit_Profile_frag : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var save_edit : Button
    lateinit var user_edit_name : EditText
    lateinit var user_edit_phone : EditText
    lateinit var user_edit_email : EditText
    lateinit var user_edit_address : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_edit__profile_frag, container, false)

        mainActivity = (activity as MainActivity)
        save_edit = view.findViewById(R.id.save_edit)
        user_edit_name = view.findViewById(R.id.user_edit_name)
        user_edit_phone = view.findViewById(R.id.user_phone_edit)
        user_edit_email = view.findViewById(R.id.user_edit_email)
        user_edit_address = view.findViewById(R.id.user_edit_add)

        mainActivity.toolbar_const.title = ""

        save_edit.setOnClickListener {

            if (validateFields())

            requireFragmentManager().popBackStack()
        }

        return view
    }

    private fun validateFields(): Boolean {

        var userName = user_edit_name.text.toString()
        var userPhone = user_edit_phone.text.toString()
        var userEmail = user_edit_email.text.toString()
        var userAddress = user_edit_address.text.toString()

        if (userName.isEmpty() || !userName.contains("^a-zA-Z")){

            user_edit_name.requestFocus()
            user_edit_name.error = "Required Field"
        }


            return true
    }

}