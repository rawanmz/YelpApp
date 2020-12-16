package com.bignerdranch.android.yelpapp

import android.content.Context
import com.bignerdranch.android.yelpapp.api.YelpApi
import com.bignerdranch.android.yelpapp.repository.YelpRepo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    private lateinit var app: App
    lateinit var retrofit: Retrofit
    private lateinit var RemoteSource: YelpApi
    fun init(app: App) {
        this.app = app
        initializeNetwork(app)
    }

//    private fun getOkhttpClient(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(RestaurantInterceptor())
//            .build()
//    }

    private fun initializeNetwork(context: Context) {
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.yelp.com/v3/")
          // .client(getOkhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        RemoteSource = retrofit.create(YelpApi::class.java)
    }


    val yelpResponse:YelpRepo by lazy {
        YelpRepo(RemoteSource)
    }
}
