package com.ssafy.android.ble_control

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.le.*
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ssafy.android.ble_control.databinding.ActivityMainBinding
import com.ssafy.android.ble_control.from_manufactor_lib.Sensor
import com.ssafy.android.ble_control.from_manufactor_lib.SensorTagGatt
import com.ssafy.android.ble_control.util.CheckPermission
import com.ssafy.android.ble_control.util.Conversion
import java.util.*

private const val TAG = "MainActivity_싸피"

@SuppressLint("MissingPermission")
@RequiresApi(api = Build.VERSION_CODES.M)
class MainActivity : AppCompatActivity() {
    companion object {
        private const val PERMISSION_REQUEST_CODE = 8
    }

    private lateinit var blueMan: BluetoothManager
    private lateinit var scanner: BluetoothLeScanner
    private lateinit var devideListAdapter: LeDeviceListAdapter
    private lateinit var checkPermission: CheckPermission

    private var blueAdapter: BluetoothAdapter? = null
    private var gatt: BluetoothGatt? = null

    private val runtimePermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_CONNECT
    )
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        blueMan = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        checkPermission = CheckPermission(this)
        blueAdapter = blueMan.adapter

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
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
        devideListAdapter = LeDeviceListAdapter(this)
        binding!!.devicelist.adapter = devideListAdapter
        binding!!.devicelist.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val device = devideListAdapter.getItem(position) as BluetoothDevice
                gatt = device.connectGatt(applicationContext, true, gattCallback)
                handler.sendEmptyMessage(CONNECTING)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_scan ->
//                Toast.makeText(this, "Start Scan", Toast.LENGTH_SHORT).show();
                startScan()
            R.id.action_stop ->
//                Toast.makeText(this, "Stop Scan", Toast.LENGTH_SHORT).show();
                stopScan()
            R.id.action_test1 -> {
                Toast.makeText(this, "test1.... ", Toast.LENGTH_SHORT).show()
                testButton() //조도
            }
            R.id.action_test2 -> {
                Toast.makeText(this, "test2.... ", Toast.LENGTH_SHORT).show()
                testButton1() //습도
            }
            R.id.action_test3 -> {
                Toast.makeText(this, "test3.... ", Toast.LENGTH_SHORT).show()
                testButton2() // Key
            }
            R.id.action_test4 -> {
                Toast.makeText(this, "test4.... ", Toast.LENGTH_SHORT).show()
                testButton4() // Key
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun startScan() {
        devideListAdapter.clear()
        devideListAdapter.notifyDataSetChanged()
        Log.d(TAG, "startScan")
        // BLE Sensor Scan
//        scanner.startScan(scanCallback)

        //특정 대상 device만.
        val scanFilters = arrayListOf<ScanFilter>()
        val scanFilter = ScanFilter.Builder()
//            .setServiceUuid(ParcelUuid.fromString("0000aa80-0000-1000-8000-00805f9b34fb"))
            .setDeviceAddress("54:6C:0E:B7:40:05")
            .build()
        scanFilters.add(scanFilter)

        //setting.
        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
            .build()
//        public void startScan(List<ScanFilter> filters, ScanSettings settings,
//            final ScanCallback callback) {
        scanner.startScan(scanFilters, scanSettings, scanCallback)


//        handler.postDelayed({
//                Toast.makeText(getApplicationContext(), "stopScan...", Toast.LENGTH_SHORT).show()
//                stopScan()
//        }, 30 * 1000)
    }

    private fun stopScan() {
        Log.d(TAG, "stopSCan")
        // BLE Sensor Scan Stop
        scanner.stopScan(scanCallback)
        if (gatt != null) {
            gatt!!.disconnect()
            gatt = null
        }
    }

    var scanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            processResult(result)
        }

        override fun onScanFailed(errorCode: Int) {
            Log.d(TAG, "errorCode: errorCode:$errorCode")
        }

        private fun processResult(result: ScanResult) {
            Log.d(TAG, "processResult: result:$result")
            if (gatt == null) {
                Log.d(TAG, "++++++++++++scan Result++++++++++++++++++")
                Log.d(TAG, result.device.toString())
                runOnUiThread {
                    devideListAdapter.addDevice(result.device)
                    devideListAdapter.notifyDataSetChanged()
                }
            }
        }
    }
    var gattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                handler.sendEmptyMessage(CONNECTED)
                gatt.discoverServices()
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                handler.sendEmptyMessage(DISCONNECT)
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            Log.i(TAG, "====== onServicesDiscovered called......")
            val services = gatt.services
            for (service in services) {
                val serviceUuid = service.uuid.toString()
                Log.e(TAG, "service UUID : $serviceUuid")
                val characteristics = service.characteristics
                for (c in characteristics) {
                    val charUuid = c.uuid.toString()
                    Log.i(TAG, "service charUuid : $charUuid")
                }
            }
        }

        // gatt.readCharacteristic Callback
        override fun onCharacteristicRead(  gatt: BluetoothGatt, ch: BluetoothGattCharacteristic, status: Int ) {
            Log.d(TAG, "####### onCharacteristicRead")
            if (status == BluetoothGatt.GATT_SUCCESS) {
                getCharacteristic(ch)
            }
        }

        override fun onReadRemoteRssi(gatt: BluetoothGatt, rssi: Int, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "RSSI : $rssi")
                // h.obtainMessage(RSSI, rssi).sendToTarget();
            }
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt, ch: BluetoothGattCharacteristic) {
            Log.i(TAG, "onCharacteristicChanged========================")
            if (gatt != null) {
                getCharacteristic(ch)
            }
        }

        override fun onCharacteristicWrite( gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int ) {
            Log.d(TAG, "onCharacteristicWrite****************************")
        }

        override fun onDescriptorWrite( gatt: BluetoothGatt, descriptor: BluetoothGattDescriptor, status: Int ) {
            Log.d(TAG, "onDescriptorWrite------------------------")
            Log.d( TAG, "descriptor : " + descriptor.uuid + ", status : " + status )
        }
    }

    var value1: String? = null
    var value2: String? = null
    var value3: String? = null

    fun getCharacteristic(ch: BluetoothGattCharacteristic?) {
        if (blueAdapter == null || gatt == null || ch == null) {
            return
        }

        val uuid = ch.uuid
        val rawValue = ch.value

        if (uuid == SensorTagGatt.UUID_IRT_DATA) { // temp
            val v = Sensor.IR_TEMPERATURE.convert(rawValue)
            value1 = "온도 : " + String.format("%.1f°C", v!!.y)
            Log.d(TAG, "IR_TEMPERATURE: $value1")
        } else if (uuid == SensorTagGatt.UUID_HUM_DATA) {
            val v = Sensor.HUMIDITY.convert(rawValue)
            value2 = "습도 : " + String.format("%.1f %%rH", v!!.x) //rh : Relative Humidity
            Log.d(TAG, "Humidity Data: $value2")
        }else {
            value3 = Conversion.BytetohexString(rawValue, rawValue.size)
            Log.d(TAG, "key Data: $value3")
        }

        runOnUiThread {
            if ("" != value1) binding!!.mTv1.text = value1
            if ("" != value2) binding!!.mTv2.text = value2
            if ("" != value3) binding!!.mTv3.text = value3
        }

        if (rawValue.size > 0) {
            Log.d(TAG, "&&&&&&&&&&&&&&&&&&&&&&&&&&&")
            Log.d(TAG, "value : $value1, $value2, $value3")
            Log.d(TAG, "&&&&&&&&&&&&&&&&&&&&&&&&&&&")
        }
    }

    private val CONNECTING = 1
    private val CONNECTED = 2
    private val DISCONNECT = 3
    private val RSSI = 4
    private val MSG = 5
    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                CONNECTING -> Toast.makeText(applicationContext, "CONNECTING", Toast.LENGTH_SHORT).show()
                CONNECTED -> {
                    Toast.makeText(applicationContext, "CONNECTED", Toast.LENGTH_SHORT).show()
                    (findViewById<View>(R.id.mTv0) as TextView).text = "BLESensor 에 연결되었습니다."
                }
                DISCONNECT -> {
                    Toast.makeText(applicationContext, "DISCONNECT", Toast.LENGTH_SHORT).show()
                    (findViewById<View>(R.id.mTv0) as TextView).text = "BLESensor 에 연결이 끊어졌습니다."
                }
                RSSI -> Toast.makeText(applicationContext, "rssi : " + msg.obj, Toast.LENGTH_SHORT).show()
                MSG -> Toast.makeText(applicationContext, "info : " + msg.obj, Toast.LENGTH_SHORT).show()
                else -> {}
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stopScan()
    }

    fun sleep(time: Int) {
         Thread.sleep(time.toLong())
    }

    private fun enableSensor(sevUuid: UUID?, confUuid: UUID?) {
        Log.d(TAG, "enableSensor 수행 시작 ------------------")
        val charistic = gatt!!.getService(sevUuid).getCharacteristic(confUuid)

        val value = ByteArray(1)
        value[0] = 1
        charistic.value = value
        gatt!!.writeCharacteristic(charistic)

        Log.d(TAG, "enableSensor 수행 완료 ------------------")
        Thread.sleep(100)
    }

    //온도 테스트
    private fun testButton() {
        enableSensor(SensorTagGatt.UUID_IR_TEMPERATURE_SERV, SensorTagGatt.UUID_IRT_CONF)

        val c = gatt!!.getService(SensorTagGatt.UUID_IR_TEMPERATURE_SERV).getCharacteristic(SensorTagGatt.UUID_IRT_DATA)

        gatt!!.setCharacteristicNotification(c, true)

        val descriptor = c.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"))

        Log.d(TAG, "testButton 426  descriptor ====: $descriptor")
        if (descriptor != null) {
            descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            gatt!!.writeDescriptor(descriptor)
        }

        Log.d(TAG, "testButton 501 : " + c.value)
        if (c.value != null) {
            Log.d(TAG, "testButton 501 : " + c.value[0])
        }

        Thread.sleep(100)

    }

    //습도 테스트 OK
    private fun testButton1() {
        enableSensor(SensorTagGatt.UUID_HUMIDITY_SERV, SensorTagGatt.UUID_HUM_CONF)

        val c = gatt!!.getService(SensorTagGatt.UUID_HUMIDITY_SERV).getCharacteristic(SensorTagGatt.UUID_HUM_DATA)
        gatt!!.setCharacteristicNotification(c, true)

        val descriptor = c.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"))

        if (descriptor != null) {
            descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            gatt!!.writeDescriptor(descriptor)
        }

        if (c.value != null) {
            Log.d(TAG, "testButton1 501 : " + c.value[0])
        }

        Thread.sleep(100)
    }

    // KEY_DATA.
    private fun testButton2() {
        var c: BluetoothGattCharacteristic? = null

        c = gatt!!.getService(SensorTagGatt.UUID_KEY_SERV).getCharacteristic(SensorTagGatt.UUID_KEY_DATA)

        gatt!!.setCharacteristicNotification(c, true)
        val descriptor = c.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"))

        if (descriptor != null) {
            descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            gatt!!.writeDescriptor(descriptor)
        }
    }


    // 테스트
    private fun testButton4() {

    }

}