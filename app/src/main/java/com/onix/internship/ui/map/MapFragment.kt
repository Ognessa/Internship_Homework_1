package com.onix.internship.ui.map

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.MapFragmentBinding
import com.onix.internship.helpers.JSONHelper
import com.onix.internship.objects.Point
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapFragment : BaseFragment<MapFragmentBinding>(R.layout.map_fragment), OnMapReadyCallback {
    override val viewModel: MapViewModel by viewModel()

    private lateinit var location: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapSupport : SupportMapFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        getUserLocation()

        mapSupport = childFragmentManager.findFragmentById(
            R.id.google_map) as SupportMapFragment

        mapSupport.getMapAsync(this)

        binding.btnExport.setOnClickListener {
            exportUserData(viewModel.createUserPoint(location))
        }

        return view
    }

    override fun onMapReady(map : GoogleMap){
        map.clear()
        viewModel.pointList.forEach {
            createPoint(map, it)
        }
        map.isMyLocationEnabled = true
    }

    private fun exportUserData(point: Point){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, JSONHelper().parsePointToJSON(point))
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun createPoint(googleMap : GoogleMap, point: Point){
        val classTitle = resources.getStringArray(R.array.class_titles)[point.pointClass].lowercase()
        googleMap.apply {
            addMarker(
                MarkerOptions()
                    .position(point.location)
                    .icon(getIcon(point.pointClass))
                    .title("$classTitle ${point.level}")
            )
            animateCamera(CameraUpdateFactory.newLatLng(point.location))
        }
    }

    private fun getIcon(classId : Int): BitmapDescriptor {
        val iconName = resources.getStringArray(R.array.class_titles)[classId].lowercase()
        val iconSize = resources.getDimension(R.dimen.icon_on_map_size).toInt()
        val drawable = requireContext().resources.getDrawable(requireContext().resources
            .getIdentifier("ic_marker_$iconName", "drawable", requireContext().packageName), null)
            .toBitmap(iconSize, iconSize)
        return BitmapDescriptorFactory.fromBitmap(drawable)
    }

    private fun getUserLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation.addOnSuccessListener {
            location = it
        }
    }

}