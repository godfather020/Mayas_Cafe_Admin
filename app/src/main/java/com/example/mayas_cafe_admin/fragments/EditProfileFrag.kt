package com.example.mayas_cafe_admin.fragments

import android.app.Activity.RESULT_OK
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
import com.example.mayas_cafe_admin.fragments.ViewModel.Edit_Profile_ViewModel
import com.example.mayas_cafe_admin.utils.Constants
import com.squareup.picasso.Picasso
import java.io.*


open class EditProfileFrag : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var saveEdit : Button
    private lateinit var userEditName : EditText
    private lateinit var userEditPhone : EditText
    private lateinit var userEditEmail : EditText
    private lateinit var userEditAddress : EditText
    private lateinit var uploadImg : CardView
    private lateinit var userEditImg : ImageView
    private lateinit var arg : String
    private var photoFile: File? = null
    private lateinit var uploadTxt : TextView
    private lateinit var editProfileView : Edit_Profile_ViewModel
    private lateinit var editLoading : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_edit__profile_frag, container, false)

        editProfileView = ViewModelProvider(this).get(Edit_Profile_ViewModel::class.java)

        mainActivity = (activity as MainActivity)

        mainActivity.toolbar_const.title = "Edit Profile"
        mainActivity.toolbar_const.setTitleTextColor(resources.getColor(R.color.black))

        saveEdit = view.findViewById(R.id.save_edit)
        userEditName = view.findViewById(R.id.user_edit_name)
        userEditPhone = view.findViewById(R.id.user_phone_edit)
        userEditEmail = view.findViewById(R.id.user_edit_email)
        userEditAddress = view.findViewById(R.id.user_edit_add)
        userEditImg = view.findViewById(R.id.user_edit_img)
        uploadImg = view.findViewById(R.id.upload_img)
        uploadTxt = view.findViewById(R.id.upload_txt)
        editLoading = view.findViewById(R.id.edit_profile_loading)

        editLoading.visibility = View.VISIBLE

        if (arguments != null) {

            arg = arguments?.getString("userName").toString()
            userEditName.setText(arg)
            Log.d("userName", arg)

            arg = arguments?.getString("userPhone").toString()
            userEditPhone.setText(arg)
            Log.d("userPhone", arg)

            arg = arguments?.getString("userEmail").toString()
            userEditEmail.setText(arg)
            Log.d("userEmail", arg)

            arg = arguments?.getString("userAddress").toString()
            userEditAddress.setText(arg)
            Log.d("userAddress", arg)
        }

        mainActivity.toolbar_const.title = ""

        uploadImg.setOnClickListener {

            uploadTxt.visibility = View.GONE
            selectImage()
        }

        saveEdit.setOnClickListener {

            if (validateFields()) {

                /*val bundle = Bundle()
                bundle.putString("userName", userEditName.text.toString())
                bundle.putString("userPhone", userEditPhone.text.toString())
                bundle.putString("userEmail", userEditEmail.text.toString())
                bundle.putString("userAddress", userEditAddress.text.toString())*/

                updateStaffProfile()

            }
        }
        return view
    }

    private fun updateStaffProfile() {

        val token = mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.DEVICE_TOKEN, 0).getString(
            Constants.sharedPrefrencesConstant.DEVICE_TOKEN, "")

        val userName = userEditName.text.toString()
        val userEmail = userEditEmail.text.toString()
        val userAddress = userEditAddress.text.toString()

        editProfileView.update_profile(this, userName, userEmail, userAddress,
            token.toString(), editLoading).observe(viewLifecycleOwner, Observer {

                if (it != null){

                    if (it.getSuccess()!!){

                        Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()

                        mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.USER_E, 0).edit().putString(Constants.sharedPrefrencesConstant.USER_E, userEmail).apply()
                        mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.USER_N, 0).edit().putString(Constants.sharedPrefrencesConstant.USER_N, userEditName.text.toString()).apply()
                        mainActivity.getSharedPreferences(Constants.sharedPrefrencesConstant.USER_P, 0).edit().putString(Constants.sharedPrefrencesConstant.USER_P, userEditPhone.text.toString()).apply()

                        editLoading.visibility = View.GONE

                        mainActivity.loadFragment(fragmentManager, ProfileFrag(), R.id.fragment_container, true, "Profile", null)
                    }

                }

        })

    }

    private fun validateFields(): Boolean {

        val userName = userEditName.text.toString()
        val userPhone = userEditPhone.text.toString()
        val userEmail = userEditEmail.text.toString()
        val userAddress = userEditAddress.text.toString()

        if (userName.isEmpty()) {

            userEditName.requestFocus()
            userEditName.error = "Required Field"
            return false
        } else if (!userName.matches(Regex("[a-z A-z]{3,15}+"))) {

            userEditName.requestFocus()
            userEditName.error = "Check username"
            return false
        } else if (userPhone.isEmpty()) {

            userEditPhone.requestFocus()
            userEditPhone.error = "Required Field"
            return false
        } else if (!userPhone.matches(Regex("^[0-9 +]{9,13}$"))) {

            userEditPhone.requestFocus()
            userEditPhone.error = "Check phone number"
            return false
        } else if (userEmail.isEmpty()) {

            userEditEmail.requestFocus()
            userEditEmail.error = "Required Field"
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {

            userEditEmail.requestFocus()
            userEditEmail.error = "Check email address"
            return false
        } else if (userAddress.isEmpty()) {

            userEditAddress.requestFocus()
            userEditAddress.error = "Required Field"

            return false
        } else if (!userAddress.matches(Regex("[a-z A-z0-9()&%#@!$*_+=/?><.,;:}{|]{15,50}+"))) {

            userEditAddress.requestFocus()
            userEditAddress.error = "Check address"
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
                uploadTxt.visibility = View.VISIBLE
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

    //
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            Log.d("requestCode", requestCode.toString())
            if (requestCode == 1) {

                Log.d("requestCode", requestCode.toString())

                    val bitmap: Bitmap? = rotateImageIfRequired(photoFile!!.absolutePath)
                    Log.d("camerPath", photoFile!!.absolutePath)

                    loadBitmapByPicasso(requireContext(), bitmap!!, userEditImg)
                    //userPro.setImageBitmap(bitmap)
                    sendProfileImg(photoFile!!.absolutePath)
                    //uploadImgPath = photoFile!!.absolutePath

            } else if (requestCode == 2) {
                val selectedImage = data!!.data
                val filePath = arrayOf(MediaStore.Images.Media.DATA)
                val c: Cursor =
                    selectedImage?.let { requireActivity().contentResolver.query(it, filePath, null, null, null) }!!
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
                userEditImg.setImageBitmap(thumbnail)
            }
        }
    }

    private fun sendProfileImg(absolutePath: String) {

        val file: File = File(absolutePath)

        editProfileView.set_profileImage(this, file, editLoading).observe(viewLifecycleOwner, Observer {

            if (it != null){

                if (it.getSuccess()!!){

                    Toast.makeText(context, "Image Updated", Toast.LENGTH_SHORT).show()

                }

            }

        })


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