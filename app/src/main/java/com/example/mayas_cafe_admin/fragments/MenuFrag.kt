package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.compose.ui.text.createTextLayoutResult
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.MenuViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_M
import com.example.mayas_cafe_admin.utils.Constants
import kotlin.collections.ArrayList


class MenuFrag : Fragment() {

    private var recycleView_models = ArrayList<RecycleModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recycleView_adapter_M: RecycleView_M
    private lateinit var mainActivity: MainActivity
    private lateinit var menuViewModel: MenuViewModel
    private lateinit var categoryName: ArrayList<String>
    private lateinit var categoryImg: ArrayList<String>
    private lateinit var loadingCategory: ProgressBar
    private var token: String? = ""
    private lateinit var categoryId : ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_menu_frag, container, false)

        menuViewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        mainActivity = activity as MainActivity

        mainActivity.toolbar_const.title = "Menu"
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        recyclerView = view.findViewById(R.id.menu_rv)
        loadingCategory = view.findViewById(R.id.loading_category)

        loadingCategory.visibility = View.VISIBLE

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        token =
            mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0)
                .getString(
                    Constants.sharedPrefrencesConstant.DEVICE_TOKEN, ""
                )

        categoryName = ArrayList<String>()
        categoryImg = ArrayList<String>()
        categoryId = ArrayList<String>()

        //setUpMenuRv()

        getCategories()

        return view
    }

    private fun getCategories() {

        if (token != null) {

            menuViewModel.getTotalCategories(this, token.toString(), loadingCategory)
                .observe(viewLifecycleOwner) {

                    if (it != null) {

                        if (it.getSuccess()!!) {

                            categoryImg.clear()
                            categoryName.clear()
                            recycleView_models.clear()

                            for (i in it.getData()!!.ListcategoryResponce!!.indices) {

                                categoryName.add(it.getData()!!.ListcategoryResponce!![i].categoryName.toString())
                                Constants.totalCategories.add(it.getData()!!.ListcategoryResponce!![i].categoryName.toString())
                                categoryImg.add("default.png")
                                categoryId.add(it.getData()!!.ListcategoryResponce!![i].id.toString())
                            }

                            loadingCategory.visibility = View.GONE
                            setUpMenuRv()
                        }
                    }
                }
        }
    }

    private fun setUpMenuRv() {

        for (i in categoryName.indices) {

            recycleView_models.add(RecycleModel(categoryName[i], categoryImg[i], categoryId[i]))

            recycleView_adapter_M = RecycleView_M(activity, recycleView_models)
            recyclerView.adapter = recycleView_adapter_M
            recycleView_adapter_M.notifyItemInserted(i)
        }
    }

}