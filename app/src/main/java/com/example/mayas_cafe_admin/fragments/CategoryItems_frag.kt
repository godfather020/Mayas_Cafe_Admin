package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.compose.ui.unit.Constraints
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_M
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_MI
import com.example.mayas_cafe_admin.utils.Constants
import java.util.ArrayList

class CategoryItems_frag : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    lateinit var  recyclerView: RecyclerView
    lateinit var recycleView_adapter_MI : RecycleView_MI
    lateinit var mainActivity: MainActivity
    lateinit var addItems : Button

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
        val view : View = inflater.inflate(R.layout.fragment_category__items_frag, container, false)

        MainActivity.isBackPressed = true

        mainActivity = activity as MainActivity

        mainActivity.toolbar_const.title = Constants.categoryName
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        addItems = view.findViewById(R.id.items_add)
        recyclerView= view.findViewById(R.id.category_items_rv)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        setUpMenuItemsRv()

        addItems.setOnClickListener {

            mainActivity.loadFragment(fragmentManager, AddItems_frag(), R.id.fragment_container, false, "AddItems", null)
        }

        return view
    }

    private fun setUpMenuItemsRv() {

        recycleView_models.clear()

        recycleView_models.add(RecycleModel("Ackee", "sada"))
        recycleView_models.add(RecycleModel("Sautéed Kale", "sada"))
        recycleView_models.add(RecycleModel("Scramble Tofu", "sada"))
        recycleView_models.add(RecycleModel("Orange Juice", "sada"))


        recycleView_adapter_MI = RecycleView_MI(activity, recycleView_models)
        recyclerView.adapter = recycleView_adapter_MI
        recycleView_adapter_MI.notifyDataSetChanged()
    }


}