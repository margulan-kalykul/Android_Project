package com.example.finalproject.service

import com.example.finalproject.interfaces.*
import okhttp3.ResponseBody
import retrofit2.Response
import com.example.finalproject.interfaces.users.UserEmail
import com.example.finalproject.interfaces.users.UserId
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Query

interface ServerAPI {
    @GET("user/{username}")
    suspend fun getUser(@Path("username") userName: String?): UserId
    @GET("user/email/{username}")
    suspend fun getUserEmail(@Path("username") userName: String?): UserEmail
    @GET("products/")
    suspend fun getProducts(): List<Product>
    @POST("products/")
    suspend fun postProducts(@Body product: Product): Product
    @GET("product/{id}/commentaries/")
    suspend fun getProductComments(@Path("id") id: String?): List<Comment>
    @POST("product/{id}/commentaries/")
    suspend fun postProductComments(@Path("id") id: Int, @Body comment: UserComment): Comment
    @GET("product/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product

    @GET("cart/{userId}")
    suspend fun getCartProducts(@Path("userId") userId: Int): List<ProductsInCart>
    @POST("cart/{userId}/")
    suspend fun postCartProducts(@Path("userId") userId: Int, @Body product: ProductsInCart) //(1,
    @DELETE("cart/{userId}/{productId}/")
    suspend fun deleteProductFromCart(@Path("userId") userId: Int, @Path("productId") productId: Int)

    @POST("register/user/")
    suspend fun registerUser(@Body user: UserRegister): UserResponse
    @GET("categories/")
    suspend fun getCategories(): List<Category>
    @POST("token/")
    suspend fun postUserCredentials(
        @Body userCred: UserLogin): Token
    @GET("products/search")
    suspend fun getProductsByName(@Query("q") name: String): SearchResult

    @GET("product/{productId}/commentaries/{userId}/")
    suspend fun getCommentsOfAUser(@Path("productId") productId: Int, @Path("userId") userId: Int): List<Comment>

    @DELETE("commentary/{commentId}/delete/")
    suspend fun deleteCommentaryOfAUser(@Path("commentId") commentId: Int): Deleted
}