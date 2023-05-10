package com.example.finalproject.basket

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBasketBinding.inflate(inflater)
        binding.rcProduct.layoutManager = LinearLayoutManager(context)
        binding.rcProduct.adapter = adapter
        adapter.addProduct(ProductBasket(1, "YES", "NO", 124.25, R.drawable.logo, 2))
        adapter.addProduct(ProductBasket(2, "NO", "YES", 124.35, R.drawable.logo))
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
}