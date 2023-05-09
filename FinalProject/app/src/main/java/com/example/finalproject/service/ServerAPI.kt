package com.example.finalproject.service

import com.example.finalproject.interfaces.Category
import com.example.finalproject.interfaces.Product
import com.example.finalproject.interfaces.Token
import com.example.finalproject.interfaces.UserLogin
import retrofit2.Call
import retrofit2.http.*
interface ServerAPI {
    @GET("products/")
    suspend fun getProducts(): List<Product>
    @GET("categories/")
    suspend fun getCategories(): List<Category>
    @POST("token/")
    suspend fun postUserCredentials(
        @Body userCred: UserLogin): Token

}