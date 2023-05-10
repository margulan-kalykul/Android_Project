package com.example.finalproject.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper(var client: OkHttpClient) {
<<<<<<< Updated upstream
    var url = "http://127.0.0.1:8000/api/"
=======
    var url = "https://59a7-5-251-200-170.ngrok-free.app/api/"
>>>>>>> Stashed changes

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(url).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}