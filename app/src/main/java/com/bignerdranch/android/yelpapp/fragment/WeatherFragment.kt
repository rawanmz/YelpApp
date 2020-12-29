package com.bignerdranch.android.yelpapp.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.yelpapp.BuildConfig
import com.bignerdranch.android.yelpapp.R
import com.bignerdranch.android.yelpapp.ServiceLocator
import com.bignerdranch.android.yelpapp.data.Hour
import com.bignerdranch.android.yelpapp.database.DayPlan
import com.bignerdranch.android.yelpapp.viewmodel.MyViewModelFactory
import com.bignerdranch.android.yelpapp.viewmodel.RestauratViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.hour_item.view.*


class WeatherFragment : Fragment() {

    private lateinit var restaurantViewModel: RestauratViewModel
    private lateinit var restaurantViewModelFactory: MyViewModelFactory
    private val args by navArgs<WeatherFragmentArgs>()
    private lateinit var adapter: HourAdapter
    private lateinit var dayPlan: DayPlan

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        restaurantViewModelFactory =
            MyViewModelFactory(ServiceLocator.yelpResponse, ServiceLocator.weatherResponse)
        restaurantViewModel =
            ViewModelProvider(this, restaurantViewModelFactory).get(RestauratViewModel::class.java)
        val recyclerView = view.findViewById<RecyclerView>(R.id.constraintLayout2)
        adapter = HourAdapter()
        val myLayutManager = LinearLayoutManager(context)
        myLayutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = myLayutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
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
                    phoneCallImg.setOnClickListener {
                        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dayPlan.phone))
                        startActivity(intent)
                    }
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
                        adapter.setData(it.forecast.forecastday[0].hour)
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
                            getString(R.string.chance_of_rain ) + it.forecast.forecastday[0]
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
        return view
    }

    private inner class HourHolder(view: View) :
        RecyclerView.ViewHolder(view)

    private inner class HourAdapter : RecyclerView.Adapter<HourHolder>() {
        var hours = emptyList<Hour>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.hour_item, parent, false)
            return HourHolder(view)
        }
        override fun getItemCount(): Int = hours.size

        override fun onBindViewHolder(holder: HourHolder, position: Int) {
            val hour = hours[position]
            holder.itemView.hour1.text = hour.time.split(" ")[1]
            holder.itemView.temp1.text = hour.temp_c.toString() + getString(R.string.temp)
            holder.itemView.weatherdescription1.text = hour.condition.text
            Glide.with(holder.itemView.icon1)
                .load("https://${hour.condition.icon}")
                .apply(
                    RequestOptions().transforms(
                        CenterCrop(), RoundedCorners(20)
                    )
                ).into(holder.itemView.icon1)
        }
        fun setData(hours: List<Hour>) {
            this.hours = hours
            notifyDataSetChanged()
        }
    }
}
