package com.ssafy.android.beacon_scanner

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.CanceledException
import android.bluetooth.le.*
import android.content.*
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.ssafy.android.util.ForegroundDetector
import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice
import uk.co.alt236.bluetoothlelib.device.mfdata.IBeaconManufacturerData

private const val TAG = "MyBeaconScanner_싸피"

@RequiresApi(api = Build.VERSION_CODES.O)
@SuppressLint("MissingPermission")
//Context 와 발견했을때, 이동할 Class, Notification에 지정할 class를 입력.
class MyBeaconScanner (private val context: Context, private val scanner: BluetoothLeScanner?, private val target: Class<*>) {
    fun startScanning() {
        Log.d(TAG, "BeaconScanner.startScanning: ")
        //scan settings.
        val scanSettings =
            ScanSettings.Builder()
//				.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
                .build()

        //scan filter
        val scanFilters: MutableList<ScanFilter> = ArrayList()
        val scanFilter = ScanFilter.Builder()
            .setDeviceAddress("00:81:F9:E2:69:F8") //mac address ex) 00:00:00:00:00:00
            .build()
        scanFilters.add(scanFilter)
        scanner!!.startScan(scanFilters, scanSettings, scanCallback)
    }

    var handler = Handler(Looper.getMainLooper())
    fun destroy() {
        Log.d(TAG, "BeaconScanner.destroy: ")
        scanner?.stopScan(scanCallback)

        // postDelayed를 호출할때 주의
        /*** handler에 있는 모든 Thread와 Message를 비워준다.  */
        handler.removeCallbacksAndMessages(null)
    }

    // ======================IBeacon 용 LeScanCallback=======================
    private val scanCallback: ScanCallback = object : ScanCallback() {
        private var tx = 0
        private var major = 0
        private var minor = 0
        private var distance = 0.0
        var preferences: SharedPreferences? = null
        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.d(TAG, "ScanCallback.onScanFailed: ")
        }

        override fun onScanResult(callbackType: Int, result: ScanResult) {
            processResult(result)
        }

        private fun processResult(result: ScanResult) {
            val device = result.device
            val rssi = result.rssi
            val record = result.scanRecord
            Log.d(TAG, "device >>>> $device updated.")
            val deviceLe = BluetoothLeDevice(device, rssi, record!!.bytes, System.currentTimeMillis())

            val iBeaconData = IBeaconManufacturerData(deviceLe)
            tx = iBeaconData.calibratedTxPower
            major = iBeaconData.major
            minor = iBeaconData.minor
            // distance = (float) rssi / (float) tx;
            distance = getNewAccuracy(tx, rssi.toDouble())
            Log.d(TAG, "rssi : $rssi, tx : $tx, =====거리 : $distance")

            //거리가 x미터 이내라면 화면 or 알림 띄우기.
            if (distance <= 5.0) {
                //scanner가 동작할때마다 계속 띄우게 되므로,
                //최초 호출 이후 1일/1시간등 특정시간 이후면 다시 Scan하도록 preference를 활용하여 적용.

                //최초 시간 기록.
                preferences = context.getSharedPreferences("beacon_key", Context.MODE_PRIVATE)
                val lastTime = preferences!!.getLong("lastTime", 0)

                //30초 마다 scan.
                val delayTime = (1000 * 10).toLong() //1_000 * 60 * 60

                // 최초 호출이거나, 일정시간 지났을 경우
                if (lastTime == 0L || System.currentTimeMillis() - lastTime > delayTime) {
                    Log.d(TAG, "새로운 호출")
                    writePreference() // 시간기록
                    launchActivityOrNotification() //시간기록 후 Activity or 알림 전송
                    pauseScan(delayTime) //scan 일시중지
                }
            }
        }

        private fun writePreference() {
            preferences!!.edit()
                .putLong("lastTime", System.currentTimeMillis())
                .apply()
        }

        private fun pauseScan(delayTime: Long) {
            Log.d(TAG, "BeaconScanner.pauseScan: ")
            handler.post { scanner!!.stopScan(this) }
            handler.postDelayed({
                Log.d(TAG, "BeaconScanner.restartScan: ")
                startScanning()
            }, delayTime) // 종료하더라도 대기타고 있을 수 있음. ***반드시 종료시켜줘야 한다.***
        }

        // scan성공했을때 알림주는 처리
        private fun launchActivityOrNotification() {
            // 1.Activity 실행
            if (ForegroundDetector.instance.isForeground) {
				startActivity();
            }

            // 2.PendingIntent로 실행.
//            startPendingIntent()

            // 3.Notification을 발송
//			sendNotification();

            // 4.Broadcast를 발송
//			sendBroadcast();
        }
    }

    private fun startActivity() {
        val intent = Intent(context, target)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    private fun startPendingIntent() {
        val i = Intent(context, target)
        val p = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_MUTABLE)
        p.send()
    }

    private fun sendNotification() {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent = Intent(context, target)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val CHANNEL_ID = "beacon_service_notification_channel"
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Beacon Service Notification Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
            NotificationCompat.Builder(context, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(context)
        }
        builder.setSmallIcon(R.drawable.ic_outline_notifications_24)
            .setContentTitle("Beacon Alert")
            .setContentText("비컨을 발견했습니다.")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        manager.notify(101, builder.build())
    }

    private fun sendBroadcast() {
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent()
        intent.setClass(context, BeaconReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 111, intent, PendingIntent.FLAG_IMMUTABLE)
        manager[AlarmManager.ELAPSED_REALTIME_WAKEUP, 0] = pendingIntent // 0초 후 발송.
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