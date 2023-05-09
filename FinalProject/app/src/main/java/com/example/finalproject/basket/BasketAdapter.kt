package com.example.finalproject.basket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.BasketProductBinding

class BasketAdapter(val listener: Listener): RecyclerView.Adapter<BasketAdapter.BasketHolder>() {
    class BasketHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = BasketProductBinding.bind(item)
        fun bind(product: ProductBasket, listener: Listener) {
            itemView.setOnClickListener {
                listener.onClick(product)
            }
            val id = product.id.toString()
            val name = product.name
            val price = product.price.toString()
            val image = product.image
            binding.apply {
                productID.text = id
                productName.text = name
                productImage.setImageResource(image)
                productPrice.text = price
                btnPlus.setOnClickListener {
                    productCount.text = (productCount.text.toString().toInt() + 1).toString()
                    productPrice.text = (productPrice.text.toString().toDouble() * productCount.text.toString().toInt()).toString()
                }
                btnMinus.setOnClickListener {
                    productCount.text = (productCount.text.toString().toInt() - 1).toString()
                    productPrice.text = (productPrice.text.toString().toDouble() * productCount.text.toString().toInt()).toString()
                    if(productCount.text.toString().toInt() == 0) listener.onDelete(product)
                    }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.basket_product, parent, false)
        return BasketHolder(view)
    }

    override fun getItemCount(): Int {
        return basket.size
    }

    override fun onBindViewHolder(holder: BasketHolder, position: Int) {
        holder.bind(basket[position], listener)
    }

    fun addProduct(product: ProductBasket) {
        basket.add(product)
        notifyDataSetChanged()
    }

    fun deleteProduct(product: ProductBasket) {
        basket.remove(product)
        notifyDataSetChanged()
    }

    interface Listener {
        fun onClick(product: ProductBasket)
        fun onDelete(product: ProductBasket)
    }
}