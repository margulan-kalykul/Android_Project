package com.example.finalproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.finalproject.databinding.ActivityLoginBinding
import com.example.finalproject.interfaces.Token
import com.example.finalproject.interfaces.UserLogin
import com.example.finalproject.interfaces.users.User
import com.example.finalproject.interfaces.users.UserEmail
import com.example.finalproject.interfaces.users.UserId
import com.example.finalproject.retrofit.RetrofitHelper
import com.example.finalproject.service.ServerAPI
import com.google.gson.Gson
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
    val tokens = Token("", "")

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

                        putInStorage(userId.id, username, userEmail.email, tokens)
                        makeIntent(userId.id, username, userEmail.email)
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
            this.tokens.refresh = listTokens.refresh
            this.tokens.access = listTokens.access
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
        return itemAPI.getUserEmail(username)
    }
    private fun putInStorage(id: Int, username: String, userEmail:String, token: Token){
        val sharedPreferences = this.getSharedPreferences("tokens", Context.MODE_PRIVATE)

        // Store the token
        val storingToken = token.access
        sharedPreferences.edit().putString("token", storingToken).apply()

        // Store the user object as a JSON string
        val user = User(id,username,userEmail)
        val userJson = Gson().toJson(user)
        sharedPreferences.edit().putString("user", userJson).apply()
    }

//    private fun retrieveTokenFromStorage(){
//        val sharedPreferences = this.getSharedPreferences("tokens", Context.MODE_PRIVATE)
//
//        val token = sharedPreferences.getString("token", null)
//
//        val userJson = sharedPreferences.getString("user", null)
//        val user = Gson().fromJson(userJson, User::class.java)
//
//        if (token != null && user != null){
//            Log.d("token", token)
//            Log.d("user", user.toString())
//
//            makeIntent(user.id, user.username, user.userEmail)
//        }
//    }

    private fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun infoAboutError(){

    }


    private fun makeIntent(userId: Int, username: String, userEmail: String){
        val intent = Intent(applicationContext, PersonalCabinet::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("userName", username)
        intent.putExtra("userEmail", userEmail)
        startActivity(intent)
    }
}