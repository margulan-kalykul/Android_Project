package com.example.finalproject.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper(var client: OkHttpClient) {
    var url = "https://1f4f-2-132-56-139.ngrok-free.app/api/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(url).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}