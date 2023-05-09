package com.example.finalproject.interfaces

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Float,
    @SerializedName("image")
    val image: String,
    @SerializedName("category")
    val category: Number
)
