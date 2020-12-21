package com.bignerdranch.android.yelpapp.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
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
import com.bignerdranch.android.yelpapp.data.*
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
    val args by navArgs<ListFragmentArgs>()
    private val viewModell: RestauratViewModel by lazy {
        ViewModelProvider(this).get(RestauratViewModel::class.java)
    }
    private var adapter = RestaurantAdapter(emptyList())
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentListBinding = DataBindingUtil
            .inflate(inflater,R.layout.fragment_list,container,false)
        val connectivityManager = context?.
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            //Display Weather
            //Display Business
            viewModell.searchRestaurant("Bearer $API_KEY",args.search,
                args.lat, args.lon).observe(viewLifecycleOwner,
                Observer{
                    adapter.setData(it)
                })
        }else {
            viewModell.readAll.observe(viewLifecycleOwner, Observer { r->
                adapter.setData(r) })
            Log.d("test","*************************************************** no connection")
        }
        binding.restaurantRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ListFragment.adapter
        }
        return binding.root
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
                    viewModell.searchRestaurant(
                        "Bearer $API_KEY",
                        queryText,
                        args.lat,
                        args.lon
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
            setOnSearchClickListener {
                searchView.setQuery(viewModell.searchTerm, false)
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_clear -> {
                viewModell.searchRestaurant(
                    "$API_KEY",
                    "",
                    args.lat,
                    args.lon,)
                    true
            }
            else -> super.onOptionsItemSelected(item)
    }
    }
    private inner class RestaurantHolder(private val binding: ListItemBinding)
        :RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(restaurant: YelpRestaurant, weather: Weather.Current?){


            binding.name.text =restaurant.name
            binding.rate.rating = restaurant.rating.toFloat()
            binding.reviews.text = restaurant.numReviews.toString()+" Reviews"
            binding.category.text = restaurant.categories[0].title
            Glide.with(binding.imageView)
                .load(restaurant.imageUrl).apply(
                    RequestOptions().transforms(
                        CenterCrop(), RoundedCorners(20)
                    )).into(binding.imageView)
            binding.weatherDes.text=weather?.condition?.text
            binding.weather.text = weather?.temp_c.toString()+"°C"
            val x= "https://${weather?.condition?.icon}"
            if (x=="https://null"){
                binding.weatherIcon.setImageResource(R.drawable.cloud)
            }else{
                Glide.with(binding.weatherIcon)
                    .load(x).apply(
                        RequestOptions().transforms(
                            CenterCrop(), RoundedCorners(20)
                        )).into(binding.weatherIcon)
            }
        }
    }
    private inner class RestaurantAdapter(private var restaurant:List<YelpRestaurant>)
        :RecyclerView.Adapter<RestaurantHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
            val binding: ListItemBinding = DataBindingUtil
                .inflate(layoutInflater,R.layout.list_item,parent,false)
            return RestaurantHolder(binding)
        }
        override fun getItemCount(): Int = restaurant.size
        override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
            val restaurants = restaurant[position]
            val x = viewModell.searchWeather(WEATHER_API_KEY,"${restaurants.coordinates.latitude},${restaurants.coordinates.longitude}","7").value
            holder.bind(restaurants, x?.current)
            holder.itemView.list_item.setOnClickListener {
                val action =ListFragmentDirections.actionListFragmentToWeatherFragment(restaurants.myid.toString())
               holder.itemView.findNavController().navigate(action)
            }
        }
        fun setData(restaurant: List<YelpRestaurant>){
            this.restaurant = restaurant
            notifyDataSetChanged()
        }
    }
}


