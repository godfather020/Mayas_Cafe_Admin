package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.text.createTextLayoutResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_M
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_NO
import java.util.ArrayList


class Menu_frag : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    lateinit var  recyclerView: RecyclerView
    lateinit var recycleView_adapter_M : RecycleView_M
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_menu_frag, container, false)

        mainActivity = activity as MainActivity

        MainActivity.isBackPressed = true

        mainActivity.toolbar_const.title = "Menu"
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        recyclerView= view.findViewById(R.id.menu_rv)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        setUpMenuRv()

        return view
    }

    private fun setUpMenuRv() {

        recycleView_models.clear()

        recycleView_models.add(RecycleModel("Breakfast", "sada"))
        recycleView_models.add(RecycleModel("Porridge", "sada"))
        recycleView_models.add(RecycleModel("Seafood", "sada"))
        recycleView_models.add(RecycleModel("Sides", "sada"))
        recycleView_models.add(RecycleModel("Appetizers", "sada"))
        recycleView_models.add(RecycleModel("Lunch & Dinner", "sada"))
        recycleView_models.add(RecycleModel("Soup", "sada"))

        recycleView_adapter_M = RecycleView_M(activity, recycleView_models)
        recyclerView.adapter = recycleView_adapter_M
        recycleView_adapter_M.notifyDataSetChanged()
    }

}