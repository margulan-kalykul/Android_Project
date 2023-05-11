package com.example.finalproject.service

import com.example.finalproject.interfaces.*
import okhttp3.ResponseBody
import retrofit2.Response
import com.example.finalproject.interfaces.users.UserEmail
import com.example.finalproject.interfaces.users.UserId
import retrofit2.Call
import retrofit2.http.*

interface ServerAPI {
    @GET("user/{username}")
    suspend fun getUser(@Path("username") userName: String?): UserId
    @GET("user/email/{username}")
    suspend fun getUserEmail(@Path("username") userName: String?): UserEmail
    @GET("products/")
    suspend fun getProducts(): List<Product>
    @GET("product/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product
    @GET("cart/{userId}")
    suspend fun getCartProducts(@Path("userId") userId: Int): List<ProductsInCart>

    @POST("register/user/")
    suspend fun registerUser(@Body user: UserRegister): UserResponse
    @GET("categories/")
    suspend fun getCategories(): List<Category>
    @POST("token/")
    suspend fun postUserCredentials(
        @Body userCred: UserLogin): Token

}