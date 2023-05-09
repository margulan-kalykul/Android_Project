package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.finalproject.databinding.ActivityProductPageBinding
import com.example.finalproject.retrofit.RetrofitHelper
import com.example.finalproject.service.ServerAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient


class ProductsPage : AppCompatActivity() {
    lateinit var binding: ActivityProductPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductPageBinding.inflate(layoutInflater)
        val products_page = binding.root
        setContentView(products_page)

//        val client = OkHttpClient.Builder().build()
//        val retrofit = RetrofitHelper(client)
//        val rf = retrofit.getInstance()
//        val itemAPI = rf.create(ServerAPI::class.java)
//
//        CoroutineScope(Dispatchers.IO).launch {
//            val listTitles = itemAPI.getProducts()
//            Log.d("data", listTitles.toString())
//        }
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