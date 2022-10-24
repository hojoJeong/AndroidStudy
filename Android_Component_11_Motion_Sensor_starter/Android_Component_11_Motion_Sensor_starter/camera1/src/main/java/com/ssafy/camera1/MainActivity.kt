package com.ssafy.camera1

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.ssafy.camera1.databinding.ActivityMainBinding
import java.util.*

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val permissionListener = object : PermissionListener {
            // 권한 얻기에 성공했을 때 동작 처리
            override fun onPermissionGranted() {
                startActivity(Intent(this@MainActivity, CameraActivity::class.java))
                finish()
            }
            // 권한 얻기에 실패했을 때 동작 처리
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@MainActivity,
                    "카메라 권한이 거부되었습니다.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setDeniedMessage("[설정] 에서 카메라 접근 권한을 부여해야만 사용이 가능합니다.")
            // 필요한 권한 설정
            .setPermissions(
                Manifest.permission.CAMERA
            )
            .check()
    }
}