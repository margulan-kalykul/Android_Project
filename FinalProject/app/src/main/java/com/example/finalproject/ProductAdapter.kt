package com.example.finalproject

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
import com.example.finalproject.databinding.ProductCabinetBinding
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import kotlin.coroutines.coroutineContext
import kotlin.math.roundToInt

class ProductAdapter(val listener: Listener): RecyclerView.Adapter<ProductAdapter.ProductHolder>() {
    var productList = arrayListOf<ProductCabinet>()
    
    class ProductHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ProductCabinetBinding.bind(item)
        fun bind(product: ProductCabinet, listener: Listener) {
            itemView.setOnClickListener {
                listener.onClick(product)
            }
            val id = product.id.toString()
            val name = product.name
            val price = product.price
            val image = product.image
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
                productPrice.text = price.toString()
                btnDelete.setOnClickListener {
                    listener.onDelete(product)
                }
                btnUpdate.setOnClickListener {
                    listener.onUpdate(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_cabinet, parent, false)
        return ProductHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bind(productList[position], listener)
    }

    fun addProduct(product: ProductCabinet) {
        productList.add(product)
        notifyDataSetChanged()
    }

    fun deleteProduct(product: ProductCabinet) {
        productList.remove(product)
        notifyDataSetChanged()
    }

    interface Listener {
        fun onClick(product: ProductCabinet)
        fun onDelete(product: ProductCabinet)
        fun onUpdate(product: ProductCabinet)
    }
}