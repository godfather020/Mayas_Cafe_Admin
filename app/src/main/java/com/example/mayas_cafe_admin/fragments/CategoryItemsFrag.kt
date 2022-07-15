package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.CategoryItemsViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_MI
import com.example.mayas_cafe_admin.utils.Constants
import kotlin.collections.ArrayList

class CategoryItemsFrag : Fragment() {

    private var recycleView_models = ArrayList<RecycleModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recycleView_adapter_MI: RecycleView_MI
    private lateinit var mainActivity: MainActivity
    private lateinit var addItems: Button
    private lateinit var categoryItemsViewModel: CategoryItemsViewModel
    private var token: String? = ""
    private lateinit var itemName: ArrayList<String>
    private lateinit var itemImg: ArrayList<String>
    private lateinit var itemId : ArrayList<String>
    private lateinit var loadingItem: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_category__items_frag, container, false)

        categoryItemsViewModel = ViewModelProvider(this).get(CategoryItemsViewModel::class.java)

        mainActivity = activity as MainActivity

        mainActivity.toolbar_const.title = Constants.categoryName
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        addItems = view.findViewById(R.id.items_add)
        recyclerView = view.findViewById(R.id.category_items_rv)
        loadingItem = view.findViewById(R.id.loading_items)

        loadingItem.visibility = View.VISIBLE

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        itemImg = ArrayList<String>()
        itemName = ArrayList<String>()
        itemId = ArrayList<String>()

        token =
            mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0)
                .getString(
                    Constants.sharedPrefrencesConstant.DEVICE_TOKEN, ""
                )

        //setUpMenuItemsRv()
        getCategoryItems()

        addItems.setOnClickListener {

            mainActivity.loadFragment(
                fragmentManager,
                AddItems_frag(),
                R.id.fragment_container,
                false,
                "AddItems",
                null
            )
        }

        return view
    }

    private fun getCategoryItems() {

        if (token != null) {

            Log.d("idC", Constants.categoryId)

            categoryItemsViewModel.getCategoryProducts(
                this,
                "x-token",
                Constants.categoryId,
                loadingItem
            ).observe(viewLifecycleOwner) {

                if (it != null){

                    if (it.getSuccess()!!){

                        itemName.clear()
                        itemImg.clear()
                        itemId.clear()
                        recycleView_models.clear()

                        for (i in it.getData()!!.ListproductResponce!!.indices){

                            itemName.add(it.getData()!!.ListproductResponce!![i].productName.toString())
                            itemImg.add(it.getData()!!.ListproductResponce!![i].productPic.toString())
                            itemId.add(it.getData()!!.ListproductResponce!![i].id.toString())

                        }

                        loadingItem.visibility = View.GONE
                        setUpMenuItemsRv()
                    }
                }
            }
        }
    }

    private fun setUpMenuItemsRv() {

       for (i in itemName.indices){

           recycleView_models.add(RecycleModel(itemName[i], itemImg[i], itemId[i]))

           recycleView_adapter_MI = RecycleView_MI(activity, recycleView_models)
           recyclerView.adapter = recycleView_adapter_MI
           recycleView_adapter_MI.notifyItemInserted(i)
       }
    }


}