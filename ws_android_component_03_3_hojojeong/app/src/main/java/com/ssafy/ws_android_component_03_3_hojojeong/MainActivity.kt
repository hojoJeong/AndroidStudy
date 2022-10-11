package com.ssafy.ws_android_component_03_3_hojojeong

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.google.android.material.tabs.TabLayout
import com.ssafy.ws_android_component_03_3_hojojeong.databinding.ActivityMainBinding
import com.ssafy.ws_android_component_03_3_hojojeong.dto.StoreDto
import com.ssafy.ws_android_component_03_3_hojojeong.dto.StuffDto
import com.ssafy.ws_android_component_03_3_hojojeong.fragment.MainFragment
import com.ssafy.ws_android_component_03_3_hojojeong.fragment.StoreFragment
import com.ssafy.ws_android_component_03_3_hojojeong.service.BoundService

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var boundService : BoundService
    private var stuffList: ArrayList<StuffDto> = arrayListOf()
    private var storeList: ArrayList<StoreDto> = arrayListOf()
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected: ")
            val binder = service as BoundService.MyLocalBinder
            boundService = binder.getService()
            isBound = true
            initData()
            initTabLayoutevent()
            initFragment()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: ")
            isBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, BoundService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
    }


    private fun initFragment(){
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment.newInstance(storeList)).commit()
        }
    }

    private fun initData(){
        storeList.add(StoreDto("싸피벅스", "010-1234-5678", "36.10830144", "128.41827"))
        boundService.insert(StuffDto("사과", 10))
        boundService.insert(StuffDto("참외", 11))
    }

    private fun initTabLayoutevent(){
        binding.tabLayoutMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MainFragment.newInstance(storeList)).commit()

                    1 ->  supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, StoreFragment.newInstance(stuffList, storeList)).commit()
                }            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}