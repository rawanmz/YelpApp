package com.bignerdranch.android.yelpapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.bignerdranch.android.yelpapp.ServiceLocator
import com.bignerdranch.android.yelpapp.data.Weather
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.sharedpreferences.QueryPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RestauratViewModel(private val app: Application)
    : AndroidViewModel(app) {
    var weather = MutableLiveData<Weather>()
    var forcast = MutableLiveData<Weather.Forecast.Forecastday>()
    var yelpRepo = ServiceLocator.yelpResponse
    var weatherpRepo = ServiceLocator.weatherResponse
    val readAll:LiveData<List<YelpRestaurant>> = yelpRepo.readAllData
    val restaurantslist = MutableLiveData<List<YelpRestaurant>>()
    val restaurantItem = MutableLiveData<YelpRestaurant>()

    private val mutableSearchTerm = MutableLiveData<String>()
    val searchTerm: String
        get() = mutableSearchTerm.value ?: ""

    fun searchRestaurant(auth: String, search: String, lat: String,lon:String)
            : LiveData<List<YelpRestaurant>> {
        QueryPreferences.setStoredQuery(app, search)
       mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)
        viewModelScope.launch {
            restaurantslist.value = yelpRepo.searchRestaurants(auth, search, lat, lon)
            mutableSearchTerm.value = search

        }

        return restaurantslist
    }
    fun searchForcastWeather(key : String,latlon: String,days:String):LiveData<Weather>{
        viewModelScope.launch {
            weather.value = weatherpRepo.searchtWeather(key,latlon,days)
        }
        return weather
    }
    fun searchResturantById(id:String): MutableLiveData<YelpRestaurant> {
        viewModelScope.launch {
          restaurantItem.value=yelpRepo.searchResturantById(id)
        }
        return restaurantItem
    }
//    fun addWeather(weather:Weather) {
//        viewModelScope.launch {
//            weatherpRepo.addWather(weather)
//        }
//    }
//    fun getWeather(weatherID:String): LiveData<Weather> {
//        viewModelScope.launch {
//            weather.value=weatherpRepo.readAllWeatherData(weatherID)
//        }
//        return weather
//    }
}