package com.example.finalproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.finalproject.databinding.ActivityLoginBinding
import com.example.finalproject.interfaces.Token
import com.example.finalproject.interfaces.UserLogin
import com.example.finalproject.retrofit.RetrofitHelper
import com.example.finalproject.service.ServerAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class Login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val loginPage = binding.root
        setContentView(loginPage)

        val userName = intent.extras?.getString("username")
        val passWord = intent.extras?.getString("password")
        if (userName != null && passWord != null) {
            binding.usernameField.setText(userName)
            binding.passwordField.setText(passWord)
        }

        val client = OkHttpClient.Builder().build()
        val retrofit = RetrofitHelper(client)
        val rf = retrofit.getInstance()
        val itemAPI = rf.create(ServerAPI::class.java)

        val loginButton = binding.buttonLogin
        loginButton.setOnClickListener{
            val username = binding.usernameField.text.toString()
            val password = binding.passwordField.text.toString()
            val credentials = UserLogin(username, password)


            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val listTokens = itemAPI.postUserCredentials(credentials)
                    Log.d("data", listTokens.toString())
                } catch (e: retrofit2.HttpException){
                    showToast(applicationContext, "You may not have registered yet")
                }

            }


        }
    }
    private fun isUserExist(token: Token): Boolean{
        if (token != null){
            showToast(applicationContext, "Authorization success!")
            return true
        }
        showToast(applicationContext, "User need to register")
        return false
    }

    private fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}