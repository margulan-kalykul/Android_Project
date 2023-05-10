package com.example.finalproject.service

import com.example.finalproject.interfaces.Category
import com.example.finalproject.interfaces.Product
import com.example.finalproject.interfaces.UserRegister
import com.example.finalproject.interfaces.UserResponse
import okhttp3.ResponseBody
import retrofit2.Response
import com.example.finalproject.interfaces.Token
import com.example.finalproject.interfaces.UserLogin
import com.example.finalproject.interfaces.users.UserId
import retrofit2.Call
import retrofit2.http.*

interface ServerAPI {
    @GET("user/{username}")
    suspend fun getUser(@Path("username") userName: String?): UserId
    @GET("products/")
    suspend fun getProducts(): List<Product>

    @POST("register/user/")
    suspend fun registerUser(@Body user: UserRegister): UserResponse
    @GET("categories/")
    suspend fun getCategories(): List<Category>
    @POST("token/")
    suspend fun postUserCredentials(
        @Body userCred: UserLogin): Token

}