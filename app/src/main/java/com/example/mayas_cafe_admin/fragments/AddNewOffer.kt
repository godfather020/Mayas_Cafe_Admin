package com.example.mayas_cafe_admin.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import java.text.SimpleDateFormat
import java.util.*


class AddNewOffer : Fragment() {

    lateinit var mainActivity: MainActivity
    var myCalendar : Calendar = Calendar.getInstance()
    lateinit var startDate : EditText
    lateinit var endDate : EditText
    val todayDate = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())

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
        val view : View = inflater.inflate(R.layout.fragment_add_new_offer, container, false)

        mainActivity = activity as MainActivity

        mainActivity.toolbar_const.title = "Add Offer"
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))
        startDate = view.findViewById(R.id.offer_startDate)
        endDate = view.findViewById(R.id.offer_endDate)
        var month = todayDate.substring(0, 2)
        var day = todayDate.substring(3,5)
        var year = todayDate.substring(6, todayDate.length)
        var editText : EditText
        editText = view.findViewById(R.id.offer_startDate)

        val date =
            OnDateSetListener { view1, year1, month1, day1 ->
                myCalendar.set(Calendar.YEAR, year1)
                myCalendar.set(Calendar.MONTH, month1)
                myCalendar.set(Calendar.DAY_OF_MONTH, day1)
                updateLabel(day.toInt(), month.toInt(), year.toInt(), editText)
            }

        startDate.setOnClickListener {

            day = todayDate.substring(3,5)
            month = todayDate.substring(0, 2)
            year = todayDate.substring(6, todayDate.length)
            editText = startDate

            DatePickerDialog(
                requireContext(),
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        endDate.setOnClickListener {

            if (startDate.text.isEmpty()){

                Toast.makeText(requireContext(), "Please mention Start Date first", Toast.LENGTH_SHORT).show()
            }
            else{

                val startDate = startDate.text

                day = startDate.substring(3,5)
                month = startDate.substring(0, 2)
                year = startDate.substring(6, startDate.length)
                editText = endDate

                DatePickerDialog(
                    requireContext(),
                    date,
                    myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
                ).show()
            }
        }

        return view
    }

    private fun updateLabel(day: Int, month: Int, year: Int, editText: EditText) {

        val myFormat = "MM/dd/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)

        Log.d("Dateday1", myCalendar.get(Calendar.DAY_OF_MONTH).toString())
        Log.d("Datemonth1", myCalendar.get(Calendar.MONTH).toString())
        Log.d("Dateyear1", myCalendar.get(Calendar.YEAR).toString())

        Log.d("Dateday", day.toString())
        Log.d("Datemonth", month.toString())
        Log.d("Dateyear", year.toString())

        if (myCalendar.get(Calendar.DAY_OF_MONTH) < day && myCalendar.get(Calendar.MONTH) <= month-1 && myCalendar.get(Calendar.YEAR) <= year){

            Toast.makeText(requireContext(), "Please select a future Date", Toast.LENGTH_SHORT).show()
        }
        else{

            if (myCalendar.get(Calendar.DAY_OF_MONTH) == day && myCalendar.get(Calendar.MONTH) < month-1){

                Toast.makeText(requireContext(), "Please select a future Date", Toast.LENGTH_SHORT).show()
            }
            else {

                editText.setText(dateFormat.format(myCalendar.time))
            }
        }
    }
}