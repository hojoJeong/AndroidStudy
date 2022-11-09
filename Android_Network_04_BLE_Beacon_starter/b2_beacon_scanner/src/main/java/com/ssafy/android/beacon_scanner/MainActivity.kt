package com.ssafy.android.beacon_scanner

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.ssafy.android.beacon_scanner.databinding.ActivityMainBinding
import com.ssafy.android.util.CheckPermission

private const val TAG = "MainActivity_싸피"

@RequiresApi(api = Build.VERSION_CODES.O)
@SuppressLint("MissingPermission")
class MainActivity : BaseActivity() {
    companion object {
        private const val PERMISSION_REQUEST_CODE = 8
    }
    private lateinit var blueMan: BluetoothManager
    private lateinit var bluetoothLeScanner: BluetoothLeScanner
    private lateinit var checkPermission: CheckPermission

    private var blueAdapter: BluetoothAdapter? = null

    private val runtimePermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_CONNECT
    )

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        blueMan = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        checkPermission = CheckPermission(this)
        blueAdapter = blueMan.adapter

        if (blueAdapter == null || !blueAdapter!!.isEnabled) {
            Toast.makeText(this, "블루투스 기능을 확인해 주세요.", Toast.LENGTH_SHORT).show()
            val bleIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(bleIntent, 1)
        }
        bluetoothLeScanner = blueAdapter!!.getBluetoothLeScanner()

        if (!checkPermission.runtimeCheckPermission(this, *runtimePermissions)) {
            ActivityCompat.requestPermissions(this, runtimePermissions, PERMISSION_REQUEST_CODE)
        } else { //이미 전체 권한이 있는 경우
            buttonToggle(false)
            initView()
        }
    }

    override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // 권한을 모두 획득했다면.
                buttonToggle(false)
                initView()
            } else {
                checkPermission.requestPermission()
            }
        }
    }

    /** */
    var toggle = true
    private fun initView() {
        myScanner = MyBeaconScanner(this, bluetoothLeScanner, EurekaActivity::class.java)
        binding.startService.setOnClickListener { v: View? ->
            Log.d(TAG, "MainActivity: startScan")
            myScanner.startScanning()
            buttonToggle(toggle)
        }
        binding.stopService.setOnClickListener { v: View? ->
            Log.d(TAG, "MainActivity: stopScan")
            myScanner.destroy()
            buttonToggle(toggle)
        }
    }

    private fun buttonToggle(toggle: Boolean) {
        binding.startService.isEnabled = !toggle
        binding.stopService.isEnabled = toggle
        this.toggle = !toggle
    }

}