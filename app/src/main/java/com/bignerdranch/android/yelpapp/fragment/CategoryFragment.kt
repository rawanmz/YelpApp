package com.bignerdranch.android.yelpapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.yelpapp.R
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.viewmodel.RestauratViewModel


class CategoryFragment : Fragment() {
    val args by navArgs<CategoryFragmentArgs>()
    private val viewModell: RestauratViewModel by lazy {
        ViewModelProvider(this).get(RestauratViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_category, container, false)
        val all=view.findViewById(R.id.All) as CardView
        val coffee=view.findViewById(R.id.coffee) as CardView
        val restaurant=view.findViewById(R.id.restaurant) as CardView
        val malls=view.findViewById(R.id.malls) as CardView
        val museum=view.findViewById(R.id.museumes) as CardView
        val hotel=view.findViewById(R.id.hotel) as CardView

all.setOnClickListener {
    val action =CategoryFragmentDirections.actionCategoryFragmentToListFragment(args.lat,args.lon," ")
    findNavController().navigate(action)
}
        coffee.setOnClickListener {
            val action =CategoryFragmentDirections.actionCategoryFragmentToListFragment(args.lat,args.lon,"Coffee")
            findNavController().navigate(action)
        }
        restaurant.setOnClickListener {
            val action =CategoryFragmentDirections.actionCategoryFragmentToListFragment(args.lat,args.lon,"Food")
            findNavController().navigate(action)
        }
        malls.setOnClickListener {
            val action =CategoryFragmentDirections.actionCategoryFragmentToListFragment(args.lat,args.lon,"Malls")
            findNavController().navigate(action)
        }
        museum.setOnClickListener {
            val action =CategoryFragmentDirections.actionCategoryFragmentToListFragment(args.lat,args.lon,"Museums")
            findNavController().navigate(action)
        }
        hotel.setOnClickListener {
            val action =CategoryFragmentDirections.actionCategoryFragmentToListFragment(args.lat,args.lon,"Hotels")
            findNavController().navigate(action)
        }
        return view
    }
}