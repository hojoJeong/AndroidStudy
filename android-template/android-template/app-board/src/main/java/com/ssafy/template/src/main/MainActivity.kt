package com.ssafy.template.src.main

import android.os.Bundle
import com.ssafy.template.R
import com.ssafy.template.config.BaseActivity
import com.ssafy.template.databinding.ActivityMainBinding
import com.ssafy.template.src.main.home.HomeFragment
import com.ssafy.template.src.main.myPage.MyPageFragment
import com.ssafy.template.src.main.search.SearchFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.menu_main_btm_nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                }
                R.id.menu_main_btm_nav_my_page -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MyPageFragment())
                        .commitAllowingStateLoss()
                }
                R.id.menu_main_btm_nav_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                }
            }
            true
        }
    }
}