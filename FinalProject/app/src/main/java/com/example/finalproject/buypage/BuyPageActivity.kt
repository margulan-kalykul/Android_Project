package com.example.finalproject.buypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.finalproject.basket.ProductBasket
import com.example.finalproject.databinding.ActivityBuyPageBinding

class BuyPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityBuyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sum = intent.getDoubleExtra("sum", 0.0)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }
}