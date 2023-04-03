package com.example.finalproject

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
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
        myButtons = findViewById(R.id.buttonsFrameLayout)
        myLoginButton = findViewById(R.id.loginButtonWelcomePage)
        myRegisterButton = findViewById(R.id.registerButtonWelcomePage)


        appearLogo()
        moveLogo()
        appearTextLogo()
        startButtons()

        myRegisterButton.setOnClickListener {
            intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
        myLoginButton.setOnClickListener {
            intent = Intent(this, Authorisation::class.java)
            startActivity(intent)
        }

    }
    private val durationOfLogo: Long = 5000
    private val durationOfText: Long = 2000
    private val durationOfAnimUp: Long = 1500
    private val durationOfAnimDown: Long = 500
    private val durationOfAppearText: Long = 3000
    private val durationOfAppearButtons: Long = 1000

    private val delayOfLogoToMoveUp: Long = 5500
    private val delayOfLogoToMoveDown: Long = delayOfLogoToMoveUp + 1500
    private val delayOfTextToAppear: Long = delayOfLogoToMoveDown
    private val delayOfButtonsToAppear: Long = delayOfTextToAppear + 1000
    private fun appearLogo(){
        myLogo.alpha = 0f
        myLogo.visibility = View.VISIBLE
        val animatorLogo = ObjectAnimator.ofFloat(myLogo, "alpha", 0f, 1f)
        animatorLogo.duration = durationOfLogo
        animatorLogo.start()
    }

    private fun moveLogo(){
        val animateTextUp = ObjectAnimator.ofFloat(myLogoText,"translationY", 0f, -700f)
        animateTextUp.duration = 1
        animateTextUp.start()

        val animateButtonsUp = ObjectAnimator.ofFloat(myButtons,"translationY", 0f, -700f)
        myLogoText.alpha = 0f
        animateButtonsUp.duration = 1
        animateButtonsUp.start()

        val animateUp = ObjectAnimator.ofFloat(myLogo, "translationY", 0f, -750f)
        animateUp.duration = durationOfAnimUp // 1000
        animateUp.startDelay = delayOfLogoToMoveUp // 5000
        animateUp.start()

        val animateDown = ObjectAnimator.ofFloat(myLogo, "translationY", -750f, -700f)
        animateDown.startDelay = delayOfLogoToMoveDown //7000
        animateDown.duration = durationOfAnimDown //500
        animateDown.start()
    }
    private fun appearTextLogo(){
        myLogoText.alpha = 0f
        val animator = ValueAnimator.ofFloat(-150f, 0f).apply {
            duration = durationOfText
            addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                myLogoText.translationX = value
            }
        }
        animator.startDelay = delayOfTextToAppear

        val animatorTextAppear = ObjectAnimator.ofFloat(myLogoText, "alpha", 0f, 1f)
        animatorTextAppear.duration = durationOfAppearText
        animatorTextAppear.startDelay = delayOfTextToAppear

        animator.start()
        animatorTextAppear.start()

    }
    private fun startButtons(){
        myButtons.alpha = 0f
        val animatorButtons = ObjectAnimator.ofFloat(myButtons, "alpha", 0f, 1f)
        animatorButtons.duration = durationOfAppearButtons
        animatorButtons.startDelay = delayOfButtonsToAppear
        animatorButtons.start()

    }
}