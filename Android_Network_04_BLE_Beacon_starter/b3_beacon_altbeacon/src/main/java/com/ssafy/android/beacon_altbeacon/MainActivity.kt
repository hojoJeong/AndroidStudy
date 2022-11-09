package com.ssafy.android.beacon_altbeacon

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ssafy.android.beacon_altbeacon.databinding.ActivityMainBinding
import com.ssafy.android.util.CheckPermission
import org.altbeacon.beacon.*
import java.util.*

private const val TAG = "MainActivity_싸피"

//android beacon library를 사용하여 개발한다.
//https://github.com/AltBeacon/android-beacon-library
@RequiresApi(api = Build.VERSION_CODES.O)
@SuppressLint("MissingPermission")
class MainActivity : AppCompatActivity() {
    companion object {
        private const val PERMISSION_REQUEST_CODE = 8
        private const val BEACON_UUID = "fda50693-a4e2-4fb1-afcf-c6eb07647825"
        private const val BEACON_MAJOR = "10004"
        private const val BEACON_MINOR = "54480"
        private const val BLUETOOTH_ADDRESS = "00:81:F9:44:39:58"
        private const val BEACON_DISTANCE = 5.0
    }

    private lateinit var beaconManager: BeaconManager
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var checkPermission: CheckPermission

    private var bluetoothAdapter: BluetoothAdapter? = null

    private val runtimePermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_CONNECT
    )

    // Beacon의 Region 설정
    // 비교데이터들로, 설치 지역이 어딘지 판단하기 위한 데이터.
    //estimote : apple, eddystone : google
//    private val region = Region(
//        "estimote",
//        Identifier.parse(BEACON_UUID),
//        Identifier.parse(BEACON_MAJOR),
//        Identifier.parse(BEACON_MINOR)
//    )
    private val region = Region(
        "estimote",
        listOf(Identifier.parse(BEACON_UUID),
        Identifier.parse(BEACON_MAJOR),
        Identifier.parse(BEACON_MINOR)),
        BLUETOOTH_ADDRESS
    )
    private var eventPopUpAble = true
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //BeaconManager 지정
        beaconManager = BeaconManager.getInstanceForApplication(this)
        //		estimo 비컨을 분석 하도록 하기 위하여 beacon parser 오프셋, 버전등을 setLayout으로 지정한다.
//		m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24
//		설명: 0 ~ 1 바이트는 제조사를 나타내는 필드로 파싱하지 않는다.
//		2~3 바이트는 0x02, 0x15 이다.
//		4~19 바이트들을 첫번째 ID로 매핑한다.(UUID)
//				20~21 바이트들을 두번째 ID로 매핑한다.(Major)
//				22-23 바이트들을 세번째 ID로 매핑한다.(Minor)
//				24~24 바이트들을 txPower로 매핑한다.
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))
        bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        checkPermission = CheckPermission(this)
        if (bluetoothAdapter == null || !bluetoothAdapter!!.isEnabled) {
            Toast.makeText(this, "블루투스 기능을 확인해 주세요.", Toast.LENGTH_SHORT).show()
            val bleIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(bleIntent, 1)
        }

        if (!checkPermission.runtimeCheckPermission(this, *runtimePermissions)) {
            ActivityCompat.requestPermissions(this, runtimePermissions, PERMISSION_REQUEST_CODE)
        } else { //이미 전체 권한이 있는 경우
            startScan()
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
                startScan()
            } else {
                checkPermission.requestPermission()
            }
        }
    }

    private fun startScan() {

        // 리전에 비컨이 있는지 없는지..정보를 받는 클래스 지정
        beaconManager.addMonitorNotifier(monitorNotifier)
        beaconManager.startMonitoring(region)

        //detacting되는 해당 region의 beacon정보를 받는 클래스 지정.
        beaconManager.addRangeNotifier(rangeNotifier)
        beaconManager.startRangingBeacons(region)
    }

    //모니터링 결과를 처리할 Notifier를 지정.
    // region에 해당하는 beacon 유무 판단
    var monitorNotifier: MonitorNotifier = object : MonitorNotifier {
        override fun didEnterRegion(region: Region) { //발견 함.
            Log.d(TAG, "I just saw an beacon for the first time!")
        }

        override fun didExitRegion(region: Region) { //발견 못함.
            Log.d(TAG, "I no longer see an beacon")
        }

        override fun didDetermineStateForRegion(state: Int, region: Region) { //상태변경
            Log.d(TAG, "I have just switched from seeing/not seeing beacons: $state")
        }
    }

    //매초마다 해당 리전의 beacon 정보들을 collection으로 제공받아 처리한다.
    var rangeNotifier: RangeNotifier = object : RangeNotifier {
        override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
            beacons?.run{
                if (isNotEmpty()) {
                    forEach { beacon ->
                        val msg = "distance: " + beacon.distance
                        // 사정거리 내에 있을 경우 이벤트 표시 다이얼로그 팝업
                        if (beacon.distance <= BEACON_DISTANCE) {
                            Log.d(TAG, "didRangeBeaconsInRegion: distance 이내.")
                            val txt = binding.textView.text.toString()
                            binding.textView.text = "${Date().toString().substring(0, 20)}$msg \n\n $txt"
                        } else {
                            Log.d(TAG, "didRangeBeaconsInRegion: distance 이외.")
                            eventPopUpAble = true
                        }
                        Log.d( TAG,"distance: " + beacon.distance + " id:" + beacon.id1 + "/" + beacon.id2 + "/" + beacon.id3)
                    }
                }
                if (isEmpty()) {
                    Log.d(TAG, "didRangeBeaconsInRegion: 비컨을 찾을 수 없습니다.")
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        //		finish();
    }

    // destroy에서 beacon scan을 중지 시킨다.
    // beacon scan을 중지 하지 않으면 일정 시간 이후 다시 scan이 가능하다.
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
        beaconManager.stopMonitoring(region)
        beaconManager.stopRangingBeacons(region)
    }

}