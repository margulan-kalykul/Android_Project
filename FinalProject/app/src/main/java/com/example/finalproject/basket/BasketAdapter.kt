package com.example.finalproject.basket

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.BasketProductBinding
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import kotlin.coroutines.coroutineContext
import kotlin.math.roundToInt

class BasketAdapter(val listener: Listener): RecyclerView.Adapter<BasketAdapter.BasketHolder>() {
    class BasketHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = BasketProductBinding.bind(item)
        fun bind(product: ProductBasket, listener: Listener) {
            itemView.setOnClickListener {
                listener.onClick(product)
            }
            val id = product.id.toString()
            val name = product.name
            val price = product.price
            val image = product.image
            var count = product.count
            binding.apply {
                productID.text = id
                productName.text = name
                Picasso.get().load(image).into(object: com.squareup.picasso.Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        productImage.setImageBitmap(bitmap)
                    }
                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                        productImage.setImageResource(R.drawable.logo)
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                        
                    }
                })
                productPrice.text = ((price * count * 100).roundToInt() / 100.0).toString()
                productCount.text = count.toString()
                btnPlus.setOnClickListener {
                    productCount.text = (++count).toString()
                    productPrice.text = ((price * count * 100).roundToInt() / 100.0).toString()
                    product.count = count
                    listener.onUpdate()
                }
                btnMinus.setOnClickListener {
                    productCount.text = (--count).toString()
                    productPrice.text = ((price * count * 100).roundToInt() / 100.0).toString()
                    product.count = count
                    listener.onUpdate()
                    if(count == 0) listener.onDelete(product)
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

    fun clearProduct() {
        basket.clear()
        notifyDataSetChanged()
    }

    interface Listener {
        fun onClick(product: ProductBasket)
        fun onDelete(product: ProductBasket)
        fun onUpdate()
    }
}