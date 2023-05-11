package com.example.finalproject.buypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.finalproject.basket.ProductBasket
import com.example.finalproject.buypage.Config
import com.example.finalproject.databinding.ActivityBuyPageBinding
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalPaymentDetails
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import com.paypal.android.sdk.payments.PaymentConfirmation
import org.json.JSONException
import java.math.BigDecimal

class BuyPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityBuyPageBinding
    private var config = PayPalConfiguration()
        .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
        .clientId(Config().CLIENT_ID)
    private var sum: Double = 0.0
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sum = intent.getDoubleExtra("sum", 0.0)
        binding.totalSum.text = "К оплате " + sum.toString()

        val intent = Intent(this, PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        startService(intent)

        binding.btnPayPal.setOnClickListener {
            processPayment()
        }
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == RESULT_OK) {
                val confirmation: PaymentConfirmation? = it.data?.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
                if(confirmation != null) {
                    try {
                        val paymentDetails = confirmation.toJSONObject().toString(4)
                        startActivity(Intent(this, PaymentDetails::class.java)
                            .putExtra("details", paymentDetails)
                            .putExtra("sum", sum))
                    }catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    setResult(RESULT_OK)
                }
            }else Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }

    private fun processPayment() {
        val payPalPay = PayPalPayment(BigDecimal(sum), "USD", "Donate to FinalProject", PayPalPayment.PAYMENT_INTENT_SALE)
        val intent = Intent(this, PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPay)
        launcher.launch(intent)
    }

    override fun onDestroy() {
        stopService(Intent(this, PayPalService::class.java))
        super.onDestroy()
    }
}