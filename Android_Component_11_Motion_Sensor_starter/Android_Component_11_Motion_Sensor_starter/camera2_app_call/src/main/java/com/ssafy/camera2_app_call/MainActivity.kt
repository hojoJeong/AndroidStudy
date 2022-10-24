package com.ssafy.camera2_app_call

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var file: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener{
            capture()
        }
    }

    private fun capture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        file = createImageFile()
        //AndroidMenifest에 설정된 URI와 동일한 값으로 설정한다.
        var photoUri = FileProvider.getUriForFile(this, "com.ssafy.camera2_app_call.fileprovider", file)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        requestActivity.launch(intent) //카메라 앱을 실행 한 후 결과를 받기 위해서 launch
    }

    val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){

            if (Build.VERSION.SDK_INT >= 29) {
                val source: ImageDecoder.Source = ImageDecoder.createSource(contentResolver, Uri.fromFile(file))
                val bitmap = ImageDecoder.decodeBitmap(source)
                imageView.setImageBitmap(bitmap)
            } else {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(file))
                imageView.setImageBitmap(bitmap)
            }
        }
    }

    lateinit var currentPhotoPath: String

    // Create an image file name
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!

        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path
            currentPhotoPath = absolutePath
        }
    }
}