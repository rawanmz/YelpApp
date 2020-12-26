package com.bignerdranch.android.yelpapp.sharedpreferences

import android.content.Context
import com.bignerdranch.android.yelpapp.data.Coordinates
import com.google.gson.Gson

class SharedPreferencesCoordinates (context: Context){
    private val sharedPreferences = context.getSharedPreferences("myPreferences", 0)
    fun setCoordinate(key:String, coordinate: Coordinates){
        val prefsEditor = sharedPreferences.edit()
        var gson = Gson()
        var json = gson.toJson(coordinate)
        prefsEditor.putString("latlang", json)
        prefsEditor.commit()
    }
    fun getCoordinate(key:String): Coordinates{
        var gson = Gson()
        var json = sharedPreferences.getString("latlang", "")
        var obj = gson.fromJson(json, Coordinates::class.java)
        return obj
    }

}
