package com.example.mayas_cafe_admin.fragments

import android.app.Activity
import android.app.AlertDialog
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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.compose.ui.unit.Constraints
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.AddItemViewModel
import com.example.mayas_cafe_admin.utils.Constants
import com.squareup.picasso.Picasso
import java.io.File
import java.io.IOException
import java.io.OutputStream


class AddItemsFrag : Fragment() {

    lateinit var mainActivity: MainActivity
    private var photoFile: File? = null
    private lateinit var addItemImg: ImageView
    private lateinit var itemName: EditText
    private lateinit var itemDes: EditText
    private lateinit var itemCategory: AutoCompleteTextView
    private lateinit var itemSizeS: CheckBox
    private lateinit var itemSizeM: CheckBox
    private lateinit var itemSizeL: CheckBox
    private lateinit var itemSizeSPrice: EditText
    private lateinit var itemSizeSOffer: EditText
    private lateinit var itemSizeMPrice: EditText
    private lateinit var itemSizeMOffer: EditText
    private lateinit var itemSizeLPrice: EditText
    private lateinit var itemSizeLOffer: EditText
    private lateinit var itemFeaturedNo: RadioButton
    private lateinit var itemFeaturedYes: RadioButton
    private lateinit var addItem: Button
    private var uploadImgPath: String? = null
    private lateinit var addItemViewModel: AddItemViewModel
    private var token: String? = ""
    lateinit var loadingAddItem: ProgressBar

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
        val view: View = inflater.inflate(R.layout.fragment_add_items_frag, container, false)

        addItemViewModel = ViewModelProvider(this).get(AddItemViewModel::class.java)

        mainActivity = activity as MainActivity

        addItemImg = view.findViewById(R.id.addItem_img)
        itemName = view.findViewById(R.id.addItem_name)
        itemDes = view.findViewById(R.id.addItem_des)
        itemCategory = view.findViewById(R.id.addItem_category)
        itemSizeS = view.findViewById(R.id.addItemS_check)
        itemSizeM = view.findViewById(R.id.addItemM_check)
        itemSizeL = view.findViewById(R.id.addItemL_check)
        itemSizeSPrice = view.findViewById(R.id.addItemS_price)
        itemSizeMPrice = view.findViewById(R.id.addItemM_price)
        itemSizeLPrice = view.findViewById(R.id.addItemL_price)
        itemSizeSOffer = view.findViewById(R.id.addItemS_offer)
        itemSizeMOffer = view.findViewById(R.id.addItemM_offer)
        itemSizeLOffer = view.findViewById(R.id.addItemL_offer)
        itemFeaturedNo = view.findViewById(R.id.no)
        itemFeaturedYes = view.findViewById(R.id.yes)
        addItem = view.findViewById(R.id.addItem_btn)
        loadingAddItem = view.findViewById(R.id.loading_addItem)

        mainActivity.toolbar_const.title = "Add Item"
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        // get reference to the string array that we just created
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.drop_down_item, Constants.totalCategories)
        // get reference to the autocomplete text view
        // set adapter to the autocomplete tv to the arrayAdapter
        itemCategory.setAdapter(arrayAdapter)

        addItemImg.setOnClickListener {

            selectImage()
        }

        itemSizeS.setOnClickListener {

            if (itemSizeS.isChecked) {

                itemSizeSPrice.isEnabled = true
                itemSizeSOffer.isEnabled = true
            } else {

                itemSizeSPrice.isEnabled = false
                itemSizeSOffer.isEnabled = false
            }
        }

        itemSizeM.setOnClickListener {

            if (itemSizeM.isChecked) {

                itemSizeMPrice.isEnabled = true
                itemSizeMOffer.isEnabled = true
            } else {

                itemSizeMPrice.isEnabled = false
                itemSizeMOffer.isEnabled = false
            }
        }

        itemSizeL.setOnClickListener {

            if (itemSizeL.isChecked) {

                itemSizeLPrice.isEnabled = true
                itemSizeLOffer.isEnabled = true
            } else {

                itemSizeLPrice.isEnabled = false
                itemSizeLOffer.isEnabled = false
            }
        }

        addItem.setOnClickListener {

            if (uploadImgPath != null) {

                val isValidate = validateFields()

                if (isValidate) {

                    token =
                        mainActivity.getSharedPreferences(
                            Constants.sharedPrefrencesConstant.DEVICE_TOKEN,
                            0
                        )
                            .getString(
                                Constants.sharedPrefrencesConstant.DEVICE_TOKEN, ""
                            )

                    createProduct()


                }
            } else {

                Toast.makeText(context, "Select a pic for item", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun createProduct() {

        val productName = itemName.text.toString()
        val productDes = itemDes.text.toString()
        val productCategory = Constants.categoryId
        val productSize = ArrayList<String>()
        val productPrice = ArrayList<String>()
        val productOffer = ArrayList<String>()
        val productAvailability = ArrayList<String>()

        if (itemSizeS.isChecked) {

            productSize.add("S")
            productAvailability.add("1")
            productPrice.add(itemSizeSPrice.text.toString())

            if (itemSizeSOffer.text.toString().isNotEmpty()) {

                productOffer.add(itemSizeSOffer.text.toString())
            } else {

                productOffer.add("0")
            }
        }
        if (itemSizeM.isChecked) {

            productSize.add("M")
            productAvailability.add("1")
            productPrice.add(itemSizeMPrice.text.toString())

            if (itemSizeMOffer.text.toString().isNotEmpty()) {

                productOffer.add(itemSizeMOffer.text.toString())
            } else {

                productOffer.add("0")
            }
        }
        if (itemSizeL.isChecked) {

            productSize.add("L")
            productAvailability.add("1")
            productPrice.add(itemSizeLPrice.text.toString())

            if (itemSizeLOffer.text.toString().isNotEmpty()) {

                productOffer.add(itemSizeLOffer.text.toString())
            } else {

                productOffer.add("0")
            }
        }

        addItemViewModel.createProduct(
            this,
            token.toString(),
            loadingAddItem,
            productName,
            productDes,
            productCategory,
            productSize,
            productPrice,
            productOffer
        ).observe(viewLifecycleOwner){

            if (it != null){

                if (it.getSuccess()!!){

                    Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show()

                    mainActivity.loadFragment(fragmentManager, MenuFrag(), R.id.fragment_container, true, "Add Item", null)
                }
            }
        }


    }

    private fun getCategory(cat: String): String {

        var id = ""

        for (i in Constants.totalCategories.indices) {

            if (cat == Constants.totalCategories[i]) {

                id = (i + 1).toString()
            }
        }

        return id
    }

    private fun validateFields(): Boolean {

        if (itemName.text.isEmpty()) {

            itemName.requestFocus()
            return false
        } else if (itemDes.text.isEmpty()) {

            itemDes.requestFocus()
            return false
        } else if (itemCategory.text.isEmpty()) {

            itemCategory.requestFocus()
            return false
        } else if (!itemSizeL.isChecked && !itemSizeM.isChecked && !itemSizeS.isChecked) {

            Toast.makeText(context, "Please choose a size", Toast.LENGTH_SHORT).show()
            return false
        } else if (itemSizeS.isChecked && itemSizeSPrice.text.isEmpty()) {

            itemSizeSPrice.requestFocus()
            return false
        } else if (itemSizeM.isChecked && itemSizeMPrice.text.isEmpty()) {

            itemSizeMPrice.requestFocus()
            return false
        } else if (itemSizeL.isChecked && itemSizeLPrice.text.isEmpty()) {

            itemSizeLPrice.requestFocus()
            return false
        } else if (itemSizeSPrice.text.isEmpty() && itemSizeMPrice.text.isEmpty() && itemSizeLPrice.text.isEmpty()) {

            Toast.makeText(context, "Please mention a price", Toast.LENGTH_SHORT).show()
            return false
        } else {

            return true
        }
    }

    private fun selectImage() {
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

                loadBitmapByPicasso(requireContext(), bitmap!!, addItemImg)
                addItemImg.setImageBitmap(bitmap)
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
                addItemImg.setImageBitmap(thumbnail)
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
}