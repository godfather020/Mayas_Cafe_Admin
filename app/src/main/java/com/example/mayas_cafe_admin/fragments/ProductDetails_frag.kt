package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.unit.Constraints
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_PD
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_RTP
import com.example.mayas_cafe_admin.utils.Constants
import java.util.ArrayList


class ProductDetails_frag : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    lateinit var  recyclerView: RecyclerView
    lateinit var recycleView_adapter_PD : RecycleView_PD
    lateinit var orderId : TextView
    lateinit var orderStatus : TextView
    lateinit var orderPickUp : TextView
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
        val view : View = inflater.inflate(R.layout.fragment_product_details_frag, container, false)

        mainActivity = activity as MainActivity

        mainActivity.toolbar_const.setTitle("Product Details")
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        recyclerView= view.findViewById(R.id.productDetails_rv)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        orderId = view.findViewById(R.id.detailsOrderId)
        orderStatus = view.findViewById(R.id.detailsOrderStatus)
        orderPickUp = view.findViewById(R.id.detailsPickUp)

        orderId.text = Constants.orderId
        orderStatus.text = Constants.orderStatus
        orderPickUp.text = Constants.orderPickUp

        setUpDetailsRv()

        return view
    }

    private fun setUpDetailsRv() {

        recycleView_models.clear()

        recycleView_models.add(RecycleModel("$5", "adads", "Samosa", "S", "2"))
        recycleView_models.add(RecycleModel("$8", "adads", "Khamand", "M", "3"))
        recycleView_models.add(RecycleModel("$10", "adads", "Jalebi", "L", "4"))
        /*recycleView_models.add(RecycleModel("$5", "adads", "Samosa", "S", "2"))
        recycleView_models.add(RecycleModel("$8", "adads", "Khamand", "M", "3"))
        recycleView_models.add(RecycleModel("$10", "adads", "Jalebi", "L", "4"))
        recycleView_models.add(RecycleModel("$5", "adads", "Samosa", "S", "2"))
        recycleView_models.add(RecycleModel("$8", "adads", "Khamand", "M", "3"))
        recycleView_models.add(RecycleModel("$10", "adads", "Jalebi", "L", "4"))
*/
        recycleView_adapter_PD = RecycleView_PD(activity, recycleView_models)
        recyclerView.adapter = recycleView_adapter_PD
        recycleView_adapter_PD.notifyDataSetChanged()
    }


}