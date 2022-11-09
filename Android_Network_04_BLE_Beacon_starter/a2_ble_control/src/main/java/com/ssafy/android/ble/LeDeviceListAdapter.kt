package com.ssafy.android.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


@SuppressLint("MissingPermission")
class LeDeviceListAdapter(context: Context?) : BaseAdapter() {
    private val mLeDevices: ArrayList<BluetoothDevice> = ArrayList()
    private val mInflator: LayoutInflater

    init {
        mInflator = LayoutInflater.from(context)
    }

    fun addDevice(device: BluetoothDevice) {
        if (!mLeDevices.contains(device)) {
            mLeDevices.add(device)
        }
    }

    fun getDevice(position: Int): BluetoothDevice {
        return mLeDevices[position]
    }

    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
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