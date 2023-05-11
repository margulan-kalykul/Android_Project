package com.example.finalproject.interfaces

import com.google.gson.annotations.SerializedName

data class ProductsInCart(
    @SerializedName("products")
    val products: Int
)
