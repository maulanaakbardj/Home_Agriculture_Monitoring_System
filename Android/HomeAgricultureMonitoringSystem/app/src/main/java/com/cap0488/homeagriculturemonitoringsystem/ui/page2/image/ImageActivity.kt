package com.cap0488.homeagriculturemonitoringsystem.ui.page2.image

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cap0488.homeagriculturemonitoringsystem.databinding.ActivityImageBinding
import com.cap0488.homeagriculturemonitoringsystem.ui.page2.identification.IdentificationActivity
import java.io.ByteArrayOutputStream


class ImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSupportActionBar()?.hide();


        binding.buttonGallery.setOnClickListener{
            takeFromGallery()
        }
        binding.buttonCamera.setOnClickListener {
            openCamera()
        }

    }

    private fun takeFromGallery(){
        val i  = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        startActivityForResult(i, IMAGE_REQUEST_CODE)
    }

    private fun openCamera(){


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED){
            val i  = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i, IMAGE_CAMERA)

        }
        else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQ)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQ){
            if(grantResults.isNotEmpty()&& grantResults[0] ==  PackageManager.PERMISSION_GRANTED){
                val i  = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(i, IMAGE_CAMERA)
            }
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var jenis = intent.getStringExtra("extrajenis").toString()

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val uriFoto = data?.data
            moveToIdentification(uriFoto.toString(),jenis)
        }
        else if (requestCode == IMAGE_CAMERA && resultCode == Activity.RESULT_OK){
            var bmp = data?.extras?.get("data") as Bitmap
            val uri = getImageUriFromBitmap(this,bmp)
            moveToIdentification(uri.toString(),jenis)
        }
    }


    fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }

    private fun moveToIdentification(uriFoto : String, jenis :String){
        val i = Intent(this, IdentificationActivity::class.java)
        i.putExtra(IdentificationActivity.EXTRAFOTO,uriFoto)
        i.putExtra(IdentificationActivity.EXTRAJENIS,jenis)
        startActivity(i)
        finish()
    }

    companion object{
        val IMAGE_REQUEST_CODE =100
        val IMAGE_CAMERA = 0
        val CAMERA_REQ = 2

        const val EXTRAJENIS = "extrajenis"


    }

}