package com.ssafy.camera2_app

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.hardware.camera2.*
import android.hardware.camera2.params.OutputConfiguration
import android.hardware.camera2.params.SessionConfiguration
import android.media.Image
import android.media.ImageReader
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.Surface
import android.view.TextureView
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ssafy.camera2_app.databinding.ActivityCameraBinding
import java.io.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "CameraActivity_싸피"
class CameraActivity : AppCompatActivity() {
    lateinit var binding: ActivityCameraBinding
    private lateinit var mCameraDevice: CameraDevice
    private lateinit var mLargestPreviewSize: Size
    private var mHandler: Handler? = null

    private var mHeight: Int = 0
    private var mWidth:Int = 0

    var mCameraId = CAMERA_BACK

    companion object {
        const val CAMERA_BACK = "0"
        const val CAMERA_FRONT = "1"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        initEvent()
        initView()
    }

    private fun initEvent(){
        binding.btnChange.setOnClickListener{
            when(mCameraId) {
                CAMERA_FRONT -> mCameraId = CAMERA_BACK
                CAMERA_BACK -> mCameraId = CAMERA_FRONT
            }
            closeCamera()
            initView()
        }

        binding.buttonShoot.setOnClickListener {
            takePicture()
        }
    }

    private fun initView() {
        Log.d(TAG, "initView: ")
        //30부터 windowManager.getDefaultDisplay()가 deprecated되었음.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display =  windowManager.currentWindowMetrics
            mWidth = display.bounds.width()
            mHeight = display.bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            mWidth = displayMetrics.widthPixels
            mHeight = displayMetrics.heightPixels
        }

//        with(DisplayMetrics()){
//            windowManager.defaultDisplay.getMetrics(this)
//            mHeight = heightPixels
//            mWidth = widthPixels
//        }
        if(binding.textureView.isAvailable){
            startPreview()
        }else{
            binding.textureView.setSurfaceTextureListener(textureListener)
        }
    }

    private var textureListener: TextureView.SurfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int ) {  }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {  }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            Log.d(TAG, "onSurfaceTextureDestroyed: ")
            return false
        }

        // TextureListener 에서 SurfaceTexture 가 사용가능한 경우, camera를 준비한다.
        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            Log.d(TAG, "onSurfaceTextureAvailable: ")
            startPreview()
        }
    }

    //UI Thread 호출하지 않고 별도 thread를 생성해서 호출 할 수 있다.
    fun startPreview() {
        //handlerThread : main thread와 동일하게 Looper를 갖는 Thread를 만든다.
        val handlerThread = HandlerThread("CAMERA2")
        handlerThread.start()
        mHandler = Handler(handlerThread.looper)

        openCamera()
    }

    private fun openCamera() {
        //시스템으로부터 CameraManager 객체를 가져온다
        val manager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        // n 개의 camera정보 loop
        //for (String cameraId : manager.getCameraIdList()) {
        //   characteristics = manager.getCameraCharacteristics(cameraId);
        //  ...
        // 카메라정보를 get
        val characteristics = manager.getCameraCharacteristics(mCameraId)

        //camera에서 지원하는 정보를 map으로 가져온다.
        val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

        //camera에서 출력되는 제일큰 이미지 정보
        mLargestPreviewSize = map!!.getOutputSizes(SurfaceTexture::class.java)[0]
        Log.d(TAG, "openCamera: width : ${mLargestPreviewSize.width}, height : ${mLargestPreviewSize.height}")

        setAspectRatioTextureView(mLargestPreviewSize.width, mLargestPreviewSize.height)

        // 권한체크
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) return

        // CameraManager.openCamera() 메서드 호출.
        // cameraId, stateCallback, handler로 카메라 미리보기를 생성한다
        manager.openCamera(mCameraId, stateCallback, mHandler)

    }

    // 카메라가 정상동작한다면 작동할 콜백
    private val stateCallback = object : CameraDevice.StateCallback() {
        // 정상적으로 작동. parameter : 현재 실행중인 camera.
        override fun onOpened(camera: CameraDevice) {
            // 나중에 release하기 위해 member로 assign
            mCameraDevice = camera

            // 카메라 미리보기 시작.
            takePreview()
        }

        // 연결이 해제시 close처리
        override fun onDisconnected(camera: CameraDevice) {
            mCameraDevice.close()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            Toast.makeText(this@CameraActivity, "카메라를 열지 못했습니다.", Toast.LENGTH_SHORT ).show()
        }
    }

    // 카메라 미리보기를 만들기
    private fun takePreview() {
        try {

            // 미리보기 할 textureview
            val texture = binding.textureView.surfaceTexture

            // 미리보기를 위한 buffer size setting.
            texture?.setDefaultBufferSize(mLargestPreviewSize.width, mLargestPreviewSize.height)

            // 미리보기를 위해 필요한 출력할 표면인 Surface
            val surface = Surface(texture)

            // 미리보기 화면요청을 생성하고, surface를 타겟으로 세팅
            // Preview:          TEMPLATE_PREVIEW
            // Take Photo:       TEMPLATE_STILL_CAPTURE
            // Video record:     TEMPLATE_RECORD
            // Video snapshot:   TEMPLATE_VIDEO_SNAPSHOT
            // Fastest capture:  TEMPLATE_ZERO_SHUTTER_LAG
            // Full control:     TEMPLATE_MANUAL
            val captureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
            // Focus
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            captureRequestBuilder.addTarget(surface)

            // camera capture session이 준비되면 호출될 callback
            var stateCallback = object : CameraCaptureSession.StateCallback() {
                override fun onConfigureFailed(session: CameraCaptureSession) {
                    Log.d(TAG, "Configuration change")
                }

                //준비가 완료되면 호출
                override fun onConfigured(session: CameraCaptureSession) {
                    // builder 로 반복전송한다.
                    session.setRepeatingRequest(captureRequestBuilder.build(), null, mHandler)
                }

            }

            if(Build.VERSION.SDK_INT >= 28){
                val sessionConfiguration = SessionConfiguration(
                    SessionConfiguration.SESSION_REGULAR, listOf(OutputConfiguration(surface)),
                    mainExecutor, stateCallback )
                mCameraDevice.createCaptureSession(sessionConfiguration)

            }else{
                // createCaptureSession으로 camera의 미리보기 시작. argument-(보여줄 surface 목록, captureSession 콜백 메소드, handler)
                // deprecated 대체 방식은 api level 28 이후만 지원.
                mCameraDevice.createCaptureSession(listOf(surface), stateCallback , mHandler)
            }


        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }


    // 사진찍을 때 호출하는 메서드
    private fun takePicture() {
        try {

            var width = mLargestPreviewSize.width
            var height = mLargestPreviewSize.height

            val imageReader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1)

            val outputSurface = ArrayList<Surface>(2)
            outputSurface.add(imageReader.surface)

            val captureBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            captureBuilder.addTarget(imageReader.surface)
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)

            //촬영되고 난 후 callback.
            val captureListener = object : CameraCaptureSession.CaptureCallback() {
                override fun onCaptureCompleted(session: CameraCaptureSession, request: CaptureRequest, result: TotalCaptureResult) {
                    super.onCaptureCompleted(session, request, result)
                    Toast.makeText(this@CameraActivity, "사진이 촬영되었습니다", Toast.LENGTH_SHORT).show()

                    takePreview()
                }
            }

            // outputSurface 에 위에서 만든 captureListener 를 달아, 캡쳐(사진 찍기) 해주고 나서 카메라 미리보기 세션을 재시작한다
            mCameraDevice.createCaptureSession(outputSurface, object : CameraCaptureSession.StateCallback() {
                override fun onConfigureFailed(session: CameraCaptureSession) {}

                override fun onConfigured(session: CameraCaptureSession) {
                    try {
                        session.capture(captureBuilder.build(), captureListener, mHandler)
                    } catch (e: CameraAccessException) {
                        e.printStackTrace()
                    }
                }

            }, null)


            var file = createImageFile()

            //사진 저장
            val readerListener = object : ImageReader.OnImageAvailableListener {
                override fun onImageAvailable(reader: ImageReader?) {
                    var image : Image? = null
                    try {
                        image = imageReader.acquireLatestImage()

                        val buffer = image!!.planes[0].buffer
                        var bytes = ByteArray(buffer.capacity())
                        buffer.get(bytes)

                        //rotation 보정
                        if( mCameraId == CAMERA_BACK ){
                            bytes = rotateImage(bytes, 90F)
                        }else if( mCameraId == CAMERA_FRONT ){
                            bytes = rotateImage(bytes, 270F)
                        }

                        var output: OutputStream? = null
                        try {
                            output = FileOutputStream(file)
                            output.write(bytes)

                        } finally {
                            output?.close()

                            //갤러리로 보내기
                            MediaStore.Images.Media.insertImage(
                                getContentResolver(),
                                currentPhotoPath,
                                currentPhotoName, //name
                                currentPhotoName  //description
                            )
                        }

                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        image?.close()
                    }
                }

            }

            // imageReader 객체에 위에서 만든 readerListener 를 달아서, 이미지가 사용가능하면 사진을 저장한다
            imageReader.setOnImageAvailableListener(readerListener, null)

        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    //이미지 회전
    fun rotateImage(bytes:ByteArray, degree:Float):ByteArray{
        val bitmap = BitmapFactory.decodeByteArray( bytes, 0, bytes.size )

        val rotateMatrix = Matrix()
        rotateMatrix.postRotate(degree)
        val rotatedBitmap: Bitmap = Bitmap.createBitmap(bitmap, 0,0, bitmap.width, bitmap.height, rotateMatrix, false)
        val stream = ByteArrayOutputStream()
        rotatedBitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
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

    override fun onPause() {
        super.onPause()
        closeCamera()
    }

    //해상도로 width height 계산
    private fun setAspectRatioTextureView(ResolutionWidth: Int, ResolutionHeight: Int) {
        if (ResolutionWidth > ResolutionHeight) {
            val newWidth = mWidth
            val newHeight = mWidth * ResolutionWidth / ResolutionHeight
            updateTextureViewSize(newWidth, newHeight)
        } else {
            val newWidth = mWidth
            val newHeight = mWidth * ResolutionHeight / ResolutionWidth
            updateTextureViewSize(newWidth, newHeight)
        }
    }

    private fun updateTextureViewSize(viewWidth: Int, viewHeight: Int) {
        Log.d(TAG, "TextureView Width : $viewWidth TextureView Height : $viewHeight")
        binding.textureView.layoutParams = FrameLayout.LayoutParams(viewWidth, viewHeight)
    }

    // 카메라 객체를 시스템에 반환하는 메서드
    private fun closeCamera() {
        mCameraDevice.close()
    }
}



