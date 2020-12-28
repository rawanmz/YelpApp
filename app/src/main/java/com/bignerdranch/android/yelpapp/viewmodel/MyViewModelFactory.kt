package com.bignerdranch.android.yelpapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.yelpapp.repository.WeatherRepo
import com.bignerdranch.android.yelpapp.repository.YelpRepo

class MyViewModelFactory(private var yelpRepo: YelpRepo,private var weatherRepo: WeatherRepo)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
         if (modelClass.isAssignableFrom(RestauratViewModel::class.java)) {
           return RestauratViewModel(yelpRepo,weatherRepo) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}