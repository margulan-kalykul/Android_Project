package com.example.finalproject.rating

import com.google.gson.annotations.SerializedName

data class RatingChange(
    @SerializedName("user")
    val user: Int,
    @SerializedName("rating")
    val rating: Float
)
