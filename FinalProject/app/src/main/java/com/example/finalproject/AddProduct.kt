package com.example.finalproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.finalproject.databinding.ActivityAddProductBinding
import com.example.finalproject.databinding.ActivityLoginBinding

import com.example.finalproject.interfaces.category.OptionAdapter
import com.example.finalproject.retrofit.RetrofitHelper
import com.example.finalproject.service.ServerAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import com.example.finalproject.interfaces.Category
import com.example.finalproject.interfaces.Product

class AddProduct : AppCompatActivity() {

    val client = OkHttpClient.Builder().build()
    val retrofit = RetrofitHelper(client)
    val rf = retrofit.getInstance()
    val itemAPI = rf.create(ServerAPI::class.java)
    lateinit var binding: ActivityAddProductBinding

    private lateinit var category: Category
    private lateinit var categories: List<Category>
    private lateinit var adapter: OptionAdapter
    private lateinit var spinner: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        val addProductPage = binding.root
        setContentView(addProductPage)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        spinner = binding.mySpinner
        val productName = binding.editTextTextPersonName
        val productDescription = binding.editTextTextPersonName2
        val productPrice = binding.editTextNumber
        val productURL = binding.editTextTextPersonName3


        CoroutineScope(Dispatchers.Main).launch {
            categories = itemAPI.getCategories()
            adapter = OptionAdapter(this@AddProduct, categories)
            spinner.adapter = adapter
            category = categories[0]

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedOption = parent.getItemAtPosition(position) as Category
                    category = selectedOption
                    Log.d("category:", category.toString())
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

            binding.button2.setOnClickListener{
                Log.d("category...", category.toString())
                val id = 0
                val name = productName.text.toString()
                val description = productDescription.text.toString()
                val price = productPrice.text.toString().toFloat()
                val url = productURL.text.toString()
                val usersProduct: Product = Product(0, name, description, price, url, category.id)
                CoroutineScope(Dispatchers.IO).launch {
                    val postt = itemAPI.postProducts(usersProduct)
                    val intent = Intent(applicationContext, ProductsPage::class.java)
                    startActivity(intent)

                }
            }
        }



//            Log.d("category11:", category.toString())


//            val postItems = itemAPI.postProducts(usersProduct)

//            Log.d("posted:", postItems.toString())



    }

//    private suspend fun getSelectedCategory(spinner: Spinner, categories: List<Category>): Category {
//        var category: Category = categories[0]
////        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
////            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
////                val selectedOption = parent.getItemAtPosition(position) as Category
////                category = selectedOption
////                Log.d("category:", category.toString())
////            }
////
////            override fun onNothingSelected(parent: AdapterView<*>) {
////
////            }
////        }
//        return category
//    }

    private fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }
}