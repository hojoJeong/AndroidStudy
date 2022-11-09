package com.ssafy.android.ble_scan

import android.Manifest
import android.annotation.SuppressLint
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothAdapter
import android.widget.Toast
import android.content.Intent
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.widget.AdapterView.OnItemClickListener
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.*
import android.os.*
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.ssafy.android.ble_scan.databinding.ActivityMainBinding
import java.util.*

private const val TAG = "MainActivity_싸피"

@SuppressLint("MissingPermission")
@RequiresApi(api = Build.VERSION_CODES.M)
class MainActivity : AppCompatActivity() {
    companion object {
        private const val PERMISSION_REQUEST_CODE = 8
    }
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var scanner: BluetoothLeScanner
    private lateinit var decideListAdapter: LeDeviceListAdapter
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        checkPermission = CheckPermission(this)
        blueAdapter = bluetoothManager.adapter

        if (blueAdapter == null || !blueAdapter!!.isEnabled) {
            Toast.makeText(this, "블루투스 기능을 확인해 주세요.", Toast.LENGTH_SHORT).show()
            val bleIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(bleIntent, 1)
        }
        scanner = blueAdapter!!.getBluetoothLeScanner()

        if (!checkPermission.runtimeCheckPermission(this, *runtimePermissions)) {
            ActivityCompat.requestPermissions(this, runtimePermissions, PERMISSION_REQUEST_CODE)
        } else { //이미 전체 권한이 있는 경우
            initView()
        }
    }

    override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<String>, grantResults: IntArray  ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // 권한을 모두 획득했다면.
                initView()
            } else {
                checkPermission.requestPermission()
            }
        }
    }

    private fun initView() {
        decideListAdapter = LeDeviceListAdapter(this)
        binding.devicelist.adapter = decideListAdapter
        binding.devicelist.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val device = decideListAdapter.getItem(position) as BluetoothDevice
                Log.d(TAG, "++++++++++++ Selected Device ++++++++++++++++++")
                Toast.makeText(this@MainActivity, "selected..$device", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onItemClick: $device") //62:A6:2D:32:69:F2
//				onScanResult: result:ScanResult{device=62:A6:2D:32:69:F2, scanRecord=ScanRecord [mAdvertiseFlags=4, mServiceUuids=[0000fd5a-0000-1000-8000-00805f9b34fb], mServiceSolicitationUuids=[], mManufacturerSpecificData={}, mServiceData={0000fd5a-0000-1000-8000-00805f9b34fb=[21, -22, 3, 1, 70, -20, -122, -22, 126, 44, 105, 77, -61, 0, 0, 0, -97, 25, -34, -7]}, mTxPowerLevel=-2147483648, mDeviceName=Smart Tag, mTransportBlocks=[]], rssi=-85, timestampNanos=2925531664814018, eventType=27, primaryPhy=1, secondaryPhy=0, advertisingSid=255, txPower=127, periodicAdvertisingInterval=0}
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
        decideListAdapter.clear()
        decideListAdapter.notifyDataSetChanged()
        Log.d(TAG, "startScan")
        // BLE Sensor Scan
        //전체 scan.
//        scanner.startScan(scanCallback)

        //특정 대상 device만.
        val scanFilters = arrayListOf<ScanFilter>()
        val scanFilter = ScanFilter.Builder()
            .setServiceUuid(ParcelUuid.fromString("0000aa80-0000-1000-8000-00805f9b34fb"))
//            .setDeviceAddress("54:6C:0E:B7:40:05")
            .build()
        scanFilters.add(scanFilter)

        //setting.
        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
            .build()
//        public void startScan(List<ScanFilter> filters, ScanSettings settings,
//            final ScanCallback callback) {
        scanner.startScan(scanFilters, scanSettings, scanCallback)

        //10초 후 scan 중지
        handler.postDelayed({
            Toast.makeText(this, "StopScan...", Toast.LENGTH_SHORT).show()
            stopScan()
        }, 10 * 1_000)
    }

    private fun stopScan() {
        Log.d(TAG, "stopSCan")
        // BLE Sensor Scan Stop
        scanner.stopScan(scanCallback)
    }

    private var scanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            processResult(result)
        }

        override fun onScanFailed(errorCode: Int) {
            Log.d(TAG, "errorCode: errorCode:$errorCode")
        }

        private fun processResult(result: ScanResult) {
            Log.d(TAG, "processResult: ")
            Log.d(TAG, "++++++++++++scan Result++++++++++++++++++")
            Log.d(TAG, result.toString())
            runOnUiThread {
                decideListAdapter.addDevice(result.device)
                decideListAdapter.notifyDataSetChanged()
            }

        }
    }

    var handler = Handler(Looper.getMainLooper())

    override fun onPause() {
        super.onPause()
        stopScan()
    }

}