package com.bignerdranch.android.yelpapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.yelpapp.R
import com.bignerdranch.android.yelpapp.viewmodel.RestauratViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_weather.*


class WeatherFragment : Fragment() {

    private lateinit var restaurantViewModel: RestauratViewModel
    private val args by navArgs<WeatherFragmentArgs>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_weather, container, false)
        restaurantViewModel = ViewModelProvider(this).get(RestauratViewModel::class.java)
                     bind()
            return view
        }
    fun bind() {
//        restaurantViewModel.detailRestaurants("Bearer $API_KEY",args.id)
//        .observe(viewLifecycleOwner, Observer {
//            temp.text=it.phone
//            town.text=it.name
//
//            Toast.makeText(context,it.toString(), Toast.LENGTH_LONG).show()
//            println("*************************************************************************$it")
//            Log.d("tag",it.toString())
//        })
    }
    }

