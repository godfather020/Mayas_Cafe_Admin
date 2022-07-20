package com.example.mayas_cafe_admin.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.AddNewOffer_ViewModel
import com.example.mayas_cafe_admin.utils.Constants
import com.squareup.picasso.Picasso
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class AddNewOffer : Fragment() {

    lateinit var mainActivity: MainActivity
    var myCalendar: Calendar = Calendar.getInstance()
    private var photoFile: File? = null
    private var uploadImgPath: String? = null
    lateinit var startDate: EditText
    lateinit var endDate: EditText
    lateinit var offerName: EditText
    lateinit var offerTitle: EditText
    lateinit var offerDes: EditText
    lateinit var offerCode: EditText
    lateinit var offerUpto: EditText
    lateinit var offerMin: EditText
    lateinit var radioCurrency: RadioButton
    lateinit var radioPercent: RadioButton
    lateinit var radioActive: RadioButton
    lateinit var radioInactive: RadioButton
    lateinit var offerImg: ImageView
    lateinit var addOffer: Button
    lateinit var addNewOfferViewModel: AddNewOffer_ViewModel
    lateinit var loadingOffers: ProgressBar
    var token: String? = ""
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
        val view: View = inflater.inflate(R.layout.fragment_add_new_offer, container, false)

        addNewOfferViewModel = ViewModelProvider(this).get(AddNewOffer_ViewModel::class.java)

        mainActivity = activity as MainActivity

        mainActivity.toolbar_const.title = "Add Offer"
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        startDate = view.findViewById(R.id.offer_startDate)
        endDate = view.findViewById(R.id.offer_endDate)
        offerUpto = view.findViewById(R.id.upto_discount)
        offerCode = view.findViewById(R.id.offer_code)
        offerDes = view.findViewById(R.id.offer_des)
        offerTitle = view.findViewById(R.id.offer_title)
        offerName = view.findViewById(R.id.offer_name)
        offerImg = view.findViewById(R.id.offer_img)
        offerMin = view.findViewById(R.id.offer_minAmt)
        radioActive = view.findViewById(R.id.radio_active)
        radioCurrency = view.findViewById(R.id.radio_currency)
        radioPercent = view.findViewById(R.id.radio_percent)
        radioInactive = view.findViewById(R.id.radio_inactive)
        addOffer = view.findViewById(R.id.addOffer_btn)
        loadingOffers = view.findViewById(R.id.loading_offers)

        var month = todayDate.substring(0, 2)
        var day = todayDate.substring(3, 5)
        var year = todayDate.substring(6, todayDate.length)
        var editText: EditText
        editText = view.findViewById(R.id.offer_startDate)

        val date =
            OnDateSetListener { view1, year1, month1, day1 ->
                myCalendar.set(Calendar.YEAR, year1)
                myCalendar.set(Calendar.MONTH, month1)
                myCalendar.set(Calendar.DAY_OF_MONTH, day1)
                updateLabel(day.toInt(), month.toInt(), year.toInt(), editText)
            }

        startDate.setOnClickListener {

            day = todayDate.substring(3, 5)
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

            if (startDate.text.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    "Please mention Start Date first",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                val startDate = startDate.text

                day = startDate.substring(3, 5)
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

        offerImg.setOnClickListener {

            selectImg()
        }

        addOffer.setOnClickListener {

            val isValidate = validateFields()

            if (isValidate) {

                if (uploadImgPath != null) {

                    token =
                        mainActivity.getSharedPreferences(
                            Constants.sharedPrefrencesConstant.DEVICE_TOKEN,
                            0
                        )
                            .getString(
                                Constants.sharedPrefrencesConstant.DEVICE_TOKEN, ""
                            )

                    createOffer()
                } else {

                    Toast.makeText(context, "Please select a image", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

    private fun createOffer() {

        if (token != null) {

            var calcType = ""
            var isActive = false

            if (radioPercent.isChecked){

                calcType = "%"
            }
            else{

                calcType = "currency"
            }

            isActive = radioActive.isChecked

            val file : File = File(uploadImgPath!!)

            addNewOfferViewModel.createCoupon(
                this,
                token.toString(),
                loadingOffers,
                offerName.text.toString(),
                offerTitle.text.toString(),
                offerDes.text.toString(),
                offerCode.text.toString(),
                startDate.text.toString(),
                endDate.text.toString(),
                calcType,
                offerUpto.text.toString(),
                offerMin.text.toString(),
                file,
                isActive
            ).observe(viewLifecycleOwner){ it ->

                if (it != null){

                    if (it.getSuccess()!!){

                        addNewOfferViewModel.updateCouponStatus(it.getData()!!.id.toString()).observe(viewLifecycleOwner){ i ->


                            if (i != null){

                                if (i.getSuccess()!!){

                                    Toast.makeText(context, "Coupon Created", Toast.LENGTH_SHORT).show()

                                    mainActivity.loadFragment(fragmentManager, Offers_frag(), R.id.fragment_container, true, "Add Coupon", null)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun selectImg() {

        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Add Photo!")
        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
            if (options[item] == "Take Photo") {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                photoFile = getPhotoFileUri("temp.jpg")

                val fileProvider = FileProvider.getUriForFile(
                    requireContext(),
                    "com.codepath.fileprovider",
                    photoFile!!
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

                if (intent.resolveActivity(requireActivity().packageManager) != null) {

                    startActivityForResult(intent, 1)
                }
            } else if (options[item] == "Choose from Gallery") {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 2)
            } else if (options[item] == "Cancel") {
                //uploadTxt.visibility = View.VISIBLE
                dialog.dismiss()
            }
        })
        builder.show()
    }

    private fun getPhotoFileUri(fileName: String): File? {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir =
            File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "CustomImg")

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("CustomImg", "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator.toString() + fileName)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            Log.d("requestCode", requestCode.toString())
            if (requestCode == 1) {

                Log.d("requestCode", requestCode.toString())

                val bitmap: Bitmap? = rotateImageIfRequired(photoFile!!.absolutePath)
                Log.d("camerPath", photoFile!!.absolutePath)

                loadBitmapByPicasso(requireContext(), bitmap!!, offerImg)
                offerImg.setImageBitmap(bitmap)
                //sendProfileImg(photoFile!!.absolutePath)
                uploadImgPath = photoFile!!.absolutePath

            } else if (requestCode == 2) {
                val selectedImage = data!!.data
                val filePath = arrayOf(MediaStore.Images.Media.DATA)
                val c: Cursor =
                    selectedImage?.let {
                        requireActivity().contentResolver.query(
                            it,
                            filePath,
                            null,
                            null,
                            null
                        )
                    }!!
                c.moveToFirst()
                val columnIndex: Int = c.getColumnIndex(filePath[0])
                val picturePath: String = c.getString(columnIndex)
                c.close()
                val thumbnail = BitmapFactory.decodeFile(picturePath)
                Log.w(
                    "path of image from gallery......******************.........",
                    picturePath + ""
                )
                //sendProfileImg(picturePath)
                uploadImgPath = picturePath
                offerImg.setImageBitmap(thumbnail)
            }
        }
    }

    private fun rotateImageIfRequired(imagePath: String): Bitmap? {
        var degrees = 0
        try {
            val exif = ExifInterface(imagePath)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degrees = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degrees = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degrees = 270
            }
        } catch (e: IOException) {
            Log.e("ImageError", "Error in reading Exif data of $imagePath", e)
        }
        val decodeBounds = BitmapFactory.Options()
        decodeBounds.inJustDecodeBounds = true
        var bitmap = BitmapFactory.decodeFile(imagePath, decodeBounds)
        val numPixels = decodeBounds.outWidth * decodeBounds.outHeight
        val maxPixels = 2048 * 1536 // requires 12 MB heap
        val options = BitmapFactory.Options()
        options.inSampleSize = if (numPixels > maxPixels) 2 else 1
        bitmap = BitmapFactory.decodeFile(imagePath, options)
        if (bitmap == null) {
            return null
        }
        val matrix = Matrix()
        matrix.setRotate(degrees.toFloat())
        bitmap = Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width,
            bitmap.height, matrix, true
        )
        return bitmap
    }

    private fun loadBitmapByPicasso(pContext: Context, pBitmap: Bitmap, pImageView: ImageView) {
        try {
            val uri = Uri.fromFile(File.createTempFile("temp_file_name", ".jpg", pContext.cacheDir))
            val outputStream: OutputStream? = pContext.contentResolver.openOutputStream(uri)
            pBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream!!.close()
            Picasso.get().load(uri).into(pImageView)
        } catch (e: java.lang.Exception) {
            Log.e("LoadBitmapByPicasso", e.message!!)
        }
    }

    private fun validateFields(): Boolean {

        if (offerName.text.isEmpty()) {

            offerName.requestFocus()
            return false
        } else if (offerDes.text.isEmpty()) {

            offerDes.requestFocus()
            return false
        } else if (offerTitle.text.isEmpty()) {

            offerTitle.requestFocus()
            return false
        } else if (offerCode.text.isEmpty()) {

            offerCode.requestFocus()
            return false
        } else if (offerMin.text.isEmpty()) {

            offerMin.requestFocus()
            return false
        } else if (offerUpto.text.isEmpty()) {

            offerUpto.requestFocus()
            return false
        } else if (startDate.text.isEmpty()) {

            startDate.requestFocus()
            return false
        } else if (endDate.text.isEmpty()) {

            endDate.requestFocus()
            return false
        } else {

            return true
        }
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

        if (myCalendar.get(Calendar.DAY_OF_MONTH) < day && myCalendar.get(Calendar.MONTH) <= month - 1 && myCalendar.get(
                Calendar.YEAR
            ) <= year
        ) {

            Toast.makeText(requireContext(), "Please select a future Date", Toast.LENGTH_SHORT)
                .show()
        } else {

            if (myCalendar.get(Calendar.DAY_OF_MONTH) == day && myCalendar.get(Calendar.MONTH) < month - 1) {

                Toast.makeText(requireContext(), "Please select a future Date", Toast.LENGTH_SHORT)
                    .show()
            } else {

                editText.setText(dateFormat.format(myCalendar.time))
            }
        }
    }
}