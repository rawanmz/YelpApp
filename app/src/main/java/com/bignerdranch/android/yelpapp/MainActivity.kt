package com.bignerdranch.android.yelpapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bignerdranch.android.yelpapp.fragment.CategoryFragment
import com.bignerdranch.android.yelpapp.fragment.DayPlanList
import com.bignerdranch.android.yelpapp.fragment.MapsFragment
import com.bignerdranch.android.yelpapp.fragment.SplashScreenActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNav)
        val navController = findNavController(R.id.warpper)
        bottomNavigation.setupWithNavController(navController)


    }
}