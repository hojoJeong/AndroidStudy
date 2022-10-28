package com.ssafy.wifidirect

import android.app.ProgressDialog
import android.content.Context
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager.PeerListListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.ListFragment

private const val TAG = "DeviceListFragment_싸피"
class DeviceListFragment : ListFragment(), PeerListListener {
    private val peers: MutableList<WifiP2pDevice> = ArrayList()
    var progressDialog: ProgressDialog? = null
    private lateinit var mContentView: View

    /**
     * @return this device
     */
    var device: WifiP2pDevice? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.listAdapter = WiFiPeerListAdapter(activity, R.layout.row_devices, peers)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mContentView = inflater.inflate(R.layout.device_list, null)
        return mContentView
    }

    /**
     * Initiate a connection with the peer.
     */

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        val device = listAdapter?.getItem(position) as WifiP2pDevice
        (activity as DeviceActionListener).showDetails(device)
    }

    /**
     * Array adapter for ListFragment that maintains WifiP2pDevice list.
     */
    private inner class WiFiPeerListAdapter
    /**
     * @param context
     * @param textViewResourceId
     * @param objects
     */(
        context: Context?, textViewResourceId: Int,
        private val items: List<WifiP2pDevice>
    ) : ArrayAdapter<WifiP2pDevice?>(context!!, textViewResourceId, items) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val v:View
            if (convertView == null) {
                val vi = activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                v = vi.inflate(R.layout.row_devices, null)
            }else{
                v = convertView
            }

            val device = items[position]
            if (device != null) {
                val top = v.findViewById(R.id.device_name) as TextView
                val bottom = v.findViewById(R.id.device_details) as TextView
                if (top != null) {
                    top.text = device.deviceName
                }
                if (bottom != null) {
                    bottom.text = getDeviceStatus(device.status)
                }
            }
            return v
        }
    }

    /**
     * Update UI for this device.
     *
     * @param device WifiP2pDevice object
     */
    fun updateThisDevice(device: WifiP2pDevice) {
        this.device = device
        var view = mContentView.findViewById(R.id.my_name) as TextView
        view.text = device.deviceName
        view = mContentView.findViewById(R.id.my_status)
        view.text = getDeviceStatus(device.status)
    }

    override fun onPeersAvailable(peerList: WifiP2pDeviceList) {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
        peers.clear()
        peers.addAll(peerList.deviceList)
        (listAdapter as WiFiPeerListAdapter).notifyDataSetChanged()
        if (peers.size == 0) {
            Log.d(TAG, "No devices found")
            return
        }
    }

    fun clearPeers() {
        peers.clear()
        (listAdapter as WiFiPeerListAdapter).notifyDataSetChanged()
    }

    /**
     *
     */
    fun onInitiateDiscovery() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
        progressDialog = ProgressDialog.show(
            activity, "Press back to cancel", "finding peers", true,
            true
        ) { }
    }

    /**
     * An interface-callback for the activity to listen to fragment interaction
     * events.
     */
    interface DeviceActionListener {
        fun showDetails(device: WifiP2pDevice?)
        fun cancelDisconnect()
        fun connect(config: WifiP2pConfig?)
        fun disconnect()
    }

    companion object {
        private fun getDeviceStatus(deviceStatus: Int): String {
            Log.d(TAG, "Peer status :$deviceStatus")
            return when (deviceStatus) {
                WifiP2pDevice.AVAILABLE -> "Available"
                WifiP2pDevice.INVITED -> "Invited"
                WifiP2pDevice.CONNECTED -> "Connected"
                WifiP2pDevice.FAILED -> "Failed"
                WifiP2pDevice.UNAVAILABLE -> "Unavailable"
                else -> "Unknown"
            }
        }
    }
}
