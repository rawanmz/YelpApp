package com.bignerdranch.android.yelpapp.repository

import com.bignerdranch.android.yelpapp.data.*

class FakeWeatherRepo : WeatherRepoInterface {
    val weather: Weather = Weather(
        Forecast(
            forecastday = listOf(
                Forecastday(
                    "date", Day(
                        2.2, 2.2,
                        Condition("icon", "text"), "20%"
                    ),
                    listOf(
                        Hour(
                            "chance", Condition("icon", "text"),
                            2.2, "00"
                        )
                    )
                )
            )
        )
    )

    override suspend fun searchWeather(key: String, q: String, days: String): Weather {
        return weather
    }

}