package com.bignerdranch.android.yelpapp.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.yelpapp.MainActivity
import com.bignerdranch.android.yelpapp.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        },2000)
        }
    }
