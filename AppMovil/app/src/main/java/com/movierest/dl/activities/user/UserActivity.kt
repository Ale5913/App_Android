package com.movierest.dl.activities.user

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.movierest.dl.MainActivity
import com.movierest.dl.R
import com.movierest.dl.restapi.ResoucesURL
import com.movierest.dl.restapi.RestApiAdapter
import com.movierest.dl.utils.UserPreferences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.user_activity.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.lang.Exception
import java.util.jar.Manifest


class UserActivity : AppCompatActivity() {
    val TAG = "UserActivity"
    val REQUEST_ID_READ_EXTERNAL_STORAGE = 100
    val REQUEST_ACTION_PICK = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity)

        Picasso.get()
            .load(ResoucesURL.URL_RESOURCE_AVATAR + UserPreferences.getAvatar(this))
            .error(R.drawable.logo)
            .into(avatarIV);

        closeSessionIV.setOnClickListener {
            UserPreferences.closeSession(this)
            Toast.makeText(this,"Cerrado de sesión exitosa",Toast.LENGTH_LONG).show()

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        avatarIV.setOnClickListener {
            request_permission_control();
        }

        emailET.setText(UserPreferences.getEmail(this))
        nameET.setText(UserPreferences.getName(this))

    }

    fun request_permission_control(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_ID_READ_EXTERNAL_STORAGE)
        } else {
            Log.i(TAG, "Permiso dado")
            // desplegar la galeria
            selectImageGallery()
        }
    }

    fun selectImageGallery(){

        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, REQUEST_ACTION_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_ACTION_PICK && resultCode == Activity.RESULT_OK){
            if(data != null){
                val uri = data.data
//                Log.i(TAG+" URI",uri.toString())

                val media = arrayOf(MediaStore.Images.Media.DATA)
//                Log.i(TAG+" media",media[0])

                var cursor = getContentResolver().query(uri,media,null,null,null)   //contentResolver
                cursor.moveToFirst()
                val columnIndex = cursor.getColumnIndex(media[0])
                var path = cursor.getString(columnIndex)
//                Log.i(TAG+" path",path)
                cursor.close()

                var file: File

                try {
                    file = File(path)
                    Log.i(TAG,file.name)

                    uploadAvatarToServer(file)
                }catch (e: Exception) {
                    Log.e(TAG, e.toString())
                }








            }
        }


    }

    fun uploadAvatarToServer(file: File){

        val reqFile = RequestBody.create(MediaType.parse("image/*"),file)
        val body =MultipartBody.Part.createFormData("image",file.name, reqFile)

        var restApiAdapter = RestApiAdapter()
        var gson = restApiAdapter.gsonDeserizerSimple()
        val endPointsApi = restApiAdapter.conection(gson)

        val call = endPointsApi.uploadAvatar(body, UserPreferences.getId(this))

        call.enqueue(object : retrofit2.Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                var imageName = response.body()

                if (imageName != null) {

                    Log.i(TAG+" res",imageName)
                    Log.i(TAG+" res",ResoucesURL.URL_RESOURCE_AVATAR + imageName)

                    UserPreferences.setAvatar(this@UserActivity,imageName)

                    Picasso.get()
                        .load(ResoucesURL.URL_RESOURCE_AVATAR + imageName)
                        .error(R.mipmap.ic_launcher)
                        .into(avatarIV);
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode){
            REQUEST_ID_READ_EXTERNAL_STORAGE -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // desplegar la galeria
                    selectImageGallery()
                } else {
                    Log.i(TAG, "Permiso no dado")
                }
            }
        }
    }

}
