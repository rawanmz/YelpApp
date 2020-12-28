package com.bignerdranch.android.yelpapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DayPlanDao {

    @Query("SELECT * FROM dayplan_table ORDER BY yelpId ASC ")
    fun readDayPlanAllData(): LiveData<List<DayPlan>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDayPlanData(plan: DayPlan)

    @Delete
    suspend fun deletePlan(plan: DayPlan)

    @Update
    suspend fun updateData(plan: DayPlan)

    @Query("SELECT * FROM dayplan_table WHERE yelpId  = :valudId ")
    suspend fun searchPlanById(valudId: String): DayPlan
}