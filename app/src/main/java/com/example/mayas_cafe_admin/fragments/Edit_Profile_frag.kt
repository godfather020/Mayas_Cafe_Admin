package com.example.mayas_cafe_admin.fragments

import android.R.attr
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
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
import androidx.activity.result.ActivityResultLauncher
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.Edit_Profile_ViewModel
import com.example.mayas_cafe_admin.utils.Constants
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.io.*


open class Edit_Profile_frag : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var save_edit : Button
    lateinit var user_edit_name : EditText
    lateinit var user_edit_phone : EditText
    lateinit var user_edit_email : EditText
    lateinit var user_edit_address : EditText
    lateinit var upload_img : CardView
    lateinit var user_edit_img : ImageView
    lateinit var arg : String
    var photoFile: File? = null
    lateinit var popup: PopupMenu
    private val CAMERA_REQUEST_CODE = 90
    private val GALLERY_REQUEST_CODE = 99
    lateinit var resultLauncherCamera: ActivityResultLauncher<Intent>
    lateinit var resultLauncherGallery: ActivityResultLauncher<Intent>
    lateinit var upload_txt : TextView
    lateinit var edit_profile_view : Edit_Profile_ViewModel
    lateinit var edit_loading : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_edit__profile_frag, container, false)

        edit_profile_view = ViewModelProvider(this).get(Edit_Profile_ViewModel::class.java)

        mainActivity = (activity as MainActivity)

        mainActivity.toolbar_const.setTitle("Edit Profile")
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        save_edit = view.findViewById(R.id.save_edit)
        user_edit_name = view.findViewById(R.id.user_edit_name)
        user_edit_phone = view.findViewById(R.id.user_phone_edit)
        user_edit_email = view.findViewById(R.id.user_edit_email)
        user_edit_address = view.findViewById(R.id.user_edit_add)
        user_edit_img = view.findViewById(R.id.user_edit_img)
        upload_img = view.findViewById(R.id.upload_img)
        upload_txt = view.findViewById(R.id.upload_txt)
        edit_loading = view.findViewById(R.id.edit_profile_loading)

        edit_loading.visibility = View.VISIBLE

        if (arguments != null) {

            arg = arguments?.getString("userName").toString()
            user_edit_name.setText(arg)
            Log.d("userName", arg)

            arg = arguments?.getString("userPhone").toString()
            user_edit_phone.setText(arg)
            Log.d("userPhone", arg)

            arg = arguments?.getString("userEmail").toString()
            user_edit_email.setText(arg)
            Log.d("userEmail", arg)

            arg = arguments?.getString("userAddress").toString()
            user_edit_address.setText(arg)
            Log.d("userAddress", arg)
        }

        mainActivity.toolbar_const.title = ""

        upload_img.setOnClickListener {

            upload_txt.visibility = View.GONE
            selectImage()
        }

        save_edit.setOnClickListener {

            if (validateFields()) {

                /*val bundle = Bundle()
                bundle.putString("userName", user_edit_name.text.toString())
                bundle.putString("userPhone", user_edit_phone.text.toString())
                bundle.putString("userEmail", user_edit_email.text.toString())
                bundle.putString("userAddress", user_edit_address.text.toString())*/

                updateStaffProfile()

            }
        }
        return view
    }

    private fun updateStaffProfile() {

        val token = mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0).getString(
            Constants.sharedPrefrencesConstant.DEVICE_TOKEN, "")

        val userName = user_edit_name.text.toString()
        val userEmail = user_edit_email.text.toString()
        val userAddress = user_edit_address.text.toString()

        edit_profile_view.update_profile(this, userName, userEmail, userAddress,
            token.toString(), edit_loading).observe(viewLifecycleOwner, Observer {

                if (it != null){

                    if (it.getSuccess()!!){

                        Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()

                        mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.USER_E, 0).edit().putString(Constants.sharedPrefrencesConstant.USER_E, userEmail).apply()

                        edit_loading.visibility = View.GONE

                        mainActivity.loadFragment(fragmentManager, Profile_frag(), R.id.fragment_container, true, "Profile", null)
                    }

                }

        })

    }

    private fun validateFields(): Boolean {

        var userName = user_edit_name.text.toString()
        var userPhone = user_edit_phone.text.toString()
        var userEmail = user_edit_email.text.toString()
        var userAddress = user_edit_address.text.toString()

        if (userName.isEmpty()) {

            user_edit_name.requestFocus()
            user_edit_name.error = "Required Field"
            return false
        } else if (!userName.matches(Regex("[a-z A-z]{3,15}+"))) {

            user_edit_name.requestFocus()
            user_edit_name.error = "Check username"
            return false
        } else if (userPhone.isEmpty()) {

            user_edit_phone.requestFocus()
            user_edit_phone.error = "Required Field"
            return false
        } else if (!userPhone.matches(Regex("^[0-9 +]{9,13}$"))) {

            user_edit_phone.requestFocus()
            user_edit_phone.error = "Check phone number"
            return false
        } else if (userEmail.isEmpty()) {

            user_edit_email.requestFocus()
            user_edit_email.error = "Required Field"
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {

            user_edit_email.requestFocus()
            user_edit_email.error = "Check email address"
            return false
        } else if (userAddress.isEmpty()) {

            user_edit_address.requestFocus()
            user_edit_address.error = "Required Field"

            return false
        } else if (!userAddress.matches(Regex("[a-z A-z0-9()&%#@!$*_+=/?><.,;:}{|]{15,50}+"))) {

            user_edit_address.requestFocus()
            user_edit_address.error = "Check address"
            return false
        }
        else {
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
                upload_txt.visibility = View.VISIBLE
                dialog.dismiss()
            }
        })
        builder.show()
    }

    fun getPhotoFileUri(fileName: String): File? {
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

    //
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            Log.d("requestCode", requestCode.toString())
            if (requestCode == 1) {
                /*user_edit_img.setImageBitmap(data!!.extras!!.get("data") as Bitmap?)
                Log.d("requestCode", requestCode.toString())
                var f = File(Environment.getExternalStorageDirectory().toString())
                //for (temp in f.listFiles()!!) {
                    //Log.d("cameraIN", temp.name)
                    //if (temp.name == "temp.jpg") {
                        //Log.d("cameraIN", temp.name)
                    //}
              //  }
                try {
                    val bitmap: Bitmap
                    val bitmapOptions = BitmapFactory.Options()
                    bitmap = BitmapFactory.decodeFile(
                        f.absolutePath,
                        bitmapOptions
                    )
                    user_edit_img.setImageBitmap(bitmap)
                    val path = (Environment
                        .getExternalStorageDirectory()
                        .toString() + File.separator
                            + "Phoenix" + File.separator + "default")
                    f.delete()
                    var outFile: OutputStream? = null
                    val file = File(path, System.currentTimeMillis().toString() + ".jpg")
                    try {
                        outFile = FileOutputStream(file)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile)
                        outFile.flush()
                        outFile.close()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }*/

                Log.d("requestCode", requestCode.toString())

                    val bitmap: Bitmap? = rotateImageIfRequired(photoFile!!.absolutePath)
                    Log.d("camerPath", photoFile!!.absolutePath)

                    /*Picasso.get()
                        .load(File(photoFile!!.absoluteFile.toString()))
                        .centerCrop()
                        .into(userPro)*/
                    loadBitmapByPicasso(requireContext(), bitmap!!, user_edit_img)
                    //userPro.setImageBitmap(bitmap)
                    sendProfileImg(photoFile!!.absolutePath)
                    //uploadImgPath = photoFile!!.absolutePath

            } else if (requestCode == 2) {
                val selectedImage = data!!.data
                val filePath = arrayOf(MediaStore.Images.Media.DATA)
                val c: Cursor =
                    selectedImage?.let { requireActivity().getContentResolver().query(it, filePath, null, null, null) }!!
                c.moveToFirst()
                val columnIndex: Int = c.getColumnIndex(filePath[0])
                val picturePath: String = c.getString(columnIndex)
                c.close()
                val thumbnail = BitmapFactory.decodeFile(picturePath)
                Log.w(
                    "path of image from gallery......******************.........",
                    picturePath + ""
                )
                sendProfileImg(picturePath)
                user_edit_img.setImageBitmap(thumbnail)
            }
        }
    }

    private fun sendProfileImg(absolutePath: String) {

        val file: File = File(absolutePath)

        edit_profile_view.set_profileImage(this, file, edit_loading).observe(viewLifecycleOwner, Observer {

            if (it != null){

                if (it.getSuccess()!!){

                    Toast.makeText(context, "Image Updated", Toast.LENGTH_SHORT).show()

                }

            }

        })


    }

    fun rotateImageIfRequired(imagePath: String): Bitmap? {
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