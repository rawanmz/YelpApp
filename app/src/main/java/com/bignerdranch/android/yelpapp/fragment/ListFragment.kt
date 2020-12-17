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
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.databinding.FragmentListBinding
import com.bignerdranch.android.yelpapp.databinding.ListItemBinding
import com.bignerdranch.android.yelpapp.viewmodel.RestauratViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


const val API_KEY = "Rv8qMpGMjLiOeOR1nn4rOhONUXDAcwxIDcv1m-O6tXU6KpPKm92BdsSiqmLEiMbIM3NToRut50OFKSqDHw0NPbAYTN_GCmwc6G03uhdz5NzpylQos1nYJac3NvLZX3Yx"

class ListFragment : Fragment() {
    private lateinit var restaurantViewModel: RestauratViewModel
    private var restaurant = emptyList<YelpRestaurant>()
    private var adapter = RestaurantsAdapter(restaurant)
private val navArgs by navArgs<ListFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        restaurantViewModel = ViewModelProvider(this).get(RestauratViewModel::class.java)
       // restaurantViewModel.searchRestaurant("Bearer $API_KEY", "Avocado Toast", "New York")
        restaurantViewModel.searchLocation("Bearer $API_KEY", navArgs.lat, navArgs.lon)

            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                adapter.setData(it)
            })
        binding.restaurantRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ListFragment.adapter

            return binding.root
        }
    }

       private inner class RestaurantsAdapter(var restaurants: List<YelpRestaurant>) :
            RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view: ListItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.list_item,parent,false)

                return ViewHolder(view)
            }

            override fun getItemCount() = restaurants.size
            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val restaurant = restaurants[position]
                holder.bind(restaurant)
            }

            fun setData(restaurant: List<YelpRestaurant>) {
                restaurants = restaurant
                notifyDataSetChanged()
            }

            private inner class ViewHolder(private val binding:ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
                fun bind(restaurant: YelpRestaurant) {
                    binding.name.text = restaurant.name
                    binding.rate.rating = restaurant.rating.toFloat()
                    binding.reviews.text = "${restaurant.numReviews} Reviews"
                    binding.address.text = restaurant.location.address
                    binding.category.text = restaurant.categories[0].title
                    binding.distance.text = restaurant.displayDistance()
                    Glide.with(binding.imageView).load(restaurant.imageUrl).apply(
                        RequestOptions().transforms(
                            CenterCrop(), RoundedCorners(20)
                        )).into(binding.imageView)
                }
            }

        }
    }


