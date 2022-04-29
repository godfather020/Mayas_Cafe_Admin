package com.example.mayas_cafe_admin.fragments

import android.R.attr
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.fragment.app.Fragment
import com.example.mayas_cafe_admin.MainActivity
import com.example.mayas_cafe_admin.R
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
    lateinit var popup: PopupMenu
    private val CAMERA_REQUEST_CODE = 90
    private val GALLERY_REQUEST_CODE = 99
    lateinit var resultLauncherCamera: ActivityResultLauncher<Intent>
    lateinit var resultLauncherGallery: ActivityResultLauncher<Intent>
    lateinit var upload_txt : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_edit__profile_frag, container, false)

        mainActivity = (activity as MainActivity)

        save_edit = view.findViewById(R.id.save_edit)
        user_edit_name = view.findViewById(R.id.user_edit_name)
        user_edit_phone = view.findViewById(R.id.user_phone_edit)
        user_edit_email = view.findViewById(R.id.user_edit_email)
        user_edit_address = view.findViewById(R.id.user_edit_add)
        user_edit_img = view.findViewById(R.id.user_edit_img)
        upload_img = view.findViewById(R.id.upload_img)
        upload_txt = view.findViewById(R.id.upload_txt)

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

                val bundle = Bundle()
                bundle.putString("userName", user_edit_name.text.toString())
                bundle.putString("userPhone", user_edit_phone.text.toString())
                bundle.putString("userEmail", user_edit_email.text.toString())
                bundle.putString("userAddress", user_edit_address.text.toString())

                mainActivity.loadFragment(fragmentManager, Profile_frag(), R.id.fragment_container, true, "Profile", bundle)
            }
        }
        return view
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
                //val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val f = File(Environment.getExternalStorageDirectory().toString(), "temp.jpg")
                intent.putExtra("File", f)

                //context?.let { FileProvider.getUriForFile(it, requireContext().getApplicationContext().getPackageName() + ".provider", f) }
                startActivityForResult(intent, 1)
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


    //
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            Log.d("requestCode", requestCode.toString())
            if (requestCode == 1) {
                user_edit_img.setImageBitmap(data!!.extras!!.get("data") as Bitmap?)
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
                }
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
                user_edit_img.setImageBitmap(thumbnail)
            }
        }
    }

}