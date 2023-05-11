package com.example.finalproject.buypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalproject.databinding.ActivityPaymentDetailsBinding
import org.json.JSONException
import org.json.JSONObject

class PaymentDetails : AppCompatActivity() {
    lateinit var binding: ActivityPaymentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            val jsonObject = JSONObject(intent.getStringExtra("details"))
            showDetails(jsonObject.getJSONObject("response"), intent.getDoubleExtra("sum", 0.0))
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showDetails(response: JSONObject, sum: Double) {
        try {
            binding.apply {
                textID.text = response.getString("id")
                textStatus.text = response.getString("state")
                textSum.text = sum.toString() + "$"
            }
        }catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}