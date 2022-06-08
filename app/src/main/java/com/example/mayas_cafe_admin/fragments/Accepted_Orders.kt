package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_AO
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_NO
import java.util.ArrayList


class Accepted_Orders : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    lateinit var  recyclerView: RecyclerView
    lateinit var recycleView_adapter_AO : RecycleView_AO
    lateinit var search : MenuItem

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
        val view : View =  inflater.inflate(R.layout.fragment_accepted_orders, container, false)

        recyclerView= view.findViewById(R.id.acceptedOrders_rv)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        setHasOptionsMenu(true)

        setUpAcceptedOrderRv()

        return view
    }

    private fun setUpAcceptedOrderRv() {

        recycleView_models.clear()

        recycleView_models.add(RecycleModel("#4545","9:40 PM", "$9", "Accepted Order", "4", "adsadsa"))
        recycleView_models.add(RecycleModel("#4545","8:30 PM", "$4", "Accepted Order", "4", "adsadsa"))
        recycleView_models.add(RecycleModel("#4545","7:50 PM", "$7", "Accepted Order", "4", "adsadsa"))

        recycleView_adapter_AO = RecycleView_AO(activity, recycleView_models)
        recyclerView.adapter = recycleView_adapter_AO
        recycleView_adapter_AO.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        search = menu.findItem(R.id.search)
        val searchView : androidx.appcompat.widget.SearchView = search.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                recycleView_adapter_AO.filter.filter(newText)
                return false
            }
        })
    }

}