package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_NP
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_NT
import java.util.ArrayList


class Notification_frag : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    var recycleView_models1 = ArrayList<RecycleModel>()
    lateinit var loading : ProgressBar
    lateinit var  recyclerView: RecyclerView
    lateinit var recyclerView2: RecyclerView
    lateinit var today_txt: TextView
    lateinit var yesterday_txt : TextView
    lateinit var recycleView_adapter_N : RecycleView_NT
    lateinit var recycleView_adapter_N2 : RecycleView_NP

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
        val view : View = inflater.inflate(R.layout.fragment_notification_frag, container, false)

        loading = view.findViewById(R.id.loading_notify)
        //loading.visibility = View.VISIBLE

        today_txt = view.findViewById(R.id.today_txt)
        yesterday_txt = view.findViewById(R.id.yesterday_txt)

        recyclerView= view.findViewById(R.id.today_rv)
        recyclerView2 = view.findViewById(R.id.yesterday_rv)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val layoutManager2 = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView2.layoutManager = layoutManager2

        setUpNotifyView()

        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                Toast.makeText(activity, "on Move", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                Toast.makeText(activity, "Notification removed", Toast.LENGTH_SHORT).show()
                //Remove swiped item from list and notify the RecyclerView
                val position = viewHolder.absoluteAdapterPosition
                recycleView_models1.removeAt(position)
                //val recycleView_adapter_N = RecycleView_Adapter_N(activity, recycleView_models)
                val recycleView_adapter_N2 = RecycleView_NP(activity, recycleView_models1)
                recycleView_adapter_N2.notifyItemChanged(position, recycleView_models1.size)
                recycleView_adapter_N2.notifyDataSetChanged()
                //setUpNotifyView()
                //val notifyId = notificationPrevious_id.get(position)
                //Log.d("notifyID", notifyId)
                //removeNotification(notifyId)

            }
        }

        val simpleItemTouchCallback1: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                Toast.makeText(activity, "on Move", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                Toast.makeText(activity, "Notification removed", Toast.LENGTH_SHORT).show()
                //Remove swiped item from list and notify the RecyclerView
                val position = viewHolder.absoluteAdapterPosition
                recycleView_models.removeAt(position)
                //val recycleView_adapter_N = RecycleView_Adapter_N(activity, recycleView_models)
                val recycleView_adapter_N = RecycleView_NT(activity, recycleView_models)
                recycleView_adapter_N.notifyItemChanged(position , recycleView_models.size)
                recycleView_adapter_N.notifyDataSetChanged()

                //setUpNotifyView()
                //val notifyId = notificationToday_id.get(position)
                //Log.d("notifyID", notifyId)
                //removeNotification(notifyId)
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView2)

        val itemTouchHelper1 = ItemTouchHelper(simpleItemTouchCallback1)
        itemTouchHelper1.attachToRecyclerView(recyclerView)

        setHasOptionsMenu(true)

        return view
    }

    private fun setUpNotifyView() {

        recycleView_models.clear()
        recycleView_models1.clear()

        recycleView_models.add(RecycleModel("New Order","You have a new order with id - #89849", "07-06-2022", "07:30\nPM"))
        recycleView_models.add(RecycleModel("New Order","You have a new order with id - #89849", "07-06-2022", "07:30\nPM"))
        recycleView_models.add(RecycleModel("New Order","You have a new order with id - #89849", "07-06-2022", "07:30\nPM"))

        recycleView_models1.add(RecycleModel("New Order","You have a new order with id - #89849", "07-06-2022", "07:30\nPM"))
        recycleView_models1.add(RecycleModel("New Order","You have a new order with id - #89849", "07-06-2022", "07:30\nPM"))
        recycleView_models1.add(RecycleModel("New Order","You have a new order with id - #89849", "07-06-2022", "07:30\nPM"))

        recycleView_adapter_N = RecycleView_NT(activity, recycleView_models)
        recycleView_adapter_N2 = RecycleView_NP(activity, recycleView_models1)
        recyclerView.adapter = recycleView_adapter_N
        recyclerView2.adapter = recycleView_adapter_N2
        recycleView_adapter_N.notifyDataSetChanged()
        recycleView_adapter_N2.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.getItem(0).setVisible(false)
        menu.getItem(2).setVisible(false)
    }
}