package com.bignerdranch.android.yelpapp.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.yelpapp.MainActivity
import com.bignerdranch.android.yelpapp.R
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        imageView2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anmation))
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 1500)
    }
}
