package com.ssafy.android.beacon_basic

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
import android.content.Context
import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice
import uk.co.alt236.bluetoothlelib.device.mfdata.IBeaconManufacturerData
import android.content.DialogInterface
import android.widget.BaseAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.ArrayList

@SuppressLint("MissingPermission")
class LeDeviceListAdapter(context: Context?) : BaseAdapter() {
    private val mLeDevices: ArrayList<BluetoothDevice> = ArrayList()
    private val mInflator: LayoutInflater

    init {
        mInflator = LayoutInflater.from(context)
    }

    fun addDevice(device: BluetoothDevice?) {
        if (!mLeDevices.contains(device)) {
            mLeDevices.add(device!!)
        }
    }

    fun getDevice(position: Int): BluetoothDevice {
        return mLeDevices[position]
    }

    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View? {
        var view = convertView
        val viewHolder: ViewHolder

        // General ListView optimization code.
        if (view == null) {
            view = mInflator.inflate(R.layout.devicelistitem, null)
            viewHolder = ViewHolder()
            viewHolder.deviceAddress = view.findViewById(R.id.device_address)
            viewHolder.deviceName = view.findViewById(R.id.device_name)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        val device = mLeDevices[i]
        viewHolder.deviceName.text = device.name ?: "unknown_device"
        viewHolder.deviceAddress.text = device.address ?: "unknown_address"

        return view!!
    }

    internal class ViewHolder {
        lateinit var deviceName: TextView
        lateinit var deviceAddress: TextView
    }

    fun clear() {
        mLeDevices.clear()
    }

    override fun getCount(): Int {
        return mLeDevices.size
    }

    override fun getItem(i: Int): Any {
        return mLeDevices[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }
}