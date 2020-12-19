package com.bignerdranch.android.yelpapp

import android.content.Context
import androidx.room.Room
import com.bignerdranch.android.yelpapp.api.WeatherApi
import com.bignerdranch.android.yelpapp.api.YelpApi
import com.bignerdranch.android.yelpapp.database.YelpDatabase
import com.bignerdranch.android.yelpapp.repository.WeatherRepo
import com.bignerdranch.android.yelpapp.repository.YelpRepo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    private lateinit var app: App
    lateinit var retrofit: Retrofit
    lateinit var resDatabase: YelpDatabase
    private lateinit var RemoteSource: YelpApi
    private lateinit var weatherApi: WeatherApi
    fun init(app: App) {
        this.app = app
        initializeDatabase(app)
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
    private fun initializeDatabase(context: Context) {
        resDatabase = Room.databaseBuilder(
            context,
            YelpDatabase::class.java,
            "db"
        ).build()
    }
    val yelpResponse: YelpRepo by lazy {
        YelpRepo(RemoteSource, resDatabase.dao())
    }
    val weatherResponse: WeatherRepo by lazy {
        WeatherRepo(weatherApi)
    }

}
