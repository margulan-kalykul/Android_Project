package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.finalproject.databinding.ActivityProductPageBinding


class ProductsPage : AppCompatActivity() {
    lateinit var binding: ActivityProductPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductPageBinding.inflate(layoutInflater)
        val products_page = binding.root
        setContentView(products_page)
    }

        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            menuInflater.inflate(R.menu.menu_products_page, menu)
            return true
        }
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_settings -> {
                    // Handle settings backButton click
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
}