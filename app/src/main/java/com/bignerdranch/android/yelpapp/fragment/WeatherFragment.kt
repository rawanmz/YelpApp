package com.bignerdranch.android.yelpapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.yelpapp.R
import com.bignerdranch.android.yelpapp.data.Weather
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.databinding.FragmentListBinding
import com.bignerdranch.android.yelpapp.databinding.ListItemBinding
import com.bignerdranch.android.yelpapp.viewmodel.RestauratViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_weather.*


class WeatherFragment : Fragment() {


    private lateinit var restaurantViewModel: RestauratViewModel
    private var weather = emptyList<Weather>()
    //private var adapter = RestaurantsAdapter(restaurant)
     var myweather: Weather? =null
    private val navArgs by navArgs<ListFragmentArgs>()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_weather, container, false)

        restaurantViewModel = ViewModelProvider(this).get(RestauratViewModel::class.java)
        val s= navArgs.lat+","+navArgs.lon
                 restaurantViewModel.searchWeather( "$WEATHER_API_KEY",s)
        //title.text= myweather?.temp_c.toString()
            return view
        }
    }

