package com.example.finalproject.service

import com.example.finalproject.interfaces.Product
import retrofit2.Call
import retrofit2.http.*
interface ServerAPI {
    @GET("products/")
    suspend fun getProducts(): List<Product>
}