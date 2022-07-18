package com.onix.internship.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.MapFragmentBinding
import com.onix.internship.helpers.PlacesReader
import com.onix.internship.objects.Place
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapFragment : BaseFragment<MapFragmentBinding>(R.layout.map_fragment){
    override val viewModel: MapViewModel by viewModel()

    private val places: List<Place> by lazy {
        PlacesReader(requireContext()).read()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val mapFragment = requireActivity().supportFragmentManager.findFragmentById(
            R.id.google_map
        ) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            addMarkers(googleMap)
        }

        return view
    }

    /**
     * Adds marker representations of the places list on the provided GoogleMap object
     */
    private fun addMarkers(googleMap: GoogleMap) {
        places.forEach { place ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .position(place.latLng)
            )
        }
    }

}