package com.example.finalproject.basket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.Product
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentProductBasketBinding
import com.example.finalproject.retrofit.RetrofitHelper
import com.example.finalproject.service.ServerAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class ProductBasketFragment : Fragment(), BasketAdapter.Listener {
    lateinit var binding: FragmentProductBasketBinding
    private var adapter = BasketAdapter(this)
    private var listener: FragmentListener? = null
    private var sum: Double = 0.0
    private var userID: Int = 1
    private var username: String = ""

    private val client = OkHttpClient.Builder().build()
    private val retrofit = RetrofitHelper(client)
    private val rf = retrofit.getInstance()
    private val itemAPI = rf.create(ServerAPI::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBasketBinding.inflate(inflater)
        binding.rcProduct.layoutManager = LinearLayoutManager(context)
        binding.rcProduct.adapter = adapter
        basket.clear()
//        adapter.addProduct(ProductBasket(1, "YES", "NO", 124.25, R.drawable.logo, 2))
//        adapter.addProduct(ProductBasket(2, "NO", "YES", 124.35, R.drawable.logo))
        userID = arguments?.getInt("userID")!!
        username = arguments?.getString("username")!!
        CoroutineScope(Dispatchers.Main).launch {
            if(userID != null) {
                val products = itemAPI.getCartProducts(userID)
                val productSet = mutableSetOf<Int>()
                for(productID in products) productSet.add(productID.products)
                for(productID in productSet) {
                    val product = itemAPI.getProduct(productID)
                    val productBasket = ProductBasket(product.id, product.name, product.description, product.price.toDouble(), product.image)
                    adapter.addProduct(productBasket)
                }
                for(product in basket) sum += (product.price * product.count)
                sendDataToActivity()
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductBasketFragment()
    }

    override fun onClick(product: ProductBasket) {
        val intent = Intent(context, Product::class.java)
        intent.putExtra("userName", username)
        intent.putExtra("productId", product.id)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDelete(product: ProductBasket) {
        adapter.deleteProduct(product)
        CoroutineScope(Dispatchers.Main).launch {
            itemAPI.deleteProductFromCart(userID, product.id)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? FragmentListener
    }

    override fun onUpdate() {
        sum = 0.0
        for(product in basket) sum += (product.price * product.count)
        sendDataToActivity()
    }

    private fun sendDataToActivity() {
        listener?.onFragmentSum(sum)
    }

    fun getCleared() {
        CoroutineScope(Dispatchers.Main).launch {
            for(product in basket) itemAPI.deleteProductFromCart(userID, product.id)
            adapter.clearProduct()
            onUpdate()
        }
    }
}