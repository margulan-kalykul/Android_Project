package com.example.finalproject.retrofit

import com.example.finalproject.service.ServerAPI
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class RetrofitCRUD {
    /*private val client = OkHttpClient.Builder().build()
    private val retrofit = RetrofitHelper(client)
    private val rf = retrofit.getInstance()
    private val itemAPI = rf.create(ServerAPI::class.java)

    fun postUser(username: String, email: String, password: String) {
        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("email", email)
        jsonObject.put("password", password)
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        // Create RequestBody
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = itemAPI.registerUser(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val result: String = gson.toJson(
                        JsonParser().parse(
                            response.body()?.string()
                        )
                    )

                    println(result)
                }
                else
                    println(response.code().toString())
            }
        }

    }*/
}