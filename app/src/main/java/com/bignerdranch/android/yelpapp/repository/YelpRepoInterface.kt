package com.bignerdranch.android.yelpapp.repository

import androidx.lifecycle.LiveData
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.database.DayPlan

interface YelpRepoInterface {
    val readAllData: LiveData<List<YelpRestaurant>>
    suspend fun searchRestaurants(
        auth: String,
        term: String,
        lat: String,
        lon:String
    ): List<YelpRestaurant>
    suspend fun searchRestaurantById(key:String): YelpRestaurant
    val readAllDayPlan : LiveData<List<DayPlan>>
    suspend fun addDayPlan(plan: DayPlan)
    suspend fun deleteDayPlan(plan: DayPlan)
    suspend fun updateDayPlan(plan: DayPlan)
    suspend fun searchPlanById(key:String):DayPlan
}
