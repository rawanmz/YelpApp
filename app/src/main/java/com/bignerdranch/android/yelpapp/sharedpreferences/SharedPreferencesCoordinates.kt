package com.bignerdranch.android.yelpapp.sharedpreferences

import android.content.Context
import com.bignerdranch.android.yelpapp.data.Coordinates
import com.google.gson.Gson

class SharedPreferencesCoordinates (context: Context){
    private val sharedPreferences = context.getSharedPreferences("myPreferences", 0)
    fun setCoordinate(key:String, coordinate: Coordinates){
        val prefsEditor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(coordinate)
        prefsEditor.putString("latlang", json)
        prefsEditor.apply()
    }
    fun getCoordinate(key:String): Coordinates{
        val gson = Gson()
        val json = sharedPreferences.getString("latlang", "")
        val obj = gson.fromJson(json, Coordinates::class.java)
        return obj
    }

}
