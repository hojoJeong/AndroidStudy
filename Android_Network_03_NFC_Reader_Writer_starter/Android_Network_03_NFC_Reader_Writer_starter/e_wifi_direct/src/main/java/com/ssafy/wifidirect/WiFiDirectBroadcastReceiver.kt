package com.ssafy.wifidirect

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.PeerListListener
import android.util.Log

private const val TAG = "WiFiDirectBroadcast_싸피"
class WiFiDirectBroadcastReceiver(
    private val manager: WifiP2pManager, private val channel: WifiP2pManager.Channel,
    private val activity: WiFiDirectActivity
) : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    @Suppress("DEPRECATION")
    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                // UI update to indicate wifi p2p status.
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                Log.d(TAG, "P2P state changed - $state")
                when(state){
                    WifiP2pManager.WIFI_P2P_STATE_ENABLED -> {
                        // Wifi Direct mode is enabled
                        activity.setIsWifiP2pEnabled(true)
                    }
                    else -> {
                        // Wi-Fi P2P is not enabled
                        activity.setIsWifiP2pEnabled(false)
                        activity.resetData()
                    }
                }
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                // discoverPeers()의 결과로 시스템에서 broadcast

                // request available peers from the wifi p2p manager. This is an
                // asynchronous call and the calling activity is notified with a
                // callback on PeerListListener.onPeersAvailable()
                // requestPeers의 결과로 PeerListListener.onPeersAvailable() 콜백.
                manager.requestPeers(channel, activity.supportFragmentManager.findFragmentById(R.id.frag_list) as PeerListListener)
                Log.d(TAG, "P2P peers changed")

            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                //manager.connect의 결과로 시스템에서 broadcast
                if (manager == null) {
                    return
                }
                val networkInfo:NetworkInfo? = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO) as NetworkInfo?

                Log.d(TAG, "P2P connection changed : networkInfo.isConnected :${networkInfo?.isConnected}")
                if (networkInfo?.isConnected == true) {

                    // we are connected with the other device, request connection
                    // info to find group owner IP
                    val fragment: DeviceDetailFragment = activity.supportFragmentManager.findFragmentById(R.id.frag_detail) as DeviceDetailFragment
                    manager.requestConnectionInfo(channel, fragment)
                    //WifiP2pManager.ConnectionInfoListener.onConnectionInfoAvailable 이 콜백.
                } else {
                    // It's a disconnect
                    activity.resetData()
                }

            }
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                val fragment = activity.supportFragmentManager.findFragmentById(R.id.frag_list) as DeviceListFragment
                fragment.updateThisDevice(
                    (intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE) as WifiP2pDevice?)!!
                )
                Log.d(TAG, "P2P this device changed")
            }
        }
    }
}
