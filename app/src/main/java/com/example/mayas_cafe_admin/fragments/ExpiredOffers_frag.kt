package com.example.mayas_cafe_admin.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.ActiveOffers_ViewModel
import com.example.mayas_cafe_admin.recycleModels.recycleModel.RecycleModel
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_AO
import com.example.mayas_cafe_admin.recycleModels.recycleViewModels.RecycleView_O
import com.example.mayas_cafe_admin.utils.Constants
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class ExpiredOffers_frag : Fragment() {

    var recycleView_models = ArrayList<RecycleModel>()
    lateinit var recyclerView: RecyclerView
    lateinit var recycleView_adapter_O: RecycleView_O
    lateinit var search: MenuItem
    lateinit var mainActivity: MainActivity
    lateinit var activeOffersViewModel: ActiveOffers_ViewModel
    lateinit var loadingExpired: ProgressBar
    var token: String? = ""
    lateinit var offersId: ArrayList<String>
    lateinit var offersName: ArrayList<String>
    lateinit var offersTitle: ArrayList<String>
    lateinit var offersDes: ArrayList<String>
    lateinit var offersCode: ArrayList<String>
    lateinit var offersCalcType: ArrayList<String>
    lateinit var offersUpTo: ArrayList<String>
    lateinit var offersMin: ArrayList<String>
    lateinit var offersStartAt: ArrayList<String>
    lateinit var offersStopAt: ArrayList<String>
    lateinit var offersImg: ArrayList<String>

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
        val view: View = inflater.inflate(R.layout.fragment_expired_offers_frag, container, false)

        activeOffersViewModel = ViewModelProvider(this).get(ActiveOffers_ViewModel::class.java)

        mainActivity = activity as MainActivity

        recyclerView = view.findViewById(R.id.expired_offers_rv)
        loadingExpired = view.findViewById(R.id.loading_expired)

        loadingExpired.visibility = View.VISIBLE

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        offersId = ArrayList<String>()
        offersName = ArrayList<String>()
        offersCalcType = ArrayList<String>()
        offersCode = ArrayList<String>()
        offersDes = ArrayList<String>()
        offersTitle = ArrayList<String>()
        offersMin = ArrayList<String>()
        offersUpTo = ArrayList<String>()
        offersStartAt = ArrayList<String>()
        offersStopAt = ArrayList<String>()
        offersImg = ArrayList<String>()

        setHasOptionsMenu(true)

        token =
            mainActivity.getSharedPreferences(
                Constants.sharedPrefrencesConstant.DEVICE_TOKEN,
                0
            )
                .getString(
                    Constants.sharedPrefrencesConstant.DEVICE_TOKEN, ""
                )

        //setUpExpiredOffersRv()

        getExpiredOffers()

        return view
    }

    private fun getExpiredOffers() {

        if (token != null) {

            activeOffersViewModel.getActiveOffers(this, token.toString(), loadingExpired)
                .observe(viewLifecycleOwner) {

                    if (it != null) {

                        if (it.getSuccess()!!) {

                            clearArrayLists()

                            for (i in it.getData()!!.ListcouponResponce!!.indices) {

                                val dateFormat: DateFormat =
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        SimpleDateFormat(
                                            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                                            Locale.getDefault()
                                        )
                                    } else {
                                        TODO("VERSION.SDK_INT < N")
                                    }
                                val date =
                                    dateFormat.parse(it.getData()!!.ListcouponResponce!![i].stopAt.toString()) //You will get date object relative to server/client timezone wherever it is parsed

                                val date1 =
                                    dateFormat.parse(it.getData()!!.ListcouponResponce!![i].startAt.toString())

                                val formatter: DateFormat =
                                    SimpleDateFormat("dd-MM-yyyy") //If you need time just put specific format for time like 'HH:mm:ss'

                                val dateStr = formatter.format(date)

                                val startDate = formatter.format(date1)

                                val valid_until = dateStr
                                val sdf = SimpleDateFormat("dd-MM-yyyy")
                                var strDate: Date? = null
                                try {
                                    strDate = sdf.parse(valid_until)
                                } catch (e: ParseException) {
                                    e.printStackTrace()
                                }

                                if (Date().after(strDate)) {

                                    Log.d("time", "stopgreater")

                                    offersId.add(it.getData()!!.ListcouponResponce!![i].id.toString())
                                    offersName.add(it.getData()!!.ListcouponResponce!![i].name.toString())
                                    offersDes.add(it.getData()!!.ListcouponResponce!![i].desc.toString())
                                    offersCode.add(it.getData()!!.ListcouponResponce!![i].code.toString())
                                    offersTitle.add(it.getData()!!.ListcouponResponce!![i].title.toString())
                                    offersCalcType.add(it.getData()!!.ListcouponResponce!![i].calculateType.toString())
                                    offersImg.add(it.getData()!!.ListcouponResponce!![i].bannerImage.toString())
                                    offersMin.add(it.getData()!!.ListcouponResponce!![i].minimumAmount.toString())
                                    offersUpTo.add(it.getData()!!.ListcouponResponce!![i].uptoDiscount.toString())
                                    offersStartAt.add(startDate)
                                    offersStopAt.add(dateStr)

                                } else {

                                    Log.d("time121", "todaygreater")
                                }
                            }

                            loadingExpired.visibility = View.GONE
                            setUpExpiredOffersRv()
                        }
                    }
                }
        }
    }

    private fun clearArrayLists() {

        recycleView_models.clear()
        offersCode.clear()
        offersId.clear()
        offersName.clear()
        offersMin.clear()
        offersTitle.clear()
        offersCalcType.clear()
        offersDes.clear()
        offersImg.clear()
        offersStartAt.clear()
        offersStopAt.clear()
        offersUpTo.clear()
    }

    private fun setUpExpiredOffersRv() {

        for (i in offersId.indices) {

            recycleView_models.add(
                RecycleModel(
                    offersId[i],
                    offersName[i],
                    offersTitle[i],
                    offersCode[i],
                    offersDes[i],
                    offersCalcType[i],
                    offersUpTo[i],
                    offersMin[i],
                    offersStartAt[i],
                    offersStopAt[i],
                    offersImg[i]
                )
            )

            recycleView_adapter_O = RecycleView_O(activity, recycleView_models)
            recyclerView.adapter = recycleView_adapter_O
            recycleView_adapter_O.notifyItemInserted(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        search = menu.findItem(R.id.search)
        val searchView: androidx.appcompat.widget.SearchView =
            search.actionView as androidx.appcompat.widget.SearchView

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