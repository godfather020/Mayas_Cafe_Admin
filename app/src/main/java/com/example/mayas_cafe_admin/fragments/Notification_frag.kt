package com.example.mayas_cafe_admin.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.Notification_ViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_NP
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_NT
import com.example.mayas_cafe_admin.utils.Constants
import java.text.SimpleDateFormat
import java.util.*


class Notification_frag : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    var recycleView_models1 = ArrayList<RecycleModel>()
    lateinit var loading : ProgressBar
    lateinit var  recyclerView: RecyclerView
    lateinit var recyclerView2: RecyclerView
    lateinit var today_txt: TextView
    lateinit var yesterday_txt : TextView
    lateinit var noNotify : TextView
    var notificationTodayTxt = ArrayList<String>()
    var notificationTodayTime = ArrayList<String>()
    var notificationTodayTitle = ArrayList<String>()
    var notificationTodayDate = ArrayList<String>()
    var notificationPreviousTxt = ArrayList<String>()
    var notificationPreviousTime = ArrayList<String>()
    var notificationPreviousTitle = ArrayList<String>()
    var notificationPreviousDate = ArrayList<String>()
    var notificationToday_id = ArrayList<String>()
    var notificationPrevious_id = ArrayList<String>()
    lateinit var recycleView_adapter_N : RecycleView_NT
    lateinit var recycleView_adapter_N2 : RecycleView_NP
    lateinit var notificationViewModel: Notification_ViewModel
    var token : String? = ""
    lateinit var mainActivity : MainActivity
    var noNoty = 1
    var today = 0
    var previous = 0

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

        notificationViewModel = ViewModelProvider(this).get(Notification_ViewModel::class.java)

        mainActivity = activity as MainActivity

        loading = view.findViewById(R.id.loading_notify)
        //loading.visibility = View.VISIBLE
        noNotify = view.findViewById(R.id.noNoty_txt)

        today_txt = view.findViewById(R.id.today_txt)
        yesterday_txt = view.findViewById(R.id.yesterday_txt)

        recyclerView= view.findViewById(R.id.today_rv)
        recyclerView2 = view.findViewById(R.id.yesterday_rv)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val layoutManager2 = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView2.layoutManager = layoutManager2

        token =
            mainActivity.getSharedPreferences(
                Constants.sharedPrefrencesConstant.DEVICE_TOKEN,
                0
            )
                .getString(
                    Constants.sharedPrefrencesConstant.DEVICE_TOKEN, ""
                )



        //setUpNotifyView()

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
                val notifyId = notificationPrevious_id.get(position)
                Log.d("notifyID", notifyId)
                removeNotification(notifyId)

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
                val notifyId = notificationToday_id.get(position)
                Log.d("notifyID", notifyId)
                removeNotification(notifyId)
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView2)

        val itemTouchHelper1 = ItemTouchHelper(simpleItemTouchCallback1)
        itemTouchHelper1.attachToRecyclerView(recyclerView)

        setHasOptionsMenu(true)

        getAllNotifications()

        return view
    }

    private fun removeNotification(notifyId: String) {

        notificationViewModel.removeNotification(this, notifyId).observe(viewLifecycleOwner) {

            if (it != null){

                if (it.success!!){

                    recycleView_adapter_N.notifyDataSetChanged()
                    recycleView_adapter_N2.notifyDataSetChanged()
                    //setUpNotifyView()
                    //setUpNotifyRv()

                }
            }
        }
    }

    private fun getAllNotifications() {

        if (token != null){

            notificationViewModel.getNotificationData(this, loading, token.toString()).observe(viewLifecycleOwner){

                if (it != null){

                    if (it.getSuccess()!!){

                        notificationTodayTxt.clear()
                        notificationPreviousTime.clear()
                        notificationPreviousTxt.clear()
                        notificationPreviousTitle.clear()
                        notificationTodayTime.clear()
                        notificationTodayTime.clear()
                        recycleView_models.clear()
                        recycleView_models1.clear()
                        notificationPrevious_id.clear()
                        notificationToday_id.clear()

                        if (it.getData()!!.notifications!!.rows!!.isEmpty()){

                            noNotify.visibility = View.VISIBLE
                            //noNoty_img.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                            recyclerView2.visibility = View.GONE
                            noNoty = 1
                        }
                        else{

                            noNotify.visibility = View.GONE
                            //noNoty_img.visibility = View.GONE
                            recyclerView.visibility = View.VISIBLE
                            recyclerView2.visibility = View.VISIBLE
                            noNoty = 0
                        }

                        //val dateTime = it.getData()!!.notifications!!.rows!![0].createdAt.toString()

                        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                            Date()
                        )

                        Log.d("timeDateCurrent", todayDate.toString())

                        //val date = "2022-05-12"

                        val sdf : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())

                        val notifyCount = it.getData()!!.notifications!!.count!!
                        Constants.notificationCount = notifyCount
                        /*val currentTime =
                            SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

                        Log.d("timeC", currentTime)*/

                        for (i in it.getData()!!.notifications!!.rows!!.indices){

                            val createdDateTime = it.getData()!!.notifications!!.rows!![i].createdAt!!

                            Log.d("dateTime", createdDateTime)

                            val date1: Date = sdf.parse(createdDateTime) as Date

                            val createdDate = SimpleDateFormat("yyy-MM-dd", Locale.getDefault()).format(date1)

                            Log.d("DateC", createdDate)

                            val createdDateAd = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(date1)

                            val notyTime =
                                SimpleDateFormat("HH:mm a", Locale.getDefault()).format(date1)

                            Log.d("timeC", notyTime)

                            var newAMPM = notyTime.substring(5,notyTime.length)
                            val newNotyTime = notyTime.substring(0,5)

                            if (newAMPM.contains("am")){

                                newAMPM = newAMPM.replace("am", "AM")
                            }
                            else{

                                newAMPM = newAMPM.replace("pm", "PM")
                            }

                            if (createdDate == todayDate){

                                Log.d("inside", "inside")

                                today = 1

                                notificationTodayTime.add( newNotyTime+"\n"+newAMPM)
                                notificationTodayTxt.add( it.getData()!!.notifications!!.rows!![i].description.toString())
                                notificationTodayTitle.add(it.getData()!!.notifications!!.rows!![i].title.toString())
                                notificationTodayDate.add(createdDateAd)
                                notificationToday_id.add(it.getData()!!.notifications!!.rows!![i].id.toString())

                            }
                            else{

                                previous = 1
                                Log.d("inside1", "inside1")
                                notificationPreviousTime.add( newNotyTime+"\n"+newAMPM)
                                notificationPreviousTxt.add( it.getData()!!.notifications!!.rows!![i].description.toString())
                                notificationPreviousTitle.add(it.getData()!!.notifications!!.rows!![i].title.toString())
                                Log.d("date323", createdDateAd)
                                notificationPreviousDate.add(createdDateAd)
                                notificationPrevious_id.add(it.getData()!!.notifications!!.rows!![i].id.toString())
                            }
                        }

                        if (today == 0){

                            today_txt.visibility = View.GONE
                            recyclerView.visibility = View.GONE
                        }
                        if (previous == 0){

                            yesterday_txt.visibility = View.GONE
                            recyclerView2.visibility = View.GONE
                        }

                        loading.visibility = View.GONE
                        setUpNotifyView()
                    }
                }
            }
        }
    }

    private fun setUpNotifyView() {

        for (i in notificationTodayTime.indices){

            recycleView_models.add(RecycleModel(notificationToday_id[i], notificationTodayTitle[i], notificationTodayTxt[i],notificationTodayDate[i], notificationTodayTime[i]))
        }

        for (i in notificationPreviousTime.indices){

            Log.d("notyPre", notificationPrevious_id[i] + " "+ notificationPreviousTxt[i] + " " + notificationPreviousDate[i]+ " " + notificationPreviousTitle[i] + " " + notificationPreviousTime[i])

            recycleView_models1.add(RecycleModel(notificationPrevious_id[i], notificationPreviousTitle[i], notificationPreviousTxt[i], notificationPreviousDate[i], notificationPreviousTime[i]))
        }

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