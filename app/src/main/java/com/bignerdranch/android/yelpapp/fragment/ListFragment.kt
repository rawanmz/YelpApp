package com.bignerdranch.android.yelpapp.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.yelpapp.BuildConfig
import com.bignerdranch.android.yelpapp.R
import com.bignerdranch.android.yelpapp.ServiceLocator
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.databinding.FragmentListBinding
import com.bignerdranch.android.yelpapp.databinding.ListItemBinding
import com.bignerdranch.android.yelpapp.sharedpreferences.SharedPreferencesCoordinates
import com.bignerdranch.android.yelpapp.viewmodel.MyViewModelFactory
import com.bignerdranch.android.yelpapp.viewmodel.RestauratViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_item.view.*


class ListFragment : Fragment() {
    val args by navArgs<ListFragmentArgs>()
    private lateinit var viewModell: RestauratViewModel
    private lateinit var restaurantViewModelFactory: MyViewModelFactory
    lateinit var sharedPreferencesCoordinates: SharedPreferencesCoordinates

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
        val binding: FragmentListBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_list, container, false)
        restaurantViewModelFactory = MyViewModelFactory(
            ServiceLocator.yelpResponse,
            ServiceLocator.weatherResponse
        )
        viewModell =
            ViewModelProvider(this, restaurantViewModelFactory).get(RestauratViewModel::class.java)
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            sharedPreferencesCoordinates = SharedPreferencesCoordinates(requireContext())
            val location = sharedPreferencesCoordinates.getCoordinate("latlang")
            viewModell.searchRestaurant(
                "Bearer ${BuildConfig.API_KEY}", args.search,
                location.latitude.toString(), location.longitude.toString()
            ).observe(viewLifecycleOwner,
                Observer {
                    adapter.setData(it)
                })
        } else {
            viewModell.readAll.observe(viewLifecycleOwner, Observer { Places ->
                adapter.setData(Places)
            })
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

                    sharedPreferencesCoordinates = SharedPreferencesCoordinates(requireContext())
                    val location = sharedPreferencesCoordinates.getCoordinate("latlang")
                    viewModell.searchRestaurant(
                        "Bearer ${BuildConfig.API_KEY}",
                        queryText,
                        location.latitude.toString(),
                        location.longitude.toString()
                    ).observe(viewLifecycleOwner, androidx.lifecycle.Observer {

                        adapter.setData(it)
                    })
                    return true
                }

                override fun onQueryTextChange(queryText: String): Boolean {
                    return false
                }
            })
            setOnSearchClickListener {
                val set = mutableSetOf<String>()
                searchView.setQuery(viewModell.searchTerm, false)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }

    private inner class RestaurantHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: YelpRestaurant) {
            binding.placeName.text = restaurant.name
            binding.phone.text = getString(R.string.phone) + restaurant.phone
            binding.rate.rating = restaurant.rating.toFloat()
            binding.reviews.text = restaurant.numReviews.toString() + getString(R.string.reviews)
            binding.category.text = restaurant.categories[0].title
            Glide.with(binding.placeImg)
                .load(restaurant.imageUrl).apply(
                    RequestOptions().transforms(
                        CenterCrop(), RoundedCorners(20)
                    )
                ).into(binding.placeImg)
        }
    }

    private inner class RestaurantAdapter(private var restaurant: List<YelpRestaurant>) :
        RecyclerView.Adapter<RestaurantHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
            val binding: ListItemBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.list_item, parent, false)
            return RestaurantHolder(binding)
        }

        override fun getItemCount(): Int = restaurant.size
        override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
            val restaurants = restaurant[position]
            holder.bind(restaurants)
            holder.itemView.list_item.setOnClickListener {
                val action =
                    ListFragmentDirections.actionListFragmentToWeatherFragment(restaurants.yelpId)
                findNavController().navigate(action)
            }

        }

        fun setData(restaurant: List<YelpRestaurant>) {
            this.restaurant = restaurant
            notifyDataSetChanged()
        }
    }
}


