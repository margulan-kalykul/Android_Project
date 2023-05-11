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
    @GET("product/{id}/commentaries")
    suspend fun getProductComments(@Path("id") id: String?): List<Comment>

    @POST("register/user/")
    suspend fun registerUser(@Body user: UserRegister): UserResponse
    @GET("categories/")
    suspend fun getCategories(): List<Category>
    @POST("token/")
    suspend fun postUserCredentials(
        @Body userCred: UserLogin): Token

}