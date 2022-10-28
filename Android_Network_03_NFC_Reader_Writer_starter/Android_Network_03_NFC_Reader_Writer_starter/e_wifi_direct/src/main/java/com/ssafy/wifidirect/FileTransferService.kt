package com.ssafy.wifidirect

import android.app.IntentService
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket


/**
 * A service that process each file transfer request i.e Intent by opening a
 * socket connection with the WiFi Direct Group Owner and writing the file
 */
/** IntentService는 Service를 상속하며, 새로운 Thread를 만들어서 동작한다. API 30에서 deprecated됨.
 * 대체하여 WorkManager 사용이 권장되나, AsyncTask와 마찬가지로 상당히 많이 볼 수 있다.
 * Handler를 사용하여 대체할 수도 있다.
 * Service와 마찬가지로 Intent를 만들어서 startService로 호출된다.
 * 실행되면 새로운 Thread에서 onHandlerIntent 메서드가 불린다.
 */
private const val TAG = "FileTransferService_싸피"
class FileTransferService : IntentService {
    constructor(name: String?) : super(name)
    constructor() : super("FileTransferService")

    /*
     * (non-Javadoc)
     * @see android.app.IntentService#onHandleIntent(android.content.Intent)
     */
    override fun onHandleIntent(intent: Intent?) {
        val context: Context = applicationContext
        if (intent!!.action == ACTION_SEND_FILE) {
            val fileUri = intent.extras!!.getString(EXTRAS_FILE_PATH)
            val host = intent.extras!!.getString(EXTRAS_GROUP_OWNER_ADDRESS)
            val socket = Socket()
            val port = intent.extras!!.getInt(EXTRAS_GROUP_OWNER_PORT)
            try {
                Log.d(TAG, "Opening client socket - ")
                socket.bind(null)
                socket.connect(InetSocketAddress(host, port), SOCKET_TIMEOUT)
                Log.d(TAG, "Client socket - " + socket.isConnected)
                val stream: OutputStream = socket.getOutputStream()
                val cr: ContentResolver = context.contentResolver

                var input = cr.openInputStream(Uri.parse(fileUri))
                Log.d(TAG, "input: $input , fileUrl : $fileUri")

                if (input != null) {
                    DeviceDetailFragment.copyFile(input, stream)
                }
                Log.d(TAG, "Client: Data written")
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
            } finally {
                if (socket != null) {
                    if (socket.isConnected) {
                        try {
                            socket.close()
                        } catch (e: IOException) {
                            // Give up
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val SOCKET_TIMEOUT = 5000
        const val ACTION_SEND_FILE = "com.ssafy.wifidirect.SEND_FILE"
        const val EXTRAS_FILE_PATH = "file_url"
        const val EXTRAS_GROUP_OWNER_ADDRESS = "go_host"
        const val EXTRAS_GROUP_OWNER_PORT = "go_port"
    }
}
