package com.example.finalproject

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var myLogo: ImageView
    private lateinit var myLogoText: ImageView
    private lateinit var myLoginButton: Button
    private lateinit var myRegisterButton: Button
    private lateinit var myButtons: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myLogo = findViewById(R.id.logoView)
        myLogoText = findViewById(R.id.logotextview)
        myLoginButton = findViewById(R.id.loginButtonWelcomePage)
        myRegisterButton = findViewById(R.id.registerButtonWelcomePage)
        myButtons = findViewById(R.id.buttonsFrameLayout)
        startLogo()
        moveLogo()
        startTextLogo()
        startButtons()
    }

    private fun startTextLogo(){
        myLogoText.alpha = 0f
        val animator = ValueAnimator.ofFloat(-150f, 0f).apply {
            duration = 2000
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                myLogoText.translationX = value
            }
        }

        val animatorLogoAppear = ObjectAnimator.ofFloat(myLogoText, "alpha", 0f, 1f)
        animatorLogoAppear.duration = 3000
        animatorLogoAppear.startDelay = 7000


        animator.startDelay = 7000
        animator.start()
        animatorLogoAppear.start()




    }
    private fun moveLogo(){

        val animateUp = ObjectAnimator.ofFloat(myLogo, "translationY", 0f, -750f)
        val animateTextUp = ObjectAnimator.ofFloat(myLogoText,"translationY", 0f, -700f)
        val animateButtonsUp = ObjectAnimator.ofFloat(myButtons,"translationY", 0f, -700f)
        myLogoText.alpha = 0f

        val dur : Long = 1500 //mlsec

        animateTextUp.duration = 1
        animateButtonsUp.duration = 1
        animateButtonsUp.start()
        animateTextUp.start()
        animateUp.duration = dur // 1000
        animateUp.startDelay = 5500
        animateUp.start()

        val animateDown = ObjectAnimator.ofFloat(myLogo, "translationY", -750f, -700f)
        animateDown.startDelay = 7000 // 1000
        animateDown.duration = dur/2 // 500

        animateDown.start()
    }
    private fun startLogo(){
        myLogo.alpha = 0f
        myLogo.visibility = View.VISIBLE
        val animatorLogo = ObjectAnimator.ofFloat(myLogo, "alpha", 0f, 1f)
        animatorLogo.duration = 5000
        animatorLogo.start()
    }

    private fun startButtons(){
        myButtons.alpha = 0f
        val animatorButtons = ObjectAnimator.ofFloat(myButtons, "alpha", 0f, 1f)
        animatorButtons.duration = 1000
        animatorButtons.startDelay = 8000
        animatorButtons.start()

    }
}