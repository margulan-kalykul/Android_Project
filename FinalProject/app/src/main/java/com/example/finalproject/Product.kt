package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalproject.databinding.ActivityProductBinding

class Product : AppCompatActivity() {
    lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        val productLayout = binding.root
        setContentView(productLayout)


    }
}