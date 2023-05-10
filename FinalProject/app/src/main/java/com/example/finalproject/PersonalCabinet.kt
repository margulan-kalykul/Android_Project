package com.example.finalproject

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
            
        }
    }

    private fun setContents(username: String?, email: String?){
        val userNameInLayout = binding.textView
        val userEmailInLayout = binding.textView2
        userNameInLayout.text = username
        userEmailInLayout.text = email
    }
}