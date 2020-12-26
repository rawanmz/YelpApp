package com.bignerdranch.android.yelpapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
@Dao
interface DayPlanDao {

    @Query("SELECT * FROM dayplan_table ORDER BY yelpId ASC ")
    fun readDayPlanAllData(): LiveData<List<DayPlan>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDayPlanData( plan: DayPlan)

    @Delete
    suspend fun deletePlan(plan: DayPlan)
}