package com.ssafy.android.util

import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.app.Activity
import android.os.Bundle
import com.ssafy.android.util.ForegroundDetector
import androidx.annotation.RequiresApi
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.android.beacon_scanner.BaseActivity
import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner
import android.widget.Toast
import android.view.WindowManager
import android.bluetooth.le.ScanSettings
import android.bluetooth.le.ScanFilter
import android.os.Looper
import android.bluetooth.le.ScanCallback
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanRecord
import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice
import uk.co.alt236.bluetoothlelib.device.mfdata.IBeaconManufacturerData
import android.app.PendingIntent
import android.app.PendingIntent.CanceledException
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import android.app.NotificationChannel
import com.ssafy.android.beacon_scanner.R
import android.app.AlarmManager
import android.content.*
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.ssafy.android.MyApplication

class CheckPermission(private val context: Context) {
    fun runtimeCheckPermission(context: Context?, vararg permissions: String?): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission( context, permission!! ) != PackageManager.PERMISSION_GRANTED ) {
                    return false
                }
            }
        }
        return true
    }

    fun requestPermission() {
        val alertDialog = AlertDialog.Builder( context )
        alertDialog.setTitle("권한이 필요합니다.")
        alertDialog.setMessage("설정으로 이동합니다.")
        alertDialog.setPositiveButton("확인") { dialogInterface, i -> // 안드로이드 버전에 따라 다를 수 있음.
            val intent =
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + context.packageName))
            context.startActivity(intent)
            dialogInterface.cancel()
        }
        alertDialog.setNegativeButton("취소") { dialogInterface, i -> dialogInterface.cancel() }
        alertDialog.show()
    }
}