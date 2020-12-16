package com.bignerdranch.android.yelpapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.yelpapp.ServiceLocator
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import kotlinx.coroutines.launch

class RestauratViewModel : ViewModel(){

    var yelpRepo= ServiceLocator.yelpResponse

    fun searchRestaurant(auth:String ,search: String, location: String)
            : LiveData<List<YelpRestaurant>> {
        val restaurantslist= MutableLiveData<List<YelpRestaurant>>()
        viewModelScope.launch {
            restaurantslist.value= yelpRepo.searchRestaurants(auth,search,location)
        }
        return restaurantslist
    }
    fun searchLocation(auth:String ,lat: String, lon: String)
            : LiveData<List<YelpRestaurant>> {
        val locationslist= MutableLiveData<List<YelpRestaurant>>()
        viewModelScope.launch {
            locationslist.value= yelpRepo.searchLocation(auth,lat,lon)
        }
        return locationslist
    }
}