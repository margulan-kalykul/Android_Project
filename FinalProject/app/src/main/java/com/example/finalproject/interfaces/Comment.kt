package com.example.finalproject.interfaces

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id")
    val _id: Int,
    @SerializedName("user")
    val user: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("product")
    val product: Int
)
