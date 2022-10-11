package com.ssafy.ws_android_component_02_3_hojojeong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.ssafy.ws_android_component_02_3_hojojeong.databinding.ActivityMainBinding
import com.ssafy.ws_android_component_02_3_hojojeong.dto.StoreDto
import com.ssafy.ws_android_component_02_3_hojojeong.dto.StuffDto
import com.ssafy.ws_android_component_02_3_hojojeong.fragment.MainFragment
import com.ssafy.ws_android_component_02_3_hojojeong.fragment.StoreFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var stuffList: ArrayList<StuffDto> = arrayListOf()
    private var storeList: ArrayList<StoreDto> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initTabLayoutevent()
        initFragment()


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
        stuffList.add(StuffDto("사과", 10))
        stuffList.add(StuffDto("참외", 11))

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