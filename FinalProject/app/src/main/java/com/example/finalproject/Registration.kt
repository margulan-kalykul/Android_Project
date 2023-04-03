package com.example.finalproject

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.databinding.RegistrationBinding

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
                // TODO: Simple authorisation for now
                enterUser(credentials)
                authorise()
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

    // TODO: Placeholder authorisation, replace
    private fun enterUser(credentials: Map<String, String>) {
        val username: String = credentials["username"]!!
        val password: String = credentials["password"]!!
        if (pseudoDB.containsKey(username)) {
            pseudoDB[username] = password
        }
    }

    // TODO: Make a transition into cabinet
    private fun authorise() {
        showToast(this, "You have authorised")
    }
}
// This will go to the MainActivity, not finished
/*private fun updateSize(){
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        val res = resources
        val screenWidthString = screenWidth.toString() + "px"
        val screenHeightString = screenHeight.toString() + "px"
        val widthId = R.val.screen
    }*/