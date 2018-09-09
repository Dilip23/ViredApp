package com.example.viredapp.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.viredapp.R
import com.example.viredapp.utilities.UserClient
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.PrefConfig
import kotlinx.android.synthetic.main.activity_sign_up.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity  : AppCompatActivity(){

    lateinit var prefConfig:PrefConfig
    val userClient: UserClient = ApiClient.getApiClient().create(UserClient::class.java)
    val LOG_TAG = SignUpActivity::class.java.simpleName
    lateinit var currentPhotoPath:String
    val REQUEST_IMAGE_CAPTURE : Int = 1
    val REQUEST_GALLERY_IMAGE:Int = 0
    lateinit var galleryPhotoPath:String
    lateinit var userPicPath:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        prefConfig = PrefConfig(this)

        val sign_btn = findViewById<Button>(R.id.btn_signup)
        val camera = findViewById<Button>(R.id.uploadCamera)
        val gallery = findViewById<Button>(R.id.uploadGallery)

        camera.setOnClickListener {
            dispatchTakePicture()
        }

        gallery.setOnClickListener {
            getImageFromGallery()
        }

        sign_btn.setOnClickListener{

            performSignup()
            toast("Signup function Finished")
        }
    }
    //TODO:If SignUp Process complete
    fun performSignup(){

        val name_et:String = et_full_name.text.toString()
        val email_et:String = et_email_address.text.toString()
        val password_et:String = password.text.toString()
        val location_et:String = location.text.toString()


        var username:RequestBody = RequestBody.create(MultipartBody.FORM,name_et)
        var email:RequestBody = RequestBody.create(MultipartBody.FORM,email_et)
        var password:RequestBody = RequestBody.create(MultipartBody.FORM,password_et)
        var location:RequestBody = RequestBody.create(MultipartBody.FORM,location_et)

        val mainfile:File = File(userPicPath)
        var filePart:RequestBody = RequestBody.create(
                MediaType.parse("image/*"),
                mainfile)

        var requestFile:MultipartBody.Part = MultipartBody.Part.createFormData("profile_pic",mainfile.name,filePart)


        Log.d("username -->",username.toString())
        Log.d("email -->",email.toString())
        Log.d("password -->",password.toString())
        Log.d("location -->",location.toString())
        Log.d("requestFIle --> ",requestFile.toString())
        Log.i(LOG_TAG,"performSignup() --> photoFIle --> "+mainfile.toString())

        if(checkDetailsFilled() == true){

            val call:Call<ResponseBody> = userClient.performRegistration(username,email,password, location,requestFile)

            call.enqueue(object :Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    Log.d(LOG_TAG+"-> onFailure",t?.printStackTrace().toString())
                }

                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    try {
                        if (response?.code() == 201) {
                            Log.d("onResponse->","User Created")
                            toast(response.code().toString())
                            showLogin()
                        }
                        else{
                            toast("Some other Response")
                            toast(response?.code().toString())
                        }
                    }catch (e:Exception){
                        Log.d(LOG_TAG,e.printStackTrace().toString())
                    }
                }

            })

        }
    }

    private fun showLogin() {
        toast("Signup Finished , Login Screen Showing")
        val i:Intent = Intent(this,MainActivity::class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(i)

    }

    private fun checkDetailsFilled():Boolean {
        val tag:String = "Enter"
        if(TextUtils.isEmpty(et_email_address.toString())) {
            toast(tag+"Email")
            return false
        }
        if(TextUtils.isEmpty(et_full_name.toString())){
            toast(tag+"Name")
            return false
        }
        if(TextUtils.isEmpty(password.toString())) {
            toast(tag+"Password")
            return false
        }
        if(TextUtils.isEmpty(location.toString())){
            toast(tag+"Location")
            return false
        }
        return true
    }

    private fun toast(s: String) {
        Toast.makeText(this,s, Toast.LENGTH_LONG).show()
    }

    fun dispatchTakePicture(){
        var takePictureIntent:Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //Ensure for atleast one camera activity
        if(takePictureIntent.resolveActivity(packageManager) != null){
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
                toast("dispatchTakePicture"+photoFile.toString())
            }catch (e:IOException){
                Log.d(LOG_TAG,e.printStackTrace().toString())
            }

            if(photoFile !=null) {
                var photoUri:Uri = FileProvider.getUriForFile(this,"com.example.android.fileprovider",photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var bitmap:Bitmap
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data!=null && data.data!=null){
            bitmap = data.extras?.get("data") as Bitmap
            toast(bitmap.toString())
        }
        else if(requestCode == REQUEST_GALLERY_IMAGE && resultCode == Activity.RESULT_OK && data != null)
        {
            var targetUri:Uri = data!!.data
            toast(targetUri.toString())
            try {
                bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(targetUri))

                galleryPhotoPath = imageToString(bitmap)
                userPicPath = galleryPhotoPath
            }catch (e:FileNotFoundException){
                e.printStackTrace()
            }
        }



    }

    @Throws(IOException::class)
    private fun createImageFile():File{
        val timeStamp:String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName:String = "JPEG_"+ timeStamp + "_"
        val storageDir:File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image:File = File.createTempFile(
                imageFileName,//prefix
                ".jpg",//suffix
                storageDir//directory
        )
        currentPhotoPath = image.absolutePath
        userPicPath = currentPhotoPath
        return image
    }

    private fun addPicGallery(){
        val mediaIntent:Intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f:File = File(currentPhotoPath)
        val contentUri:Uri = Uri.fromFile(f)
        mediaIntent.setData(contentUri)
        this.sendBroadcast(mediaIntent)
    }

    private fun getImageFromGallery(){
        toast("get Image from Gallery started")
        val intent:Intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        toast("Intent Created")
        startActivityForResult(intent,REQUEST_GALLERY_IMAGE)
    }


    private fun imageToString(bitmap: Bitmap):String{
        var byteArrayOutputStream:ByteArrayOutputStream  = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream)
        var imgByte : ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(imgByte,Base64.DEFAULT)

    }

}
