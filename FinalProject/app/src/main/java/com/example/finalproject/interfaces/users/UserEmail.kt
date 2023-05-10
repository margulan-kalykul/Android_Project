package com.example.finalproject.interfaces.users

import com.google.gson.annotations.SerializedName

data class UserEmail(
    @SerializedName("email")
    val email: String
)
