package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_BP
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_RTP
import java.util.ArrayList

class ReadyToPickUp : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    lateinit var  recyclerView: RecyclerView
    lateinit var recycleView_adapter_RTP : RecycleView_RTP
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
        val view : View = inflater.inflate(R.layout.fragment_ready_to_pick_up, container, false)

        recyclerView= view.findViewById(R.id.pickUp_rv)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        setPickUpRv()

        setHasOptionsMenu(true)

        return view
    }

    private fun setPickUpRv() {

        recycleView_models.clear()

        recycleView_models.add(RecycleModel("#4545","9:40 PM", "$9", "Ready to PickUp", "4", "adsadsa"))
        recycleView_models.add(RecycleModel("#4545","8:30 PM", "$4", "Ready to PickUp", "4", "adsadsa"))
        recycleView_models.add(RecycleModel("#4545","7:50 PM", "$7", "Ready to PickUp", "4", "adsadsa"))

        recycleView_adapter_RTP = RecycleView_RTP(activity, recycleView_models)
        recyclerView.adapter = recycleView_adapter_RTP
        recycleView_adapter_RTP.notifyDataSetChanged()

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
                recycleView_adapter_RTP.filter.filter(newText)
                return false
            }
        })
    }
}