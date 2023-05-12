package com.example.finalproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.basket.BasketActivity
import com.example.finalproject.basket.BasketAdapter
import com.example.finalproject.databinding.ActivityLoginBinding
import com.example.finalproject.databinding.ActivityPersonalCabinetBinding
import com.example.finalproject.interfaces.Product
import com.example.finalproject.retrofit.RetrofitHelper
import com.example.finalproject.service.ServerAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class PersonalCabinet : AppCompatActivity(), ProductAdapter.Listener {
    lateinit var binding: ActivityPersonalCabinetBinding
    private var adapter = ProductAdapter(this)
    private val client = OkHttpClient.Builder().build()
    private val retrofit = RetrofitHelper(client)
    private val rf = retrofit.getInstance()
    private val itemAPI = rf.create(ServerAPI::class.java)
    private lateinit var userName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPersonalCabinetBinding.inflate(layoutInflater)
        binding.rcProduct.layoutManager = LinearLayoutManager(this)
        binding.rcProduct.adapter = adapter
        adapter.productList.clear()
        val PersonalCabinetPage = binding.root
        setContentView(PersonalCabinetPage)

        val userId: Int = intent.getIntExtra("userId", 1)
        userName = intent.getStringExtra("userName").toString()
        val userEmail: String? = intent.extras?.getString("userEmail")

        setContents(userName, userEmail)

        binding.productsButton.setOnClickListener {
            val intent = Intent(this, ProductsPage::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("userName", userName)
            startActivity(intent)
        }
        binding.logOutButton.setOnClickListener {
            logOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }
        binding.textView7.setOnClickListener {
            val intent = Intent(this, AddProduct::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
        binding.cartButton.setOnClickListener {
            val intent = Intent(this, BasketActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("userName", userName)
            startActivity(intent)
        }

        CoroutineScope(Dispatchers.Main).launch {
            val products = itemAPI.getProductOfAUser(userId)
            for(product in products) {
                val productCabinet = ProductCabinet(product.id, product.name, product.description, product.price.toDouble(), product.image)
                adapter.addProduct(productCabinet)
            }
        }
    }

    private fun setContents(username: String?, email: String?){
        val userNameInLayout = binding.textView
        val userEmailInLayout = binding.textView2
        userNameInLayout.text = username
        userEmailInLayout.text = email
    }

    private fun logOut(){
        val sharedPreferences = this.getSharedPreferences("tokens", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        editor.remove("token")

        editor.remove("user")

        editor.apply()
    }
    override fun onClick(product: ProductCabinet) {
        val intent = Intent(this, com.example.finalproject.Product::class.java)
        intent.putExtra("userName", userName)
        intent.putExtra("productId", product.id)
        startActivity(intent)
    }

    override fun onDelete(product: ProductCabinet) {
        adapter.deleteProduct(product)
        CoroutineScope(Dispatchers.Main).launch {
            itemAPI.deleteProduct(product.id)
        }
    }

    override fun onUpdate(product: ProductCabinet) {
        TODO("Not yet implemented")
    }
}