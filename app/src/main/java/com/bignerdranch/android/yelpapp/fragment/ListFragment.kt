package com.bignerdranch.android.yelpapp.fragment

import android.app.AlertDialog
import android.app.SearchManager
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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
import com.bignerdranch.android.yelpapp.search.SearchAdapter
import com.bignerdranch.android.yelpapp.search.SuggestionProvider
import com.bignerdranch.android.yelpapp.sharedpreferences.SharedPreferencesCoordinates
import com.bignerdranch.android.yelpapp.viewmodel.MyViewModelFactory
import com.bignerdranch.android.yelpapp.viewmodel.MyViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

class ListFragment : Fragment() {
    val args by navArgs<ListFragmentArgs>()
    private lateinit var viewModell: MyViewModel
    private lateinit var restaurantViewModelFactory: MyViewModelFactory
    lateinit var sharedPreferencesCoordinates: SharedPreferencesCoordinates
    private var adapter = RestaurantAdapter(emptyList())
    private var mSuggestionAdapter: SearchAdapter? = null
    private lateinit var searchView: SearchView
    lateinit var sortSpinner: Spinner
    private var queryTextListener: SearchView.OnQueryTextListener? = null

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
                ViewModelProvider(this, restaurantViewModelFactory).get(MyViewModel::class.java)
        sortSpinner = binding.spinner
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
                        if (it.isNotEmpty()) {
                            adapter.setData(it)
                        } else {
                            AlertDialog.Builder(requireContext())
                                    .setTitle(getString(R.string.no_place))
                                    .setMessage(getString(R.string.please_change_location))
                                    .setIcon(R.drawable.logo)
                                    .setCancelable(true).show()
                        }
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
        adapter.sort()
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_search ->
                return false
        }
        searchView.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)

        val searchManager =
                requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        mSuggestionAdapter = SearchAdapter(requireActivity(), null, 0)
        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            searchView.suggestionsAdapter = mSuggestionAdapter

            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    val cursor = getRecentSuggestions(newText)
                    if (cursor != null) {
                        mSuggestionAdapter?.swapCursor(cursor)
                    }
                    return false
                }

                override fun onQueryTextSubmit(queryText: String): Boolean {
                    val suggestions = SearchRecentSuggestions(
                            activity,
                            SuggestionProvider.AUTHORITY, SuggestionProvider.MODE
                    )
                    suggestions.saveRecentQuery(queryText, null)
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
            }
            searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
                override fun onSuggestionSelect(position: Int): Boolean {
                    return false
                }

                override fun onSuggestionClick(position: Int): Boolean {

                    searchView.setQuery(mSuggestionAdapter?.getSuggestionText(position), true)
                    return true
                }
            })
            searchView.setOnQueryTextListener(queryTextListener)

        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun getRecentSuggestions(query: String): Cursor? {
        val uriBuilder = Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(SuggestionProvider.AUTHORITY)

        uriBuilder.appendPath(SearchManager.SUGGEST_URI_PATH_QUERY)

        val selection = " ?"
        val selArgs = arrayOf(query)

        val uri = uriBuilder.build()
        return activity?.contentResolver?.query(uri, null, selection, selArgs, null)
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
            RecyclerView.Adapter<RestaurantHolder>(), AdapterView.OnItemSelectedListener {
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

        fun sort() {
            var sort: List<String> =
                    listOf(getString(R.string.sort), getString(R.string.rating), getString(R.string.distance))
            var dataAdapter: ArrayAdapter<String> =
                    ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, sort)
            sortSpinner.adapter = dataAdapter
            sortSpinner.onItemSelectedListener = this

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (position == 1) {
                ratingSort()
            } else if (position == 2) {
                distanceSort()
            }
            viewModell.readAll.observe(viewLifecycleOwner, {
                adapter.setData(it)
            })
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        private fun ratingSort() {
            Collections.sort(restaurant) { l1, l2 ->
                l2.rating.compareTo(l1.rating)
            }
        }

        private fun distanceSort() {
            Collections.sort(restaurant) { l1, l2 ->
                l1.distanceInMeters.compareTo(l2.distanceInMeters)
            }
        }
    }
}



