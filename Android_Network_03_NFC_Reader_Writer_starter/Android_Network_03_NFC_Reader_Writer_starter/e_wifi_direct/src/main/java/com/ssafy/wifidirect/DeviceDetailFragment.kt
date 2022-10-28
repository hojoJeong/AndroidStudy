/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ssafy.wifidirect

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pInfo
import android.net.wifi.p2p.WifiP2pManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import java.io.*
import java.net.ServerSocket


/**
 * A fragment that manages a particular peer and allows interaction with device
 * i.e. setting up network connection and transferring data.
 */
private const val TAG = "DeviceDetailFragment_싸피"
class DeviceDetailFragment : Fragment(), WifiP2pManager.ConnectionInfoListener {
    private lateinit var mContentView: View
    private lateinit var device: WifiP2pDevice
    private lateinit var info: WifiP2pInfo
    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mContentView = inflater.inflate(R.layout.device_detail, null)

        //Connect 버튼
        mContentView.findViewById<View>(R.id.btn_connect).setOnClickListener {
            val config = WifiP2pConfig()
            config.deviceAddress = device.deviceAddress
            // wps : Wi-Fi Protected Setup information, PBC : Push button configuration
            // 수락하기 버튼을 보여줄것이다.(키패드, 핀코드 등 가능한 듯)
            config.wps.setup = WpsInfo.PBC
            if ((progressDialog != null && progressDialog!!.isShowing)) {
                progressDialog!!.dismiss()
            }
            progressDialog = ProgressDialog.show(
                activity,
                "Press back to cancel",
                ("Connecting to :"
                        + device.deviceAddress),
                true,
                true // new DialogInterface.OnCancelListener() {
                //
                // @Override
                // public void onCancel(DialogInterface dialog) {
                // ((DeviceActionListener)
                // getActivity()).cancelDisconnect();
                // }
                // }
            )
            //WifiDirect의 connect 함수 호출
            (activity as DeviceListFragment.DeviceActionListener).connect(config)
        }
        //Disconnect 버튼
        mContentView.findViewById<View>(R.id.btn_disconnect).setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(v: View) {
                    (activity as DeviceListFragment.DeviceActionListener).disconnect()
                }
            })

        //Gallary 버튼
        mContentView.findViewById<View>(R.id.btn_start_client).setOnClickListener {
            // Allow user to pick an image from Gallery or other
            // registered apps
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, CHOOSE_FILE_RESULT_CODE)
        }
        return mContentView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // User has picked an image. Transfer it to group owner i.e peer using
        // FileTransferService.
        data?.let { it ->
            val uri: Uri? = it.data
            val statusText: TextView = mContentView.findViewById<View>(R.id.status_text) as TextView
            statusText.text = "Sending: $uri"
            Log.d(TAG, "Intent----------- $uri")

            activity?.startService(Intent(activity, FileTransferService::class.java).also { intent ->
                intent.action = FileTransferService.ACTION_SEND_FILE
                intent.putExtra(FileTransferService.EXTRAS_FILE_PATH, uri.toString())
                intent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,info.groupOwnerAddress.hostAddress)
                intent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_PORT, 8988)
            })
        }
    }

    override fun onConnectionInfoAvailable(info: WifiP2pInfo) {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
        this.info = info
        this.requireView().visibility = View.VISIBLE

        // The owner IP is now known.
        var view: TextView = mContentView.findViewById<View>(R.id.group_owner) as TextView
        view.text = (resources.getString(R.string.group_owner_text)
                + (if ((info.isGroupOwner == true)) resources.getString(R.string.yes) else resources.getString(R.string.no)))

        // InetAddress from WifiP2pInfo struct.
        view = mContentView.findViewById<View>(R.id.device_info) as TextView
        view.text = ("Group Owner IP - " + info.groupOwnerAddress.hostAddress)

        // After the group negotiation, we assign the group owner as the file
        // server. The file server is single threaded, single connection server
        // socket.
        if (info.groupFormed && info.isGroupOwner) {
            activity?.let {
                FileServerAsyncTask( it, mContentView.findViewById(R.id.status_text)).execute()
            }
        } else if (info.groupFormed) {
            // The other device acts as the client. In this case, we enable the
            // get file button.
            mContentView.findViewById<View>(R.id.btn_start_client).visibility = View.VISIBLE
            (mContentView.findViewById<View>(R.id.status_text) as TextView).text = resources.getString(R.string.client_text)
        }

        // hide the connect button
        mContentView.findViewById<View>(R.id.btn_connect).visibility = View.GONE
    }

    /**
     * Updates the UI with device data
     *
     * @param device
     * the device to be displayed
     */
    fun showDetails(device: WifiP2pDevice) {
        this.device = device
        this.requireView().visibility = View.VISIBLE

        var view: TextView = mContentView.findViewById<View>(R.id.device_address) as TextView
        view.text = device.deviceAddress
        view = mContentView.findViewById<View>(R.id.device_info) as TextView
        view.text = device.toString()
    }

    /**
     * Clears the UI fields after a disconnect or direct mode disable operation.
     */
    fun resetViews() {
        mContentView.findViewById<View>(R.id.btn_connect).visibility = View.VISIBLE
        var view: TextView = mContentView.findViewById<View>(R.id.device_address) as TextView
        view.setText(R.string.empty)
        view = mContentView.findViewById<View>(R.id.device_info) as TextView
        view.setText(R.string.empty)
        view = mContentView.findViewById<View>(R.id.group_owner) as TextView
        view.setText(R.string.empty)
        view = mContentView.findViewById<View>(R.id.status_text) as TextView
        view.setText(R.string.empty)
        mContentView.findViewById<View>(R.id.btn_start_client).visibility = View.GONE
        view.visibility = View.GONE
    }

    /**
     * A simple server socket that accepts connection and writes some data on
     * the stream.
     */
    class FileServerAsyncTask(private val context: Context, private var statusText: TextView) :
        AsyncTask<Void?, Void?, String?>() {

        override fun doInBackground(vararg params: Void?): String? {
            try {
                val serverSocket = ServerSocket(8988)
                Log.d(TAG, "Server: Socket opened")
                val client = serverSocket.accept()

                val file = File(
                    context.getExternalFilesDir("received"),
                    "wifip2pshared-" + System.currentTimeMillis()
                            + ".jpg"
                )

                val dirs = File(file.parent!!)
                if (!dirs.exists()) dirs.mkdirs()
                file.createNewFile()
                Log.d( TAG,"server: copying files $file")

                val inputstream = client.getInputStream()
                copyFile(inputstream, FileOutputStream(file))
                serverSocket.close()
                Log.d( TAG,"server: copied. socket closed. $file")
                return file.absolutePath
            } catch (e: IOException) {
                Log.e(TAG, (e.message)!!)
                return null
            }
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
        override fun onPostExecute(result: String?) {
            result?.let {
                Log.d( TAG,"server: File copied. - $result")
                statusText.text = "File copied - $result"

                val recvFile = File(result)
                val fileUri = FileProvider.getUriForFile(context,"com.ssafy.wifidirect.fileprovider", recvFile)

                context.startActivity( Intent().also { intent ->
                    intent.action = Intent.ACTION_VIEW
                    intent.setDataAndType(fileUri, "image/*")
                    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                })

                //갤러리로 보내기
//                MediaStore.Images.Media.insertImage(
//                    context.contentResolver,
//                    recvFile.absolutePath,
//                    recvFile.name, //name
//                    recvFile.name  //description
//                )

                Toast.makeText(context, "파일전송 완료 :  $result", Toast.LENGTH_SHORT).show()
            }
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 */
        override fun onPreExecute() {
            statusText.text = "Opening a server socket"
        }


    }

    companion object {
        protected val CHOOSE_FILE_RESULT_CODE = 20
        fun copyFile(input: InputStream, output: OutputStream): Boolean {
            val buf = ByteArray(1024*1024)
            var len: Int
            try {
                while ((input.read(buf).also { len = it }) != -1) {
                    output.write(buf, 0, len)
                }
                output.close()
                input.close()
            } catch (e: IOException) {
                Log.d(TAG, e.toString())
                return false
            }
            return true
        }
    }
}