package com.example.finalproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.finalproject.databinding.ActivityLoginBinding
import com.example.finalproject.databinding.ActivityPersonalCabinetBinding

class PersonalCabinet : AppCompatActivity() {
    lateinit var binding: ActivityPersonalCabinetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPersonalCabinetBinding.inflate(layoutInflater)
        val PersonalCabinetPage = binding.root
        setContentView(PersonalCabinetPage)

        val userId: Int? = intent.extras?.getInt("userId")
        val userName: String? = intent.extras?.getString("userName")
        val userEmail: String? = intent.extras?.getString("userEmail")
        if (userId != null) {
            Log.d("data", userId.toString())
        }
        setContents(userName, userEmail)

        binding.productsButton.setOnClickListener {
            val intent = Intent(this, ProductsPage::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
        binding.logOutButton.setOnClickListener {
            logOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }
        binding.textView7.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }
    }

    private fun setContents(username: String?, email: String?){
        val userNameInLayout = binding.textView
        val userEmailInLayout = binding.textView2
        userNameInLayout.text = username
        userEmailInLayout.text = email
    }

    private fun logOut(){
        val sharedPreferences = this.getSharedPreferences("tokens", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        editor.remove("token")

        editor.remove("user")

        editor.apply()
    }
}