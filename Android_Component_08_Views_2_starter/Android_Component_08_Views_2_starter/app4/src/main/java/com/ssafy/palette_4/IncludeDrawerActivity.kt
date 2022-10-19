package com.ssafy.palette_4

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.ssafy.palette_4.databinding.IncludeDrawerBinding

class IncludeDrawerActivity : AppCompatActivity() {

    private lateinit var binding: IncludeDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = IncludeDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainInclude.mainBtn.setOnClickListener {
            binding.mainDrawerLayout.openDrawer(GravityCompat.START, true)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)   //선택시 options menu 선택과 동일하게 동작함.
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)

        //navigationView에서 menu가 선택되었을 때 처리
        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item1 ->{
                    Toast.makeText(this, "item1 clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.item2 ->{
                    Toast.makeText(this, "item2 clicked", Toast.LENGTH_SHORT).show()

                }
                R.id.item3 ->{
                    Toast.makeText(this, "item3 clicked", Toast.LENGTH_SHORT).show()
                }
            }
            binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drawer2, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            if(binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)){   //서랍 열려있는지 확인
                binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
            } else{     //서랍 닫기
                binding.mainDrawerLayout.openDrawer(GravityCompat.START)
            }
        } else if(item.itemId == R.id.right_options){
            Toast.makeText(this, "right options menu clicked", Toast.LENGTH_SHORT).show()
            binding.mainDrawerLayout.openDrawer(GravityCompat.END)
        }
        return super.onOptionsItemSelected(item)
    }
}



