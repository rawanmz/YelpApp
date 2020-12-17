package com.bignerdranch.android.yelpapp

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MapsFragment : Fragment() {

        private lateinit var mMap: GoogleMap
        private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

        private val callback = OnMapReadyCallback { googleMap ->
            mMap = googleMap
            googleMap.setOnMapLongClickListener {
                val lat=it.latitude
                val lon=it.longitude
                val location=LatLng(lat,lon)
                googleMap.addMarker(MarkerOptions().position(location))
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
               val action = MapsFragmentDirections.actionMapsFragmentToListFragment(lat.toString(),lon.toString())
               findNavController().navigate(action)
            }
            googleMap.clear()
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_maps, container, false)
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext())

            return view
        }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
        }
}