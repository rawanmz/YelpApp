package com.bignerdranch.android.yelpapp

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bignerdranch.android.yelpapp.viewmodel.RestauratViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
val v:RestauratViewModel
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNav)
        val navController = findNavController(R.id.warpper)
        bottomNavigation.setupWithNavController(navController)
// Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                (query)
            }
        }

    }
}