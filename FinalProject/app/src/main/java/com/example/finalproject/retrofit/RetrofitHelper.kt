package com.example.finalproject.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper(var client: OkHttpClient) {
    var url = "https://00f9-146-120-202-175.ngrok-free.app/api/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(url).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}