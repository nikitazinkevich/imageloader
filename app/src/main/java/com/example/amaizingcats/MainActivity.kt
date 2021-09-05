package com.example.amaizingcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    private lateinit var toolBar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        toolBar = findViewById(R.id.tool_bar)
        recyclerView = findViewById(R.id.recycler_view)
        val pictureAdapter = PictureAdapter()
        recyclerView.apply {
            layoutManager = GridLayoutManager(
                this.context,
                7,
                RecyclerView.HORIZONTAL,
                false
            )
            adapter = pictureAdapter
            isNestedScrollingEnabled = false
        }
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        mainViewModel.pictureList.observe(this) { imageList ->
            lifecycleScope.launch {
                pictureAdapter.submitList(imageList)
            }
        }





    }

    override fun onResume() {
        super.onResume()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_icon_toolbar -> {
                mainViewModel.onAddNewPicture()
                (recyclerView.adapter as PictureAdapter).adaptSinglePicture()

            }
            R.id.reload_title_toolbar -> mainViewModel.onReloadAll()
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}