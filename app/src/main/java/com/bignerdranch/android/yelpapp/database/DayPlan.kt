package com.bignerdranch.android.yelpapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bignerdranch.android.yelpapp.data.Coordinates
import com.bignerdranch.android.yelpapp.data.YelpCategory

@Entity(tableName = "dayplan_table")
class DayPlan(
    @PrimaryKey()
    val yelpId: String,
    var name: String,
    var rating: Double,
    var phone: String,
    var is_closed: Boolean,
    var numReviews: Int,
    var distanceInMeters: Double, val imageUrl: String,
    var categories: List<YelpCategory>,
    var coordinates: Coordinates,
    var listDescription: String? = " "

) {
    fun displayDistance(): String {
        val milesPerMeter = 0.000621371
        val distanceInMiles = "%.2f".format(distanceInMeters * milesPerMeter)
        return "$distanceInMiles mi"
    }
}

