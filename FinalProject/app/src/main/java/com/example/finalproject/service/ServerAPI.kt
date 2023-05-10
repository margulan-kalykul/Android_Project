package com.example.finalproject.service

import com.example.finalproject.interfaces.Product
import com.example.finalproject.interfaces.UserRegister
import com.example.finalproject.interfaces.UserResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ServerAPI {
    @GET("products/")
    suspend fun getProducts(): List<Product>

    @POST("register/user/")
    suspend fun registerUser(@Body user: UserRegister): UserResponse
}