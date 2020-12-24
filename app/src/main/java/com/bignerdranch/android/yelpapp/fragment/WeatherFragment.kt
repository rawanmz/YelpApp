package com.bignerdranch.android.yelpapp.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.yelpapp.R
import com.bignerdranch.android.yelpapp.viewmodel.RestauratViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_weather.*


class WeatherFragment : Fragment() {

    private lateinit var restaurantViewModel: RestauratViewModel
    private val args by navArgs<WeatherFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        restaurantViewModel = ViewModelProvider(this).get(RestauratViewModel::class.java)
        bind()
        return view
    }

    @SuppressLint("SetTextI18n")
    fun bind() {
        restaurantViewModel.searchResturantById(args.id).observe(viewLifecycleOwner,
            Observer {
                if (it != null) {
                    placeName.text = it.name

                    phone.text = "Phone: " + it.phone
                    rate.rating = it.rating.toFloat()
                    reviews.text = it.numReviews.toString() + " Reviews"
                    category.text = it.categories[0].title
                    Place_distance.text=it.displayDistance()
                    if(it.is_closed==true) {
                        is_close.text="Closed"
                        is_close.setTextColor(Color.RED)
                    }else{
                           is_close.text="Open"
                        is_close.setTextColor(Color.BLUE)
                        }

                    Glide.with(placeImg)
                        .load(it.imageUrl).apply(
                            RequestOptions().transforms(
                                CenterCrop(), RoundedCorners(20)
                            )
                        ).into(placeImg)
                }
                    restaurantViewModel.searchForcastWeather(
                        WEATHER_API_KEY,
                        "${it.coordinates.latitude},${it.coordinates.longitude}",
                        "3"
                    ).observe(viewLifecycleOwner, Observer {
                        hour1.text = it.forecast.forecastday[0].hour[0].time.split(" ")[1]
                        temp1.text = it.forecast.forecastday[0].hour[0].temp_c.toString() + "°C"
                        weatherdescription1.text = it.forecast.forecastday[0].hour[0].condition.text
                        Glide.with(icon1)
                            .load("https://${it.forecast.forecastday[0].hour[0].condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(icon1)

                        hour2.text = it.forecast.forecastday[0].hour[3].time.split(" ")[1]
                        temp2.text = it.forecast.forecastday[0].hour[3].temp_c.toString() + "°C"
                        weatherdescription2.text = it.forecast.forecastday[0].hour[3].condition.text
                        Glide.with(icon2)
                            .load("https://${it.forecast.forecastday[0].hour[3].condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(icon2)

                        hour3.text = it.forecast.forecastday[0].hour[6].time.split(" ")[1]
                        temp3.text = it.forecast.forecastday[0].hour[6].temp_c.toString() + "°C"
                        weatherdescription3.text = it.forecast.forecastday[0].hour[6].condition.text
                        Glide.with(icon3)
                            .load("https://${it.forecast.forecastday[0].hour[6].condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(icon3)

                        hour4.text = it.forecast.forecastday[0].hour[9].time.split(" ")[1]
                        temp4.text = it.forecast.forecastday[0].hour[9].temp_c.toString() + "°C"
                        weatherdescription4.text = it.forecast.forecastday[0].hour[9].condition.text
                        Glide.with(icon4)
                            .load("https://${it.forecast.forecastday[0].hour[9].condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )
                            ).into(icon4)
                        hour5.text = it.forecast.forecastday[0].hour[12].time.split(" ")[1]
                        temp5.text = it.forecast.forecastday[0].hour[12].temp_c.toString() + "°C"
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
                        temp6.text = it.forecast.forecastday[0].hour[15].temp_c.toString() + "°C"
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
                        temp7.text = it.forecast.forecastday[0].hour[18].temp_c.toString() + "°C"
                        weatherdescription7.text =
                            it.forecast.forecastday[0].hour[18].condition.text
                        Glide.with(icon7)
                            .load("https://${it.forecast.forecastday[0].hour[18].condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )).into(icon7)

                        hour8.text = it.forecast.forecastday[0].hour[21].time.split(" ")[1]
                        temp8.text = it.forecast.forecastday[0].hour[21].temp_c.toString() + "°C"
                        weatherdescription8.text =
                            it.forecast.forecastday[0].hour[21].condition.text
                        Glide.with(icon8)
                            .load("https://${it.forecast.forecastday[0].hour[21].condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )).into(icon8)

                        day1.text=it.forecast.forecastday[0].date
                        Glide.with(day_icon1)
                            .load("https://${it.forecast.forecastday[0].day.condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )).into(day_icon1)
                        daytemp1.text = it.forecast.forecastday[0].day.avgtemp_c.toString() + "°C /"+
                                it.forecast.forecastday[0].day.avgtemp_f.toString()+"°F"
                        dayweatherdescription1.text = "Daily Chance of Rain "+it.forecast.forecastday[0].day.daily_chance_of_rain+"%"


                        day2.text=it.forecast.forecastday[1].date
                        Glide.with(day_icon2)
                            .load("https://${it.forecast.forecastday[1].day.condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )).into(day_icon2)
                        daytemp2.text = it.forecast.forecastday[1].day.avgtemp_c.toString() + "°C /"+
                                it.forecast.forecastday[1].day.avgtemp_f.toString()+"°F"
                        dayweatherdescription2.text = "Daily Chance of Rain "+it.forecast.forecastday[1].day.daily_chance_of_rain+"%"


                        day3.text=it.forecast.forecastday[2].date
                        Glide.with(day_icon3)
                            .load("https://${it.forecast.forecastday[2].day.condition.icon}")
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(), RoundedCorners(20)
                                )).into(day_icon3)
                        daytemp3.text = it.forecast.forecastday[2].day.avgtemp_c.toString() + "°C /"+
                                it.forecast.forecastday[2].day.avgtemp_f.toString()+"°F"
                        dayweatherdescription3.text = "Daily Chance of Rain "+it.forecast.forecastday[2].day.daily_chance_of_rain+"%"




                    })

            })


    }
}
        //                        weather_des.weatherDes.text=weather?.condition?.text
        //            binding.weather.text = weather?.temp_c.toString()+"°C"
        //            val x= "https://${weather?.condition?.icon}"
        //            if (x=="https://null"){
        //                binding.weatherIcon.setImageResource(R.drawable.cloud)
        //            }else{
        //                Glide.with(binding.weatherIcon)
        //                    .load(x).apply(
        //                        RequestOptions().transforms(
        //                            CenterCrop(), RoundedCorners(20)erIcon)
            //        //            }
            //            })
            //    }
            //    }
        //                        )).into(binding.weath