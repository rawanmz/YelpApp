package com.bignerdranch.android.yelpapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.yelpapp.R
import com.bignerdranch.android.yelpapp.viewmodel.RestauratViewModel


class WeatherFragment : Fragment() {

    private lateinit var restaurantViewModel: RestauratViewModel
    private val args by navArgs<WeatherFragmentArgs>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        //weather= Weather(33.3, Condition(text = "sss",icon = "ffff"))
        val view= inflater.inflate(R.layout.fragment_weather, container, false)
        restaurantViewModel = ViewModelProvider(this).get(RestauratViewModel::class.java)
                     bind()
            return view
        }
    fun bind() {
//        restaurantViewModel.searchForecastWeather(WEATHER_API_KEY,args.+","+args.lon,"3")
//        .observe(viewLifecycleOwner, Observer {
//            temp.text=it.current.temp_c.toString()+"°C"
//            desc.text=it.current.condition.text
//            town.text=it.location.country
//            town.text=it.forecast.forecastday[0].day.avgtemp_c.toString()
//            Log.d("rawan",it.current.condition.icon)
//            Glide.with(weather_image)
//                .load("https://"+it.current.condition.icon)
//                .into(weather_image)
//            Toast.makeText(context,it.toString(), Toast.LENGTH_LONG).show()
//            println("*************************************************************************$it")
//            Log.d("tag",it.toString())
//        })
    }
    }

