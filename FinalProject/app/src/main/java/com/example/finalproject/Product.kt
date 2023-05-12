package com.example.finalproject

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.finalproject.databinding.ActivityProductBinding
import com.example.finalproject.interfaces.Comment
import com.example.finalproject.interfaces.ProductsInCart
import com.example.finalproject.interfaces.UserComment
import com.example.finalproject.rating.RatingChange
import com.example.finalproject.retrofit.RetrofitHelper
import com.example.finalproject.service.ServerAPI
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import java.lang.Exception

class Product : AppCompatActivity() {
    lateinit var binding: ActivityProductBinding
    var comments = MutableLiveData<List<String>>()
    var clicked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        val productLayout = binding.root
        setContentView(productLayout)

        // Setup at the start
        val productId = intent.getIntExtra("productId", 1)
        val userId = intent.getIntExtra("userId", 2)
        val userName = intent.getStringExtra("userName")?:""
        val client = OkHttpClient.Builder().build()
        val retrofit = RetrofitHelper(client)
        val rf = retrofit.getInstance()
        val itemAPI = rf.create(ServerAPI::class.java)

        Log.d("productId:", productId.toString())
        Log.d("username:", userName)

        binding.commentFiled.clearFocus()

        binding.backButton.setOnClickListener {
            finish()
        }

//        // Show rating
//        binding.addButton.setOnClickListener {
//            val rating = binding.ratingBar.rating
//            Toast.makeText(applicationContext, rating.toString(), Toast.LENGTH_SHORT).show()
//        }

        CoroutineScope(Dispatchers.Main).launch {

            var avg = 0.0
            var sum = 0.0
            val product = itemAPI.getProduct(productId)
            var ratings = itemAPI.getRatingsOfAProduct(productId)
            for(rating in ratings) sum += rating.rating
            if(ratings.isNotEmpty()) avg = sum / ratings.size
            Log.d("avg1", avg.toString())
            val image = product.image
            val name = product.name
            binding.apply {
                Picasso.get().load(image).into(object : com.squareup.picasso.Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        productImage.setImageBitmap(bitmap)
                    }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                        productImage.setImageResource(R.drawable.logo)
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                    }
                })
                productName.text = name
                ratingBar.rating = avg.toFloat()
//                ratingBar.setOnRatingBarChangeListener { _, rate, _ ->
//                    CoroutineScope(Dispatchers.IO).launch {
//                        itemAPI.changeRatingsOfAProduct(productId, RatingChange(userId, rate))
//                        avg = 0.0
//                        sum = 0.0
//                        Log.d("data", rate.toString())
//                        ratings = itemAPI.getRatingsOfAProduct(productId)
//                        for(rating in ratings) sum += rating.rating
//                        if(ratings.isNotEmpty()) avg = sum / ratings.size
//                        Log.d("avg", avg.toString())
//                        ratingBar.rating = avg.toFloat()
//                    }
//                }
            }

        }
        val ratingBar = binding.ratingBar
        ratingBar.setOnRatingBarChangeListener { _, rate, _ ->
            if(clicked){
                clicked = false
                return@setOnRatingBarChangeListener
            }
            CoroutineScope(Dispatchers.IO).launch {
                itemAPI.changeRatingsOfAProduct(productId, RatingChange(userId, rate))
                var avg = 0.0
                var sum = 0.0
                Log.d("data", rate.toString())
                var ratings = itemAPI.getRatingsOfAProduct(productId)
                for(rating in ratings) sum += rating.rating
                if(ratings.isNotEmpty()) avg = sum / ratings.size
                Log.d("avg", avg.toString())
                ratingBar.rating = avg.toFloat()
                clicked = true
            }
        }

        binding.btnAddCart.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                itemAPI.postCartProducts(userId, ProductsInCart(productId))

            }
        }

        binding.button.setOnClickListener {
            val text = binding.commentFiled.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val comment = UserComment(userName, text)
                Log.d("comment:", comment.toString())
                val postComment = itemAPI.postProductComments(productId, comment)
                Log.d("comment:", postComment.toString())

                withContext(Dispatchers.Main) {
                    updateList(itemAPI, productId)
                }
            }
        }
        Log.d("test", "got here")

        updateList(itemAPI, productId)

        comments.observe(this, Observer {
            binding.commentList.adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, it)
            changeListHeight(binding.commentList)
        })
    }

    private fun updateList(itemAPI: ServerAPI, productId: Int) {
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
    }

    private fun changeListHeight(listView: ListView) {
        var height = 0
        val adapter = listView.adapter

        for (i in 0 until adapter.count) {
            val comment = adapter.getView(i, null, listView)
            comment.measure(0, 0)
            height += comment.measuredHeight
        }
        Log.d("height", height.toString())

        val layoutParams = listView.layoutParams
        layoutParams.height = height + (listView.dividerHeight * (adapter.count - 1))
        listView.layoutParams = layoutParams
    }
}