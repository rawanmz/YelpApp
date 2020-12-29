package com.bignerdranch.android.yelpapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.yelpapp.ServiceLocator
import com.bignerdranch.android.yelpapp.data.Weather
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.database.DayPlan
import com.bignerdranch.android.yelpapp.repository.WeatherRepoInterface
import com.bignerdranch.android.yelpapp.repository.YelpRepoInterface
import kotlinx.coroutines.launch

class RestauratViewModel(
    var yelpRepo: YelpRepoInterface = ServiceLocator.yelpResponse,
    var weatherRepo: WeatherRepoInterface = ServiceLocator.weatherResponse
) : ViewModel() {
    var weather = MutableLiveData<Weather>()
    val readAll: LiveData<List<YelpRestaurant>> = yelpRepo.readAllData
    val restaurantsList = MutableLiveData<List<YelpRestaurant>>()
    val restaurantItem = MutableLiveData<YelpRestaurant>()
    val dayPlan = MutableLiveData<DayPlan>()

    private val mutableSearchTerm = mutableListOf<String>()
    val searchTerm: String
        get() = (mutableSearchTerm ?: "").toString()

    fun searchRestaurant(auth: String, search: String, lat: String, lon: String)
            : LiveData<List<YelpRestaurant>> {
        //  QueryPreferences.setStoredQuery(app, search)
        //  mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)
        viewModelScope.launch {
            restaurantsList.value = yelpRepo.searchRestaurants(auth, search, lat, lon)
            mutableSearchTerm.add(search)

        }
        return restaurantsList
    }

    fun searchForecastWeather(key: String, latlon: String, days: String): LiveData<Weather> {
        viewModelScope.launch {
            weather.value = weatherRepo.searchWeather(key, latlon, days)
        }
        return weather
    }

    fun searchRestaurantById(id: String): MutableLiveData<YelpRestaurant> {
        viewModelScope.launch {
            restaurantItem.value = yelpRepo.searchRestaurantById(id)
        }
        return restaurantItem
    }

    val planDays: LiveData<List<DayPlan>> = yelpRepo.readAllDayPlan

    fun addDayPlan(plan: DayPlan) {
        viewModelScope.launch {
            yelpRepo.addDayPlan(plan)
        }
    }

    fun deleteDayPlan(plan: DayPlan) {
        viewModelScope.launch {
            yelpRepo.deleteDayPlan(plan)
        }
    }

    fun updateDayPlan(plan: DayPlan) {
        viewModelScope.launch {
            yelpRepo.updateDayPlan(plan)
        }
    }

    fun searchDayPlan(planId: String): MutableLiveData<DayPlan> {
        viewModelScope.launch {
            dayPlan.value = yelpRepo.searchPlanById(planId)
        }
        return dayPlan
    }
}