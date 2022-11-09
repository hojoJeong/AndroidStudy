package com.ssafy.android.beacon_scanner

import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.content.DialogInterface
import android.content.Intent
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
import android.content.BroadcastReceiver
import android.view.WindowManager
import android.bluetooth.le.ScanSettings
import android.bluetooth.le.ScanFilter
import android.os.Looper
import android.bluetooth.le.ScanCallback
import android.content.SharedPreferences
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
import android.util.Log
import com.ssafy.android.MyApplication
import com.ssafy.android.beacon_scanner.databinding.ActivityMyBinding

private const val TAG = "EurekaActivity_싸피"

@RequiresApi(api = Build.VERSION_CODES.O)
@SuppressLint("MissingPermission")
class EurekaActivity : BaseActivity() {
    private lateinit var binding: ActivityMyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "EurekaActivity.onCreate: EurekaActivity 실행!!")
        binding = ActivityMyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )

        binding.textView.text = "EurekaActivity 실행!!"
    }

}