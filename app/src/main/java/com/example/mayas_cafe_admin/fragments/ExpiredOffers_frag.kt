package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_AO
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_O
import java.util.ArrayList


class ExpiredOffers_frag : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    lateinit var  recyclerView: RecyclerView
    lateinit var recycleView_adapter_O : RecycleView_O
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
        val view : View = inflater.inflate(R.layout.fragment_expired_offers_frag, container, false)

        recyclerView= view.findViewById(R.id.expired_offers_rv)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        setHasOptionsMenu(true)

        setUpExpiredOffersRv()

        return view
    }

    private fun setUpExpiredOffersRv() {

        recycleView_models.clear()

        recycleView_models.add(RecycleModel("40%","sadads", "Porridge", "Flat discount on every purchase", "Offer available only 21 to 23 April"))
        recycleView_models.add(RecycleModel("40%","sadads", "Sauteed Kale", "For first time user only", "Offer available only 21 to 23 April"))
        recycleView_models.add(RecycleModel("40%","sadads", "Porridge", "Flat discount on every purchase", "Offer available only 21 to 23 April"))
        recycleView_models.add(RecycleModel("40%","sadads", "Sauteed Kale", "For first time user only", "Offer available only 21 to 23 April"))

        recycleView_adapter_O = RecycleView_O(activity, recycleView_models)
        recyclerView.adapter = recycleView_adapter_O
        recycleView_adapter_O.notifyDataSetChanged()
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
                recycleView_adapter_O.filter.filter(newText)
                return false
            }
        })
    }
}