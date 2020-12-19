package com.bignerdranch.android.yelpapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
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
import kotlinx.android.synthetic.main.list_item.view.*


const val API_KEY =
    "Rv8qMpGMjLiOeOR1nn4rOhONUXDAcwxIDcv1m-O6tXU6KpPKm92BdsSiqmLEiMbIM3NToRut50OFKSqDHw0NPbAYTN_GCmwc6G03uhdz5NzpylQos1nYJac3NvLZX3Yx"
const val WEATHER_API_KEY="cb6f4fdb41c6451f918113810201712"
class ListFragment : Fragment() {
    private lateinit var restaurantViewModel: RestauratViewModel
    private var restaurant = emptyList<YelpRestaurant>()
    private var adapter = RestaurantsAdapter(restaurant)
    private val navArgs by navArgs<ListFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        restaurantViewModel = ViewModelProvider(this).get(RestauratViewModel::class.java)
        restaurantViewModel.searchRestaurant("Bearer $API_KEY","", navArgs.lat, navArgs.lon)
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                adapter.setData(it)
            })
        val s= navArgs.lat+","+navArgs.lon
        restaurantViewModel.searchWeather( "$WEATHER_API_KEY",s).observe(viewLifecycleOwner,
            Observer {
                Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()
            })
        restaurantViewModel.searchForecastWeather(WEATHER_API_KEY,navArgs.lat+","+navArgs.lon,"7")
            .observe(viewLifecycleOwner, Observer {
                Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()
                println("*************************************************************************$it")
                Log.d("tag",it.toString())
            })
        binding.restaurantRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ListFragment.adapter

            return binding.root
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search, menu)
        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String): Boolean {
                    Log.d(TAG, "QueryTextSubmit: $queryText")
                    restaurantViewModel.searchRestaurant(
                        "Bearer $API_KEY",
                        queryText,
                        navArgs.lat,
                        navArgs.lon
                    ).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                            adapter.setData(it)
                        })
                    return true
                }

                override fun onQueryTextChange(queryText: String): Boolean {
                    Log.d(TAG, "QueryTextChange: $queryText")
                    return false
                }
            })
        }
    }
    private inner class RestaurantsAdapter(var restaurants: List<YelpRestaurant>) :
        RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: ListItemBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)

            return ViewHolder(view)
        }

        override fun getItemCount() = restaurants.size
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val restaurant = restaurants[position]
            holder.bind(restaurant)
            holder.itemView.list_item.setOnClickListener {
                val action =ListFragmentDirections.actionListFragmentToWeatherFragment()
               holder.itemView.findNavController().navigate(action)
            }

        }

        fun setData(restaurant: List<YelpRestaurant>) {
            restaurants = restaurant
            notifyDataSetChanged()
        }

        private inner class ViewHolder(private val binding: ListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(restaurant: YelpRestaurant) {
                binding.name.text = restaurant.name
                binding.rate.rating = restaurant.rating.toFloat()
                binding.reviews.text = "${restaurant.numReviews} Reviews"
                //binding.address.text = restaurant.location.address
                binding.category.text = restaurant.categories[0].title
                binding.distance.text = restaurant.displayDistance()
                Glide.with(binding.imageView).load(restaurant.imageUrl).apply(
                    RequestOptions().transforms(
                        CenterCrop(), RoundedCorners(20)
                    )
                ).into(binding.imageView)
            }
//            fun bind2(weather: Double){
//                binding.weather.text =weather.toString()
//
//            }
        }

    }
}


