package com.example.finalproject.basket

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.finalproject.R
import com.example.finalproject.buypage.BuyPageActivity
import com.example.finalproject.databinding.ActivityBasketBinding
import kotlin.math.roundToInt

class BasketActivity : AppCompatActivity(), FragmentListener {
    private var sum: Double = 0.0
    lateinit var binding: ActivityBasketBinding
    private val fragment = ProductBasketFragment.newInstance()
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment).commit()

        binding.btnClear.setOnClickListener {
            fragment.getCleared()
        }
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == RESULT_OK) {
                fragment.getCleared()
                finish()
            }
        }
        binding.btnBuy.setOnClickListener {
            val intent = Intent(this, BuyPageActivity::class.java)
            intent.putExtra("sum", sum)
            launcher.launch(intent)
        }

        for(product in basket) sum += product.price * product.count
        binding.basketText.text = "Моя корзина: " + ((sum * 100).roundToInt() / 100.0).toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }

    override fun onFragmentSum(sum: Double) {
        binding.basketText.text = "Моя корзина: " + ((sum * 100).roundToInt() / 100.0).toString()
    }
}