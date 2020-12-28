package com.bignerdranch.android.yelpapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.yelpapp.R


class CategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        val all = view.findViewById(R.id.All) as CardView
        val coffee = view.findViewById(R.id.coffee) as CardView
        val restaurant = view.findViewById(R.id.restaurant) as CardView
        val malls = view.findViewById(R.id.malls) as CardView
        val museum = view.findViewById(R.id.museumes) as CardView
        val hotel = view.findViewById(R.id.hotel) as CardView

        all.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToListFragment(getString(R.string.all))
            findNavController().navigate(action)
        }
        coffee.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToListFragment(getString(R.string.coffee))
            findNavController().navigate(action)
        }
        restaurant.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToListFragment(getString(R.string.food))
            findNavController().navigate(action)
        }
        malls.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToListFragment(getString(R.string.malls))
            findNavController().navigate(action)
        }
        museum.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToListFragment(getString(R.string.museums))
            findNavController().navigate(action)
        }
        hotel.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToListFragment(getString(R.string.hotels))
            findNavController().navigate(action)
        }
        return view
    }
}