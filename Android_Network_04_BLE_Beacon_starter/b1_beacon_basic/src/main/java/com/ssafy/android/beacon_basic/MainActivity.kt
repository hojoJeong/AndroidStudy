package com.ssafy.android.beacon_basic

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import android.bluetooth.BluetoothDevice
import com.ssafy.android.beacon_basic.R
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanRecord
import android.bluetooth.le.ScanResult
import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice
import uk.co.alt236.bluetoothlelib.device.mfdata.IBeaconManufacturerData
import android.content.DialogInterface
import android.util.Log
import android.widget.BaseAdapter
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import com.ssafy.android.beacon_basic.databinding.ActivityMainBinding
import java.lang.Exception

private const val TAG = "MainActivity_싸피"

@SuppressLint("MissingPermission")
class MainActivity : AppCompatActivity() {
    companion object {
        private const val PERMISSION_REQUEST_CODE = 8
    }
    
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothLeScanner: BluetoothLeScanner
    private lateinit var leDeviceListAdapter: LeDeviceListAdapter
    private lateinit var checkPermission: CheckPermission

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var addr: String? = null

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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        checkPermission = CheckPermission(this)
        bluetoothAdapter = bluetoothManager.adapter

        if (bluetoothAdapter == null || !bluetoothAdapter!!.isEnabled) {
            Toast.makeText(this, "블루투스 기능을 확인해 주세요.", Toast.LENGTH_SHORT).show()
            val bleIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(bleIntent, 1)
        }
        bluetoothLeScanner = bluetoothAdapter!!.getBluetoothLeScanner()

        if (!checkPermission.runtimeCheckPermission(this, *runtimePermissions)) {
            ActivityCompat.requestPermissions(this, runtimePermissions, PERMISSION_REQUEST_CODE)
        } else { //이미 전체 권한이 있는 경우
            initView()
        }
    }

    override fun onRequestPermissionsResult( requestCode: Int,  permissions: Array<String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // 권한을 모두 획득했다면.
                initView()
            } else {
                checkPermission.requestPermission()
            }
        }
    }

    fun initView() {
        leDeviceListAdapter = LeDeviceListAdapter(this)
        binding.devicelist.adapter = leDeviceListAdapter
        binding.devicelist.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val device = leDeviceListAdapter.getItem(position) as BluetoothDevice
                addr = device.toString()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_scan ->
                // Toast.makeText(this, "Start Scan", Toast.LENGTH_SHORT).show();
                startScan()
            R.id.action_stop ->
                // Toast.makeText(this, "Stop Scan", Toast.LENGTH_SHORT).show();
                stopScan()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startScan() {
        Log.d(TAG, "startScan")
        leDeviceListAdapter.clear()
        leDeviceListAdapter.notifyDataSetChanged()
        // IBeacon Sensor Scan
        bluetoothLeScanner.startScan(mLeScanCallback)
    }

    private fun stopScan() {
        Log.d(TAG, "stopSCan")

        // IBeacon Sensor Scan
        bluetoothLeScanner.stopScan(mLeScanCallback)
    }


    // ======================IBeacon 용 LeScanCallback=======================
    private val mLeScanCallback: ScanCallback = object : ScanCallback() {
        private var tx = 0
        private var major = 0
        private var minor = 0
        private var distance = 0.0

        override fun onScanResult(callbackType: Int, result: ScanResult) {
            Log.d(TAG, "onScanResult: result:$result")
            processResult(result.device, result.rssi, result.scanRecord)
        }

        fun processResult(device: BluetoothDevice, rssi: Int, scanRecord: ScanRecord?) {
            Log.d(TAG, "device >>>> $device updated.")
            runOnUiThread {
                leDeviceListAdapter.addDevice(device)
                leDeviceListAdapter.notifyDataSetChanged()
            }
            if (device.toString() == addr) {
                val deviceLe = BluetoothLeDevice(device, rssi, scanRecord!!.bytes, System.currentTimeMillis())
                Log.d(TAG, "Selected Device " + deviceLe.address + " updated.")

                val iBeaconData = IBeaconManufacturerData(deviceLe)
                tx = iBeaconData.calibratedTxPower
                major = iBeaconData.major
                minor = iBeaconData.minor

                Log.d(TAG, "UUID: " + iBeaconData.uuid)
                Log.d(TAG, "major: " + iBeaconData.major)
                Log.d(TAG, "minor: " + iBeaconData.minor)

                // distance = (float) rssi / (float) tx;
                distance = getNewAccuracy(tx, rssi.toDouble())
                //distance = Math.pow(12.0, 1.5 * (rssi.toFloat() / tx.toFloat() - 1))

                Log.d(TAG, "rssi : " + rssi + ", tx : " + tx + ", =====거리 : " + distance)

                runOnUiThread {
                    binding.mTv.text = ("Major : " + major + ", Minor " + minor
                            + ", distance : " + distance)
                }

            }
        }
    }

    // IOS 거리 계산 공식 : 값이 많이 튐
    private fun calculateAccuracy(txPower: Int, rssi: Double): Double {
        if (rssi == 0.0) {
            return -1.0 // if we can not determine accuracy, return -1.
        }
        val ratio = rssi * 1.0 / txPower
        return if (ratio < 1.0) {
            Math.pow(ratio, 10.0)
        } else {
            0.89976 * Math.pow(ratio, 7.7095) + 0.111
        }
    }

    private fun getNewAccuracy(txPower: Int, rssi: Double): Double {
        return Math.pow(12.0, 1.5 * (rssi / txPower - 1))
    }

}