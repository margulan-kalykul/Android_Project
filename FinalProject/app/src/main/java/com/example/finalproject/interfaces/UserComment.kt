package com.example.finalproject.interfaces

import com.google.gson.annotations.SerializedName

data class UserComment(
    @SerializedName("user")
    val user: String,
    @SerializedName("text")
    val text: String
)
