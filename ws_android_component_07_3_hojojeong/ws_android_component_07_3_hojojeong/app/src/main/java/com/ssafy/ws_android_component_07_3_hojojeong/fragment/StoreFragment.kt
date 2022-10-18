package com.ssafy.ws_android_component_07_3_hojojeong.fragment

import android.Manifest
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.ssafy.ws_android_component_07_3_hojojeong.MainActivity
import com.ssafy.ws_android_component_07_3_hojojeong.databinding.FragmentStoreBinding
import com.ssafy.ws_android_component_07_3_hojojeong.dto.StoreDto
import com.ssafy.ws_android_component_07_3_hojojeong.stuff.StuffActivity

private const val ARG_STORE = "storeList"
private const val ARG_STUFF = "stuffList"
private const val TAG = "StoreFragment 싸피"
class StoreFragment : Fragment() {
    private lateinit var binding: FragmentStoreBinding
    private lateinit var activity: Context
    private lateinit var storeItem: StoreDto

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeItem = StoreDto("싸피벅스", "010-1234-5678", "36.10830144", "128.41827")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: check")
        

        Log.d(TAG, "onViewCreated: $storeItem")
        binding.txtMainTitle.text = storeItem.title
        binding.txtMainNumber.text = storeItem.number
        binding.txtMainLat.text = storeItem.lat
        binding.txtMainLong.text = storeItem.long

        binding.txtMainTitle.setOnClickListener {
            val intent = Intent(requireContext(), StuffActivity::class.java)
            startActivity(intent)
        }

        binding.btnFmstoreSavenum.setOnClickListener{
            checkPermission()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                addContacts(
                    binding.txtMainTitle.text.toString(),
                    binding.txtMainNumber.text.toString()
                )
            } else {
                Toast.makeText(activity, "연락처 권한이 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            addContacts(binding.txtMainTitle.text.toString(),
                binding.txtMainNumber.text.toString())
        } else {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_CONTACTS)
        }
    }

    private fun addContacts(pharmacyName: String, number: String) {
        val p = ContentValues()
        p.put(ContactsContract.RawContacts.ACCOUNT_TYPE, "com.google")
        p.put(ContactsContract.RawContacts.ACCOUNT_NAME, "ssafy")
        val rawContact =
            requireContext().contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, p)
        val rawContactId = ContentUris.parseId(rawContact!!)

        //이름 넣는 거
        val value = ContentValues()
        value.apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
            put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, pharmacyName)
        }
        val valueInput = requireContext().contentResolver.insert(ContactsContract.Data.CONTENT_URI, value)

        val ppv = ContentValues()
        ppv.apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
            put(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
            put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
        }

        val ppvInput = requireContext().contentResolver.insert(ContactsContract.Data.CONTENT_URI, ppv)

        if(valueInput != null && ppvInput != null){
            Toast.makeText(activity, "연락처 저장이 완료되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

}