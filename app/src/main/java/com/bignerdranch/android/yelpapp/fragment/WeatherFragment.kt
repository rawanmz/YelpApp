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
import com.bignerdranch.android.yelpapp.data.Condition
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

   private lateinit var weather :Weather
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        weather= Weather(33.3, Condition(text = "sss",icon = "ffff"))
        val view= inflater.inflate(R.layout.fragment_weather, container, false)
        restaurantViewModel = ViewModelProvider(this).get(RestauratViewModel::class.java)
        bind(weather)
            return view
        }
    fun bind(weather: Weather) {
        val v=temp.setText( weather.temp_c.toString())
        desc.text=weather.condition.text

        Glide.with(wheather_image).load(weather.condition.icon).apply(
            RequestOptions().transforms(
                CenterCrop(), RoundedCorners(20)
            )
        ).into(wheather_image)
    }
    }

