package com.example.finalproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.finalproject.databinding.ActivityLoginBinding
import com.example.finalproject.interfaces.UserLogin
import com.example.finalproject.interfaces.users.UserEmail
import com.example.finalproject.interfaces.users.UserId
import com.example.finalproject.retrofit.RetrofitHelper
import com.example.finalproject.service.ServerAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.HttpException

class Login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    val client = OkHttpClient.Builder().build()
    val retrofit = RetrofitHelper(client)
    val rf = retrofit.getInstance()
    val itemAPI = rf.create(ServerAPI::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val loginPage = binding.root
        setContentView(loginPage)


        val loginButton = binding.buttonLogin
        loginButton.setOnClickListener{


            val username = binding.usernameField.text.toString()
            val password = binding.passwordField.text.toString()
            val credentials = UserLogin(username, password)

            if (checkValidness(credentials)){
                CoroutineScope(Dispatchers.IO).launch {
                    if( isUserExist(credentials) ){
                        val userId: UserId = getUserId(username)
                        val userEmail: UserEmail = getUserEmail(username)

                        val intent = Intent(applicationContext, PersonalCabinet::class.java)
                        intent.putExtra("userId", userId.id)
                        intent.putExtra("userName", username)
                        intent.putExtra("userEmail", userEmail.email)
                        startActivity(intent)
                    }
                }
            }
        }
    }
    private fun checkValidness(credentials: UserLogin): Boolean{
        if(credentials.username.isEmpty() or credentials.password.isEmpty()){
            runOnUiThread {
                showToast(applicationContext, "Fill all credentials")
            }
            return false
        }
        return true
    }

    private suspend fun isUserExist(credentials: UserLogin): Boolean{
        try {
            val listTokens = this.itemAPI.postUserCredentials(credentials)
            Log.d("data", listTokens.toString())
        } catch (e: HttpException){
            runOnUiThread {
                showToast(applicationContext, "No active account found with the given credentials")
            }
            return false
        }

        return true
    }

    private suspend fun getUserId(username: String): UserId {
        Log.d("dataId", this.itemAPI.getUser(username).toString())
        return this.itemAPI.getUser(username)
    }
    private suspend fun getUserEmail(username: String): UserEmail {
        Log.d("dataEmail", this.itemAPI.getUserEmail(username).toString())
        return this.itemAPI.getUserEmail(username)
    }
    private fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun infoAboutError(){

    }
}