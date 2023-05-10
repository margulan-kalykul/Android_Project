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


class ProductsPage : AppCompatActivity() {
    lateinit var binding: ActivityProductPageBinding

    private lateinit var listViewAdapter : ExpandableListViewAdapter
    private lateinit var chapterList : List<String>
    private lateinit var topicList : HashMap<String, List<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductPageBinding.inflate(layoutInflater)
        val products_page = binding.root
        setContentView(products_page)
        showList()

        listViewAdapter = ExpandableListViewAdapter(this, chapterList, topicList)
        val eListView = binding.eListView
        eListView.setAdapter(listViewAdapter)

    }
        private fun showList() {
            chapterList = ArrayList()
            topicList = HashMap()
            (chapterList as ArrayList<String>).add("Chapter 1")
            (chapterList as ArrayList<String>).add("Chapter 2")
            (chapterList as ArrayList<String>).add("Chapter 3")
            (chapterList as ArrayList<String>).add("Chapter 4")
            (chapterList as ArrayList<String>).add("Chapter 5")

            val topic1 : MutableList<String> = ArrayList()
            topic1.add("Topic 1")
            topic1.add("Topic 2")
            topic1.add("Topic 3")

            val topic2 : MutableList<String> = ArrayList()
            topic2.add("Topic 1")
            topic2.add("Topic 2")
            topic2.add("Topic 3")

            val topic3 : MutableList<String> = ArrayList()
            topic3.add("Topic 1")
            topic3.add("Topic 2")
            topic3.add("Topic 3")

            val topic4 : MutableList<String> = ArrayList()
            topic4.add("Topic 1")
            topic4.add("Topic 2")
            topic4.add("Topic 3")

            val topic5 : MutableList<String> = ArrayList()
            topic5.add("Topic 1")
            topic5.add("Topic 2")
            topic5.add("Topic 3")

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