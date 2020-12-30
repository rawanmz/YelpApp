package com.bignerdranch.android.yelpapp.search

import android.content.SearchRecentSuggestionsProvider

class SuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.bignerdranch.android.yelpapp.search.SuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}