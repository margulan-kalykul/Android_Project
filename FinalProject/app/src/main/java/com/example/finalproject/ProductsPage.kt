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
import com.example.finalproject.interfaces.Product


class ProductsPage : AppCompatActivity() {
    lateinit var binding: ActivityProductPageBinding
    val client = OkHttpClient.Builder().build()
    val retrofit = RetrofitHelper(client)
    val rf = retrofit.getInstance()
    val itemAPI = rf.create(ServerAPI::class.java)
    private lateinit var listViewAdapter : ExpandableListViewAdapter
    private lateinit var chapterList : List<String>
    private lateinit var topicList : HashMap<String, List<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductPageBinding.inflate(layoutInflater)
        val products_page = binding.root
        setContentView(products_page)
        CoroutineScope(Dispatchers.IO).launch {
            val call = itemAPI.getProducts()
            showList(call)
        }


        listViewAdapter = ExpandableListViewAdapter(this, chapterList, topicList)
        val eListView = binding.eListView
        eListView.setAdapter(listViewAdapter)

    }
        private fun showList(call: List<Product>) {
            chapterList = ArrayList()
            topicList = HashMap()
            (chapterList as ArrayList<String>).add(call[0].name)
            (chapterList as ArrayList<String>).add(call[1].name)
            (chapterList as ArrayList<String>).add(call[2].name)
            (chapterList as ArrayList<String>).add(call[3].name)
            (chapterList as ArrayList<String>).add(call[4].name)

            val topic1 : MutableList<String> = ArrayList()
            topic1.add(call[0].description)
            topic1.add(call[0].image)
            topic1.add(call[0].description)

            val topic2 : MutableList<String> = ArrayList()
            topic2.add(call[1].description)
            topic2.add(call[1].image)
            topic2.add(call[1].description)

            val topic3 : MutableList<String> = ArrayList()
            topic3.add(call[2].description)
            topic3.add(call[2].image)
            topic3.add(call[2].description)

            val topic4 : MutableList<String> = ArrayList()
            topic4.add(call[3].description)
            topic4.add(call[3].image)
            topic4.add(call[3].description)

            val topic5 : MutableList<String> = ArrayList()
            topic5.add(call[4].description)
            topic5.add(call[4].image)
            topic5.add(call[4].description)

            topicList[chapterList[0]] = topic1
            topicList[chapterList[1]] = topic2
            topicList[chapterList[2]] = topic3
            topicList[chapterList[3]] = topic4
            topicList[chapterList[4]] = topic5
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