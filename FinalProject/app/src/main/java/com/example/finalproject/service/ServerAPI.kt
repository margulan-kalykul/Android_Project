package com.example.finalproject.service

<<<<<<< Updated upstream
import com.example.finalproject.interfaces.Product
=======
import com.example.finalproject.interfaces.*
import com.example.finalproject.interfaces.users.UserId
>>>>>>> Stashed changes
import retrofit2.Call
import retrofit2.http.*
interface ServerAPI {
    @GET("user/{username}")
    suspend fun getUser(@Path("username") userName: String?): UserId
    @GET("products/")
    suspend fun getProducts(): List<Product>
<<<<<<< Updated upstream
=======
    @GET("categories/")
    suspend fun getCategories(): List<Category>
    @POST("token/")
    suspend fun postUserCredentials(
        @Body userCred: UserLogin): Token


>>>>>>> Stashed changes
}