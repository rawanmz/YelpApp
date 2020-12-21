package com.bignerdranch.android.yelpapp.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.yelpapp.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var lat: Double = 0.0
    private var lon: Double = 0.0
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        googleMap.setOnMapLongClickListener {
            val lat = it.latitude
            val lon = it.longitude
            val location = LatLng(lat, lon)
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            val action = MapsFragmentDirections.actionMapsFragmentToListFragment(
                lat.toString(),
                lon.toString()
            )
            findNavController().navigate(action)
            googleMap.addMarker(MarkerOptions().position(location))

        }
        googleMap.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED ){
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { place ->
                var location = place.getResult()
                if (location != null) {
                    var geocoder = Geocoder(requireContext(), Locale.getDefault())
                    var address = geocoder.getFromLocation(
                        location.latitude, location.longitude, 1)
                    lat = address[0].latitude
                    lon = address[0].longitude
                }
                var currentLocation = LatLng(lat, lon)
                mMap?.addMarker(MarkerOptions().position(currentLocation))
                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15F))
            }
        }else{
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),44) }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}