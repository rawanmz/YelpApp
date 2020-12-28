package com.bignerdranch.android.yelpapp.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.yelpapp.BuildConfig
import com.bignerdranch.android.yelpapp.R
import com.bignerdranch.android.yelpapp.ServiceLocator
import com.bignerdranch.android.yelpapp.database.DayPlan
import com.bignerdranch.android.yelpapp.viewmodel.MyViewModelFactory
import com.bignerdranch.android.yelpapp.viewmodel.RestauratViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_weather.*


class WeatherFragment : Fragment() {

    private lateinit var restaurantViewModel: RestauratViewModel
    private lateinit var restaurantViewModelFactory: MyViewModelFactory
    private val args by navArgs<WeatherFragmentArgs>()

    private lateinit var dayPlan: DayPlan

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        restaurantViewModelFactory =
            MyViewModelFactory(ServiceLocator.yelpResponse, ServiceLocator.weatherResponse)
        restaurantViewModel =
            ViewModelProvider(this, restaurantViewModelFactory).get(RestauratViewModel::class.java)
        bind()
        return view
    }

    @SuppressLint("SetTextI18n")
    fun bind() {

        val v = restaurantViewModel.searchRestaurantById(args.id).observe(viewLifecycleOwner,
            Observer {
                if (it != null) {
                    dayPlan = DayPlan(
                        it.yelpId, it.name, it.rating, it.phone,
                        it.is_closed, it.numReviews, it.distanceInMeters, it.imageUrl,
                        it.categories, it.coordinates, it.listDescription
                    )
                    placeName.text = it.name
                    phone.text = getString(R.string.phone) + it.phone
                    rate.rating = it.rating.toFloat()
                    reviews.text = it.numReviews.toString() + getString(R.string.reviews)
                    category.text = it.categories[0].title
                    Place_distance.text = it.displayDistance()
                    if (it.is_closed == true) {
                        is_close.text = getString(R.string.closed)
                        is_close.setTextColor(Color.RED)
                    } else {
                        is_close.text = getString(R.string.open)
                        is_close.setTextColor(Color.parseColor("#9AD9DC"))
                    }

                    Glide.with(placeImg)
                        .load(it.imageUrl).apply(
                            RequestOptions().transforms(
                                CenterCrop(), RoundedCorners(20)
                            )
                        ).into(placeImg)
                    addImage.setOnClickListener { view ->
                        restaurantViewModel.addDayPlan(dayPlan)
                        val action =
                            WeatherFragmentDirections.actionWeatherFragmentToDayPlanList()
                        findNavController().navigate(action)
                    }
                }
                val connectivityManager =
                    context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    restaurantViewModel.searchForecastWeather(
                        BuildConfig.WEATHER_API_KEY,
                        "${it.coordinates.latitude},${it.coordinates.longitude}",
                        "3"
                    ).observe(viewLifecycleOwner, Observer {
                        hour1.text = it.forecast.forecastday[0].hour[0].time.split(" ")[1]
                        temp1.text =
                            it.forecast.forecastday[0].hour[0].temp_c.toString() + getString(R.string.temp)
                        weatherdescription1.text = it.forecast.forecastday[0].hour[0].condition.text
                        Glide.with(icon1)
                            .load("https://${it.forecast.forecastday[0].hour[0].condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(icon1)

                        hour2.text = it.forecast.forecastday[0].hour[3].time.split(" ")[1]
                        temp2.text =
                            it.forecast.forecastday[0].hour[3].temp_c.toString() + getString(R.string.temp)
                        weatherdescription2.text = it.forecast.forecastday[0].hour[3].condition.text
                        Glide.with(icon2)
                            .load("https://${it.forecast.forecastday[0].hour[3].condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(icon2)

                        hour3.text = it.forecast.forecastday[0].hour[6].time.split(" ")[1]
                        temp3.text =
                            it.forecast.forecastday[0].hour[6].temp_c.toString() + getString(R.string.temp)
                        weatherdescription3.text = it.forecast.forecastday[0].hour[6].condition.text
                        Glide.with(icon3)
                            .load("https://${it.forecast.forecastday[0].hour[6].condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(icon3)

                        hour4.text = it.forecast.forecastday[0].hour[9].time.split(" ")[1]
                        temp4.text =
                            it.forecast.forecastday[0].hour[9].temp_c.toString() + getString(R.string.temp)
                        weatherdescription4.text = it.forecast.forecastday[0].hour[9].condition.text
                        Glide.with(icon4)
                            .load("https://${it.forecast.forecastday[0].hour[9].condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(icon4)

                        hour5.text = it.forecast.forecastday[0].hour[12].time.split(" ")[1]
                        temp5.text =
                            it.forecast.forecastday[0].hour[12].temp_c.toString() + getString(R.string.temp)
                        weatherdescription5.text =
                            it.forecast.forecastday[0].hour[12].condition.text
                        Glide.with(icon5)
                            .load("https://${it.forecast.forecastday[0].hour[12].condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(icon5)

                        hour6.text = it.forecast.forecastday[0].hour[15].time.split(" ")[1]
                        temp6.text =
                            it.forecast.forecastday[0].hour[15].temp_c.toString() + getString(R.string.temp)
                        weatherdescription6.text =
                            it.forecast.forecastday[0].hour[20].condition.text
                        Glide.with(icon6)
                            .load("https://${it.forecast.forecastday[0].hour[15].condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(icon6)

                        hour7.text = it.forecast.forecastday[0].hour[18].time.split(" ")[1]
                        temp7.text =
                            it.forecast.forecastday[0].hour[18].temp_c.toString() + getString(R.string.temp)
                        weatherdescription7.text =
                            it.forecast.forecastday[0].hour[18].condition.text
                        Glide.with(icon7)
                            .load("https://${it.forecast.forecastday[0].hour[18].condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(icon7)

                        hour8.text = it.forecast.forecastday[0].hour[21].time.split(" ")[1]
                        temp8.text =
                            it.forecast.forecastday[0].hour[21].temp_c.toString() + getString(R.string.temp)
                        weatherdescription8.text =
                            it.forecast.forecastday[0].hour[21].condition.text
                        Glide.with(icon8)
                            .load("https://${it.forecast.forecastday[0].hour[21].condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(icon8)

                        day1.text = it.forecast.forecastday[0].date
                        Glide.with(day_icon1)
                            .load("https://${it.forecast.forecastday[0].day.condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(day_icon1)
                        daytemp1.text =
                            it.forecast.forecastday[0].day.avgtemp_c.toString() + getString(R.string.temp_) +
                                    it.forecast.forecastday[0].day.avgtemp_f.toString() + getString(
                                R.string.f
                            )
                        dayweatherdescription1.text =
                            getString(R.string.chance_of_rain) + it.forecast.forecastday[0]
                                .day.daily_chance_of_rain + getString(R.string.percentage)

                        day2.text = it.forecast.forecastday[1].date
                        Glide.with(day_icon2)
                            .load("https://${it.forecast.forecastday[1].day.condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(day_icon2)
                        daytemp2.text =
                            it.forecast.forecastday[1].day.avgtemp_c.toString() + getString(R.string.temp_) +
                                    it.forecast.forecastday[1].day.avgtemp_f.toString() +
                                    getString(R.string.f)
                        dayweatherdescription2.text =
                            getString(R.string.chance_of_rain) + it.forecast.forecastday[1].day.daily_chance_of_rain +
                                    getString(R.string.percentage)

                        day3.text = it.forecast.forecastday[2].date
                        Glide.with(day_icon3)
                            .load("https://${it.forecast.forecastday[2].day.condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(day_icon3)
                        daytemp3.text =
                            it.forecast.forecastday[2].day.avgtemp_c.toString() + getString(R.string.temp_) +
                                    it.forecast.forecastday[2].day.avgtemp_f.toString() +
                                    getString(R.string.f)
                        dayweatherdescription3.text =
                            getString(R.string.chance_of_rain) + it.forecast.forecastday[2].day.daily_chance_of_rain +
                                    getString(R.string.percentage)
                    })
                } else {
                    AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.no_internet))
                        .setMessage(getString(R.string.check_internet))
                        .setCancelable(true)
                        .setIcon(R.drawable.no_signal)
                        .show()
                    Toast.makeText(context, R.string.no_connection, Toast.LENGTH_LONG).show()
                }
            })
    }
}
