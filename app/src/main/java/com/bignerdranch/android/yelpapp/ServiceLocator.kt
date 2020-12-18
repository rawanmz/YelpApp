package com.bignerdranch.android.yelpapp

import android.content.Context
import com.bignerdranch.android.yelpapp.api.WeatherApi
import com.bignerdranch.android.yelpapp.api.YelpApi
import com.bignerdranch.android.yelpapp.repository.WeatherRepo
import com.bignerdranch.android.yelpapp.repository.YelpRepo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    private lateinit var app: App
    lateinit var retrofit: Retrofit
    private lateinit var RemoteSource: YelpApi
    private lateinit var weatherApi: WeatherApi
    fun init(app: App) {
        this.app = app
        initializeNetworkYelp(app)
        initializeNetworkWeather(app)
    }

    private fun initializeNetworkYelp(context: Context) {
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.yelp.com/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        RemoteSource = retrofit.create(YelpApi::class.java)
    }
    private fun initializeNetworkWeather(context: Context) {
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    val yelpResponse: YelpRepo by lazy {
        YelpRepo(RemoteSource)
    }
    val weatherResponse: WeatherRepo by lazy {
        WeatherRepo(weatherApi)
    }

}
