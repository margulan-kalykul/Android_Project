package com.example.finalproject.basket

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentProductBasketBinding

class ProductBasketFragment : Fragment(), BasketAdapter.Listener {
    lateinit var binding: FragmentProductBasketBinding
    private var adapter = BasketAdapter(this)
    private var listener: FragmentListener? = null
    private var sum: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBasketBinding.inflate(inflater)
        binding.rcProduct.layoutManager = LinearLayoutManager(context)
        binding.rcProduct.adapter = adapter
        adapter.addProduct(ProductBasket(1, "YES", "NO", 124.25, R.drawable.logo, 2))
        adapter.addProduct(ProductBasket(2, "NO", "YES", 124.35, R.drawable.logo))

        for(product in basket) sum += (product.price * product.count)
        sendDataToActivity()

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductBasketFragment()
    }

    override fun onClick(product: ProductBasket) {
        return
    }

    override fun onDelete(product: ProductBasket) {
        adapter.deleteProduct(product)
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
        adapter.clearProduct()
        onUpdate()
    }
}