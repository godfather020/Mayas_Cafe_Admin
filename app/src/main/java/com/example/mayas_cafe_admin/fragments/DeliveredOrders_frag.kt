package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_DO
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_RTP
import java.util.ArrayList


class DeliveredOrders_frag : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    lateinit var  recyclerView: RecyclerView
    lateinit var recycleView_adapter_DO : RecycleView_DO

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
        val view : View = inflater.inflate(R.layout.fragment_delivered_orders_frag, container, false)

        recyclerView= view.findViewById(R.id.deliveredOrder_rv)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        setUpDeliveredOrderRv()

        return view
    }

    private fun setUpDeliveredOrderRv() {

        recycleView_models.clear()

        recycleView_models.add(RecycleModel("#4545","9:40 PM", "$9", "Delivered", "4", "adsadsa"))
        recycleView_models.add(RecycleModel("#4545","8:30 PM", "$4", "Delivered", "4", "adsadsa"))
        recycleView_models.add(RecycleModel("#4545","7:50 PM", "$7", "Delivered", "4", "adsadsa"))

        recycleView_adapter_DO = RecycleView_DO(activity, recycleView_models)
        recyclerView.adapter = recycleView_adapter_DO
        recycleView_adapter_DO.notifyDataSetChanged()
    }


}