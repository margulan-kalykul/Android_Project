package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.beust.klaxon.Json
import com.example.finalproject.databinding.ActivityProductBinding
import com.google.gson.Gson
import  com.example.finalproject.interfaces.Product as ProductInterface
class Product : AppCompatActivity() {
    lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        val productLayout = binding.root
        setContentView(productLayout)

        val productId = intent.extras?.getInt("productId")

        Log.d("id", productId.toString())
    }
}