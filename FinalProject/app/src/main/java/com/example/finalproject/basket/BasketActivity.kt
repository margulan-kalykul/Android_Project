package com.example.finalproject.basket

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityBasketBinding

class BasketActivity : AppCompatActivity() {
    private var sum: Double = 0.0
    lateinit var binding: ActivityBasketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, ProductBasketFragment.newInstance()).commit()

        for(product in basket) sum += product.price * product.count
        binding.basketText.text = "Моя корзина: " + sum.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }

    fun onUpdate() {

    }
}