package com.ssafy.android.ble

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ssafy.android.ble.databinding.ActivityMainBinding
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
    private lateinit var deviceListAdapter: LeDeviceListAdapter
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

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(getLayoutInflater())
        setContentView(binding.getRoot())

        bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        checkPermission = CheckPermission(this)
        blueAdapter = bluetoothManager.getAdapter()

        if (blueAdapter == null || !blueAdapter!!.isEnabled()) {
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

    override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
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
        deviceListAdapter = LeDeviceListAdapter(this)
        binding.devicelist.setAdapter(deviceListAdapter)
        binding.devicelist.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                val device: BluetoothDevice = deviceListAdapter.getItem(position) as BluetoothDevice
                gatt = device.connectGatt(applicationContext, false, gattCallback)
                handler.sendEmptyMessage(CONNECTING)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.main, menu)
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
                Toast.makeText(this, "test1.온도.. ", Toast.LENGTH_SHORT).show()
                testButton() //온도
            }
            R.id.action_test2 -> {
                Toast.makeText(this, "test2.습도.. ", Toast.LENGTH_SHORT).show()
                testButton1() //습도
            }
            R.id.action_test3 -> {
                Toast.makeText(this, "test3.Key.. ", Toast.LENGTH_SHORT).show()
                testButton2() // Key
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startScan() {
        deviceListAdapter.clear()
        deviceListAdapter.notifyDataSetChanged()
        Log.d(TAG, "startScan")
        // BLE Sensor Scan
        scanner.startScan(scanCallback)

//        handler.postDelayed({
//                Toast.makeText(getApplicationContext(), "stopScan...", Toast.LENGTH_SHORT).show()
//                stopScan()
//       }, 30 * 1000)
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
            Log.d(TAG, "onScanResult: result:$result")
            if (gatt == null) {
                Log.d(TAG, "++++++++++++Device++++++++++++++++++")
                Log.d(TAG, result.device.toString())
                runOnUiThread {
                    deviceListAdapter.addDevice(result.device)
                    deviceListAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    //call back
    var gattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {

        //Gatt Client와 Server간 연결 변경시 call-back 되는 함수
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            Log.i(TAG, "====== onConnectionStateChange called......")
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                handler.sendEmptyMessage(CONNECTED)
                gatt.discoverServices()
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                handler.sendEmptyMessage(DISCONNECT)
            }
        }

        //gatt.discoverServices() 호출시 call-back 되는 함수
        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            Log.i(TAG, "====== onServicesDiscovered called......")
            val services: List<BluetoothGattService> = gatt.getServices()
            for (service in services) {
                val serviceUuid: String = service.getUuid().toString()
                Log.e(TAG, "service UUID : $serviceUuid")
                val characteristics: List<BluetoothGattCharacteristic> = service.getCharacteristics()
                for (c in characteristics) {
                    val charUuid: String = c.getUuid().toString()
                    Log.i(TAG, "service charUuid : $charUuid")
                }
            }
        }

        // gatt.readCharacteristic() 호출시(get 명령어)  call-back 되는 함수
        override fun onCharacteristicRead( gatt: BluetoothGatt, ch: BluetoothGattCharacteristic, status: Int ) {
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
        //Notification 수신시 call-back 되는 함수
        override fun onCharacteristicChanged(gatt: BluetoothGatt, ch: BluetoothGattCharacteristic) {
            Log.i(TAG, "onCharacteristicChanged========================")
            if (gatt != null) {
                getCharacteristic(ch)
            }
        }

        //Gatt Server에 set 명령어 수행시 호출되는 함수 (설정변경 등...)
        override fun onCharacteristicWrite( gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int ) {
            Log.d(TAG, "onCharacteristicWrite****************************")
        }

        //Descriptor 설정 변경시 호출되는 함수
        override fun onDescriptorWrite( gatt: BluetoothGatt, descriptor: BluetoothGattDescriptor, status: Int ) {
            Log.d(TAG, "onDescriptorWrite------------------------")
            Log.d(TAG, "descriptor : " + descriptor.getUuid()  + ", status : " + status)
        }
    }

    var value: String? = null
    //전달된 Characteristic 값 변환하는 함수
    private fun getCharacteristic(ch: BluetoothGattCharacteristic?) {
        if (blueAdapter == null || gatt == null || ch == null) {
            return
        }
        val uuid: UUID = ch.getUuid()
        val rawValue: ByteArray = ch.getValue()
        value = Conversion.BytetohexString(rawValue, rawValue.size)

        Log.i(TAG, "getCharacteristic: value:$value")

        runOnUiThread(Runnable { binding.mTv.setText(value) })
        if (rawValue.size > 0) {
            Log.d(TAG, "&&&&&&&&&&&&&&&&&&&&&&&&&&&")
            Log.d(TAG, "value : $value")
            Log.d(TAG, "&&&&&&&&&&&&&&&&&&&&&&&&&&&")
        }
    }

    //List<BluetoothGattService> services;
    private val CONNECTING = 1
    private val CONNECTED = 2
    private val DISCONNECT = 3
    private val RSSI = 4
    private val MSG = 5
    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                CONNECTING -> Toast.makeText( getApplicationContext(), "CONNECTING", Toast.LENGTH_SHORT ).show()
                CONNECTED -> {
                    Toast.makeText(getApplicationContext(), "CONNECTED", Toast.LENGTH_SHORT).show()
                    (findViewById<View>(R.id.mTv) as TextView).setText("BLESensor 에 연결되었습니다.")
                }
                DISCONNECT -> {
                    Toast.makeText(getApplicationContext(), "DISCONNECT", Toast.LENGTH_SHORT).show()
                    (findViewById<View>(R.id.mTv) as TextView).setText("BLESensor 에 연결이 끊어졌습니다.")
                }
                RSSI -> Toast.makeText( getApplicationContext(), "rssi : " + msg.obj, Toast.LENGTH_SHORT).show()
                MSG -> Toast.makeText( getApplicationContext(), "info : " + msg.obj, Toast.LENGTH_SHORT ).show()
                else -> {}
            }
        }
    }

    protected override fun onPause() {
        super.onPause()
        stopScan()
    }

    fun sleep(time: Int) {
        try {
            Thread.sleep(time.toLong())
        } catch (e: InterruptedException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

    var UUID_ACC_SERV = UUID
        .fromString("f000aa10-0451-4000-b000-000000000000")
    var UUID_ACC_DATA = UUID
        .fromString("f000aa11-0451-4000-b000-000000000000")
    var UUID_IRT_SERV = UUID
        .fromString("f000aa00-0451-4000-b000-000000000000")
    var UUID_IRT_DATA = UUID
        .fromString("f000aa01-0451-4000-b000-000000000000")
    var UUID_IRT_CONF = UUID
        .fromString("f000aa02-0451-4000-b000-000000000000")
    var UUID_KEY_SERV = UUID
        .fromString("0000ffe0-0000-1000-8000-00805f9b34fb")
    var UUID_KEY_DATA = UUID
        .fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
    var UUID_HUM_SERV = UUID
        .fromString("f000aa20-0451-4000-b000-000000000000")
    var UUID_HUM_DATA = UUID
        .fromString("f000aa21-0451-4000-b000-000000000000")
    var UUID_HUM_CONF = UUID
        .fromString("f000aa22-0451-4000-b000-000000000000")
    var UUID_GYR_SERV = UUID
        .fromString("f000aa50-0451-4000-b000-000000000000")
    var UUID_GYR_DATA = UUID
        .fromString("f000aa51-0451-4000-b000-000000000000")
    var UUID_GYR_CONF = UUID
        .fromString("f000aa52-0451-4000-b000-000000000000")

    private fun enableSensor(sevUuid: UUID, confUuid: UUID) {
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
        enableSensor(UUID_IRT_SERV, UUID_IRT_CONF)

        val c = gatt!!.getService(UUID_IRT_SERV).getCharacteristic(UUID_IRT_DATA)

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
        enableSensor(UUID_HUM_SERV, UUID_HUM_CONF)

        val c = gatt!!.getService(UUID_HUM_SERV).getCharacteristic(UUID_HUM_DATA)
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

    // KEY_DATA 정상적으로 실행됨...
    private fun testButton2() {
        var c: BluetoothGattCharacteristic? = null

        c = gatt!!.getService(UUID_KEY_SERV).getCharacteristic(UUID_KEY_DATA)

        gatt!!.setCharacteristicNotification(c, true)
        val descriptor = c.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"))

        if (descriptor != null) {
            descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            gatt!!.writeDescriptor(descriptor)
        }
    }

}