package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.compose.ui.unit.Constraints
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.utils.Constants


class AddItemsFrag : Fragment() {

    lateinit var mainActivity: MainActivity

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
        val view : View = inflater.inflate(R.layout.fragment_add_items_frag, container, false)

        mainActivity = activity as MainActivity

        mainActivity.toolbar_const.setTitle("Add Item")
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        // get reference to the string array that we just created
        /*val languages = ArrayList<String>()
        languages.add("Breakfast")
        languages.add("Porridge")
        languages.add("Dinner")*/
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, Constants.totalCategories)
        // get reference to the autocomplete text view
        val autocompleteTV = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV.setAdapter(arrayAdapter)

        return view
    }


}