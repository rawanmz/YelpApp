package com.bignerdranch.android.yelpapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "restaurant_table")
data class YelpRestaurant(
    @PrimaryKey() @SerializedName("id") val yelpId: String,
    val name: String,
    val rating: Double,
    val phone: String,
    val is_closed: Boolean,
    @SerializedName("review_count") val numReviews: Int,
    @SerializedName("distance") val distanceInMeters: Double,
    @SerializedName("image_url") val imageUrl: String,
    val categories: List<YelpCategory>,
    val coordinates: Coordinates,
    var listDescription: String? = " "

) {
    fun displayDistance(): String {
        val milesPerMeter = 0.000621371
        val distanceInMiles = "%.2f".format(distanceInMeters * milesPerMeter)
        return "$distanceInMiles mi"
    }
}
