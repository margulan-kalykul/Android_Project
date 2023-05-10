package com.example.finalproject.interfaces

import com.google.gson.annotations.SerializedName

data class UserRegister(
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
