package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.finalproject.databinding.ActivityAddProductBinding
import com.example.finalproject.databinding.ActivityLoginBinding
import com.example.finalproject.interfaces.category.Option
import com.example.finalproject.interfaces.category.OptionAdapter
import com.example.finalproject.retrofit.RetrofitHelper
import com.example.finalproject.service.ServerAPI
import okhttp3.OkHttpClient

class AddProduct : AppCompatActivity() {

    val client = OkHttpClient.Builder().build()
    val retrofit = RetrofitHelper(client)
    val rf = retrofit.getInstance()
    val itemAPI = rf.create(ServerAPI::class.java)
    lateinit var binding: ActivityAddProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddProductBinding.inflate(layoutInflater)
        val addProductPage = binding.root
        setContentView(addProductPage)

        val options = listOf(
            Option("Option 1"),
            Option("Option 2"),
            Option("Option 3")
        )
        val adapter = OptionAdapter(this, options)
        val spinner = binding.mySpinner
        spinner.adapter = adapter
    }
}