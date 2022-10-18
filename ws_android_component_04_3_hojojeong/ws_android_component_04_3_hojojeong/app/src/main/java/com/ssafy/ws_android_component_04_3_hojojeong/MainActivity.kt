package com.ssafy.ws_android_component_04_3_hojojeong

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.ws_android_component_04_3_hojojeong.databinding.ActivityMainBinding
import com.ssafy.ws_android_component_04_3_hojojeong.dto.StoreDto
import com.ssafy.ws_android_component_04_3_hojojeong.dto.StuffDto
import com.ssafy.ws_android_component_04_3_hojojeong.fragment.MainFragment
import com.ssafy.ws_android_component_04_3_hojojeong.fragment.StoreFragment
import com.ssafy.ws_android_component_04_3_hojojeong.service.BoundService

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var boundService : BoundService
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected: ")
            val binder = service as BoundService.MyLocalBinder
            boundService = binder.getService()
            isBound = true
            initData()
            initViewPager()
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

    private fun initData(){
        if(boundService.selectAll().size == 0){
            boundService.insert(StuffDto("사과", 10))
            boundService.insert(StuffDto("참외", 11))
        }
    }

    private fun initViewPager(){
        binding.viewpager.adapter = PagerAdapter(supportFragmentManager, lifecycle)
        binding.viewpager.registerOnPageChangeCallback(PageChangeCallback())
        binding.bottomNav.setOnItemSelectedListener { navigationSelected(it)}
    }

    private inner class PagerAdapter(fm: FragmentManager, lc: Lifecycle): FragmentStateAdapter(fm, lc) {
        override fun getItemCount() = 2
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> MainFragment()
                1 -> StoreFragment()
                else -> error("no such position: $position")
            }
        }
    }

    private inner class PageChangeCallback: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.bottomNav.selectedItemId = when (position) {
                0 -> R.id.info_detail
                1 -> R.id.info_store

                else -> error("no such position: $position")
            }
        }
    }

    private fun navigationSelected(item: MenuItem): Boolean {
        val checked = item.setChecked(true)
        when (checked.itemId) {
            R.id.info_detail -> {
                binding.viewpager.currentItem = 0
                return true
            }
            R.id.info_store -> {
                binding.viewpager.currentItem = 1
                return true
            }
        }
        return false
    }
}