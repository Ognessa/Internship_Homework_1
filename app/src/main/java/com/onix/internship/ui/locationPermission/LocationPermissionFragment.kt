package com.onix.internship.ui.locationPermission

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.onix.internship.arch.BaseFragment
import com.onix.internship.R
import com.onix.internship.databinding.LocationPermissionFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationPermissionFragment : BaseFragment<LocationPermissionFragmentBinding>(R.layout.location_permission_fragment) {

    override val viewModel: LocationPermissionViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))

        return view
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                checkPreferencesAndNavigate()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                checkPreferencesAndNavigate()
            }
            else -> {
                Toast.makeText(requireContext(), R.string.permission_error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPreferencesAndNavigate(){
        if(viewModel.checkPreferencesKeys())
            findNavController().navigate(LocationPermissionFragmentDirections.actionLocationPermissionFragmentToTabMenuFragment())
        else
            findNavController().navigate(LocationPermissionFragmentDirections.actionLocationPermissionFragmentToAskAgeFragment())
    }
}