package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_AT
import java.util.ArrayList

class CurrentTransactions_frag : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    lateinit var  recyclerView: RecyclerView
    lateinit var recycleView_adapter_AT : RecycleView_AT
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
        val view : View = inflater.inflate(R.layout.fragment_current_transactions_frag, container, false)

        recyclerView= view.findViewById(R.id.currentTrans_rv)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        setHasOptionsMenu(true)

        setUpCurrentTransRv()

        return view
    }

    private fun setUpCurrentTransRv() {

        recycleView_models.clear()

        recycleView_models.add(RecycleModel("#4534","25 Apr 2022", "$9", "Received", "By Cash", "adsadsa"))
        recycleView_models.add(RecycleModel("#5667","25 Apr 2022", "$9", "Received", "By Cash", "adsadsa"))
        recycleView_models.add(RecycleModel("#4564","25 Apr 2022", "$9", "Received", "By Cash", "adsadsa"))

        recycleView_adapter_AT = RecycleView_AT(activity, recycleView_models)
        recyclerView.adapter = recycleView_adapter_AT
        recycleView_adapter_AT.notifyDataSetChanged()
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
                recycleView_adapter_AT.filter.filter(newText)
                return false
            }
        })
    }
}