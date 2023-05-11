package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.finalproject.databinding.ActivityProductBinding
import com.example.finalproject.interfaces.Comment
import com.example.finalproject.retrofit.RetrofitHelper
import com.example.finalproject.service.ServerAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient

class Product : AppCompatActivity() {
    lateinit var binding: ActivityProductBinding
    var comments = MutableLiveData<List<String>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        val productLayout = binding.root
        setContentView(productLayout)

        var productId = intent.extras?.getInt("id")
        if (productId == null)
            productId = 1

        binding.backButton.setOnClickListener {
            finish()
        }

//        // Show rating
//        binding.addButton.setOnClickListener {
//            val rating = binding.ratingBar.rating
//            Toast.makeText(applicationContext, rating.toString(), Toast.LENGTH_SHORT).show()
//        }

        val client = OkHttpClient.Builder().build()
        val retrofit = RetrofitHelper(client)
        val rf = retrofit.getInstance()
        val itemAPI = rf.create(ServerAPI::class.java)

        Log.d("test", "got here")

        lifecycleScope.launch(Dispatchers.IO) {
            val response: List<Comment> = itemAPI.getProductComments(productId.toString())
            withContext(Dispatchers.Main) {
                val commentString = mutableListOf<String>()
                for (comment in response) {
                    commentString.add(comment.user + "\n" + comment.text)
                }
                comments.value = commentString
            }
        }

        comments.observe(this, Observer {
            binding.commentList.adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, it)
        })
    }
}