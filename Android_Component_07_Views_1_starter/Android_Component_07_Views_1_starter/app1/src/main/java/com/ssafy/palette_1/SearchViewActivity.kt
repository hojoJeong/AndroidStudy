package com.ssafy.palette_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import com.ssafy.palette_1.databinding.ActivitySearchViewBinding

class SearchViewActivity : AppCompatActivity() {

    lateinit var binding : ActivitySearchViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setTitle(R.string.search_view_title)
        setSupportActionBar(binding.toolbar)

        binding.searchViewAlone.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchViewAloneTv.text = query

                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.button.setOnClickListener {
            startActivity(Intent(this, TextureViewActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_view, menu)
        val searchItem = menu!!.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchViewInToolbarTv.text = query
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }
}