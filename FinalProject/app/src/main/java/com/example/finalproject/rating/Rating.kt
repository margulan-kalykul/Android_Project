package com.example.finalproject.rating

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("id")
    val id: Int,
    @SerializedName("rating")
    val rating: Float,
    @SerializedName("user")
    val user: Int,
    @SerializedName("product")
    val product: Int
)
