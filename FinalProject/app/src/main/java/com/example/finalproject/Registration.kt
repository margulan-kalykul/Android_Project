package com.example.finalproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.databinding.RegistrationBinding
import com.example.finalproject.interfaces.UserRegister
import com.example.finalproject.interfaces.UserResponse
import com.example.finalproject.retrofit.RetrofitHelper
import com.example.finalproject.service.ServerAPI
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class Registration : AppCompatActivity() {
    lateinit var binding: RegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegistrationBinding.inflate(layoutInflater)
        val registration = binding.root
        setContentView(registration)

        val register = binding.registerButton
        register.setOnClickListener {
            val credentials: Map<String, String> = getFields()
            if (checkCredentials(credentials)) {
                CoroutineScope(Dispatchers.IO).launch {
                    //binding.progressBar.visibility = View.VISIBLE
                    enterUser(credentials)
                    //binding.progressBar.visibility = View.GONE
                }
            }
        }

    }

    private fun checkCredentials(credentials: Map<String, String>): Boolean {
        // check if fields are filled
        for (field: Map.Entry<String, String> in credentials) {
            if (field.value.isEmpty()) {
                showToast(applicationContext, "Fill all fields")
                return false
            }
        }

        // check password has been typed correctly
        if (credentials["password"] != credentials["confirmPassword"]) {
            showToast(applicationContext, "Passwords don't match")
            return false
        }

        return true
    }

    private fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun getFields(): Map<String, String> {
        val username = binding.usernameField.text.toString()
        val email = binding.emailField.text.toString()
        val password = binding.passwordField.text.toString()
        val confirmPassword = binding.passwordConfirmField.text.toString()
        return mapOf("username" to username, "email" to email,
        "password" to password, "confirmPassword" to confirmPassword)
    }

    // try posting to server and print message
    private suspend fun enterUser(credentials: Map<String, String>) {
        val username: String = credentials["username"]!!
        val email: String = credentials["email"]!!
        val password: String = credentials["password"]!!

        val client = OkHttpClient.Builder().build()
        val retrofit = RetrofitHelper(client)
        val rf = retrofit.getInstance()
        val itemAPI = rf.create(ServerAPI::class.java)

        try {
            val response: UserResponse = itemAPI.registerUser(UserRegister(username, email, password))
            println(response)
            val intent = Intent(this, Login::class.java)
            intent.putExtra("username", username)
            intent.putExtra("password", password)
            startActivity(intent)
        } catch (e: java.lang.Exception) {
            println("Exception: " + e.message)
        }
    }
}