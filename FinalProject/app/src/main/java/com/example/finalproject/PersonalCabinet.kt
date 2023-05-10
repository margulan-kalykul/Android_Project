package com.example.finalproject

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

        val id: Int? = intent.extras?.getInt("userId")
        val userName: String? = intent.extras?.getString("userName")
        val userEmail: String? = intent.extras?.getString("userEmail")
        if (id != null) {
            Log.d("data", id.toString())
        }
        setContents(userName, userEmail)
    }

    private fun setContents(username: String?, email: String?){
        val userNameInLayout = binding.textView
        val userEmailInLayout = binding.textView2
        userNameInLayout.text = username
        userEmailInLayout.text = email
    }
}