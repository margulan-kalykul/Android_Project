package com.example.finalproject.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper(var client: OkHttpClient) {
    var url = "https://849c-2-133-181-173.ngrok-free.app/api/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(url).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}