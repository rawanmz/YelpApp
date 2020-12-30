package com.bignerdranch.android.yelpapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.yelpapp.data.Coordinates
import com.bignerdranch.android.yelpapp.data.YelpCategory
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.database.DayPlan

class FakeYelpRepo : YelpRepoInterface {
    private val yelpRestaurantList = mutableListOf<YelpRestaurant>()
    private val dayPlanList = mutableListOf<DayPlan>()
    private val dayPlanLiveData = MutableLiveData<List<DayPlan>>()
    private val mutableLiveDataYelp = MutableLiveData<List<YelpRestaurant>>()

    fun add(yelpRestaurant: YelpRestaurant) {
        yelpRestaurantList.add(yelpRestaurant)
        mutableLiveDataYelp.value = yelpRestaurantList

    }

    fun addPlan(plan: DayPlan) {
        dayPlanList.add(plan)
        dayPlanLiveData.value = dayPlanList

    }

    override val readAllData: LiveData<List<YelpRestaurant>> = mutableLiveDataYelp

    override suspend fun searchRestaurants(
        auth: String,
        term: String,
        lat: String,
        lon: String
    ): List<YelpRestaurant> {
        return yelpRestaurantList
    }

    override suspend fun searchRestaurantById(key: String): YelpRestaurant {
        var yelpRestaurant = YelpRestaurant(
            "", "", 3.3, "", false, 1, 4.4, "", listOf(YelpCategory("title")),

            Coordinates(0.0, 0.0), ""
        )
        yelpRestaurantList.forEach {
            if (key == it.yelpId) {
                yelpRestaurant = it
            }
        }
        return yelpRestaurant
    }

    override val readAllDayPlan: LiveData<List<DayPlan>> = dayPlanLiveData

    override suspend fun addDayPlan(plan: DayPlan) {
        dayPlanList.add(plan)
        dayPlanLiveData.value = dayPlanList
    }

    override suspend fun deleteDayPlan(plan: DayPlan) {
        dayPlanList.remove(plan)
        dayPlanLiveData.value = dayPlanList
    }

    override suspend fun updateDayPlan(plan: DayPlan) {
        var index = 0
        dayPlanList.forEach {
            if (it.yelpId == plan.yelpId) {
                dayPlanList.set(index, plan)
            }
            index += 1
        }
    }

    override suspend fun searchPlanById(key: String): DayPlan {
        var dayPlan: DayPlan? = null
        dayPlanList.forEach {
            if (key == it.yelpId) {
                dayPlan = it
            }
        }
        return dayPlan as DayPlan
    }
}