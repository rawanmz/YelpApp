package com.bignerdranch.android.yelpapp.repository

import androidx.lifecycle.LiveData
import com.bignerdranch.android.yelpapp.api.YelpApi
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.database.DayPlan
import com.bignerdranch.android.yelpapp.database.DayPlanDao
import com.bignerdranch.android.yelpapp.database.YelpDao

class YelpRepo(
    private val yelpApi: YelpApi, private val resDao: YelpDao,
    private val dayPlanDao: DayPlanDao
) : YelpRepoInterface {

    override val readAllData: LiveData<List<YelpRestaurant>> = resDao.readAllData()

    override suspend fun searchRestaurants(
        auth: String,
        term: String,
        lat: String,
        lon: String
    ): List<YelpRestaurant> {
        resDao.deleteRestaurant()
        val restaurant = yelpApi.searchRestaurants(auth, term, lat, lon).restaurants

        resDao.addData(
            *restaurant.map {
                YelpRestaurant(
                    it.yelpId,
                    it.name,
                    it.rating,
                    it.phone,
                    it.is_closed,
                    it.numReviews,
                    it.distanceInMeters,
                    it.imageUrl,
                    it.categories,
                    it.coordinates,
                    it.listDescription
                )
            }.toTypedArray()
        )
        return restaurant
    }

    override suspend fun searchRestaurantById(key: String): YelpRestaurant {
        return resDao.searchResturantById(key)
    }

    override val readAllDayPlan: LiveData<List<DayPlan>> = dayPlanDao.readDayPlanAllData()

    override suspend fun addDayPlan(plan: DayPlan) {
        dayPlanDao.addDayPlanData(plan)
    }

    override suspend fun deleteDayPlan(plan: DayPlan) {
        dayPlanDao.deletePlan(plan)
    }

    override suspend fun updateDayPlan(plan: DayPlan) {
        dayPlanDao.updateData(plan)
    }

    override suspend fun searchPlanById(key: String): DayPlan {
        return dayPlanDao.searchPlanById(key)
    }
}
