package com.bignerdranch.android.yelpapp.sharedpreferences


import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit

//private const val PREF_SEARCH_QUERY = "searchQuery"
//object QueryPreferences {
//    fun getStoredQuery(context: Context): String {
//        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
//        return prefs.getString(PREF_SEARCH_QUERY, "")!!
//    }
//    fun setStoredQuery(context: Context, query: String) {
//        PreferenceManager.getDefaultSharedPreferences(context)
//            .edit {
//                putString(PREF_SEARCH_QUERY, query)
//            }
//    }
//}