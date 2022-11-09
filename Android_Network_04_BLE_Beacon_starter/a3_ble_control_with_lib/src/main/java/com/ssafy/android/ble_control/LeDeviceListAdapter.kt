package com.ssafy.android.ble_control

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
        val deviceName:String? = device.name

        if (deviceName != null && deviceName.length > 0) {
            viewHolder.deviceName!!.text = deviceName
            viewHolder.deviceAddress!!.text = device.address
        } else {
            viewHolder.deviceName!!.text = "unknown_device"
        }
        return view!!
    }

    internal class ViewHolder {
        var deviceName: TextView? = null
        var deviceAddress: TextView? = null
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