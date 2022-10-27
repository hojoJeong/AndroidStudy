package com.ssafy.camera1

import android.graphics.BitmapFactory
import android.hardware.Camera
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.camera1.databinding.ActivityCameraBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "MainActivity_싸피"
class CameraActivity : AppCompatActivity() {
    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null

    lateinit var binding:ActivityCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create an instance of Camera
        mCamera = getCameraInstance()

        mPreview = mCamera?.let {
            // Create our Preview view
            CameraPreview(this, it)
        }

        // Set the Preview view as the content of our activity.
        mPreview?.also {
            val preview: FrameLayout = findViewById(R.id.camera_preview)
            preview.addView(it)
        }

        binding.buttonCapture.setOnClickListener {
            // get an image from the camera
            mCamera?.takePicture(null, null, mPicture)
        }

    }

    /** A safe way to get an instance of the Camera object. */
    fun getCameraInstance(): Camera? {
        return try {
            Camera.open() // attempt to get a Camera instance
        } catch (e: Exception) {
            // Camera is not available (in use or does not exist)
            null // returns null if camera is unavailable
        }
    }

    private val mPicture = Camera.PictureCallback { data, _ ->
        val pictureFile: File = createImageFile()

        try {
            val fos = FileOutputStream(pictureFile)
            fos.write(data)
            fos.close()

            //imgeview 보여주기.
            val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
            // 화면에 표시해줌
            binding.imageView.setImageBitmap(bitmap)

            //갤러리로 보내기
            MediaStore.Images.Media.insertImage(
                getContentResolver(),
                currentPhotoPath,
                currentPhotoName, //name
                currentPhotoName  //description
            )

            mCamera?.startPreview()

        } catch (e: FileNotFoundException) {
            Log.d(TAG, "File not found: ${e.message}")
        } catch (e: IOException) {
            Log.d(TAG, "Error accessing file: ${e.message}")
        }
    }

    lateinit var currentPhotoPath: String
    lateinit var currentPhotoName: String
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!

        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path
            currentPhotoPath = absolutePath
            currentPhotoName = currentPhotoPath.substring(currentPhotoPath.lastIndexOf("/")+1)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mCamera?.release()
    }
}