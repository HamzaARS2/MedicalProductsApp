package com.ars.groceriesapp.ui.auth.phone_location.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.ars.domain.utils.Resource
import com.ars.groceriesapp.PhoneLocationGraphDirections
import com.ars.groceriesapp.databinding.FragmentLocationBinding
import com.ars.groceriesapp.ui.auth.phone_location.PhoneLocationViewModel
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.distinctUntilChanged
import java.io.IOException
import java.util.*


class LocationFragment : Fragment() {
    private val binding by lazy { FragmentLocationBinding.inflate(layoutInflater) }
    private val viewModel: PhoneLocationViewModel by activityViewModels()


    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationManager by lazy {
        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private lateinit var locationCallback: LocationCallback

    private lateinit var locationSettingsLauncher: ActivityResultLauncher<Intent>


    override fun onAttach(context: Context) {
        super.onAttach(context)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(
            requireContext()
        )
        locationSettingsLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                // Handle the result here if necessary

                if (isLocationEnabled()) {
                    Toast.makeText(requireContext(), "Location is Enabled", Toast.LENGTH_SHORT)
                        .show()
                    requestLocationPermission()
                } else
                    Toast.makeText(requireContext(), "Location is Disabled", Toast.LENGTH_SHORT)
                        .show()
            }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (location != null) {
                        val address = getAddress(location)
                        viewModel.customer.address = address
                        viewModel.updateCustomer()

                    } else
                        Log.d("locationCallback", "onLocationResult: Location = null")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.locationRequestPermissionBtn.setOnClickListener {
            if (isLocationEnabled()) {
                Toast.makeText(requireContext(), "Enabled", Toast.LENGTH_SHORT).show()
                requestLocationPermission()
            } else showLocationSettings()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.updatedCustomer.distinctUntilChanged().collect { response ->
                when(response) {
                    is Resource.Success -> {
                        Toast.makeText(requireContext(), "Registered Successfully", Toast.LENGTH_SHORT).show()
                        Navigation
                            .findNavController(requireView())
                            .navigate(PhoneLocationGraphDirections.actionGlobalHomeGraph2(response.result))
                    }
                    is Resource.Failure -> {
                        Snackbar.make(requireView(),response.e.message ?: "Something went wrong!", Snackbar.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                    }
                    else ->{}
                }
            }
        }
    }

    

    private fun requestLocationPermission() {
        // The registered ActivityResultCallback gets the result of this request.
        if (!isLocationEnabled()) {
            showLocationSettings()
            return
        }
        if (!isPermissionGranted()) {
//            requestPermissionLauncher.launch(
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            )
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else requestLocationData()
    }

    private fun isLocationEnabled(): Boolean =
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    private fun showLocationSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        locationSettingsLauncher.launch(intent)
    }

    private fun isPermissionGranted(): Boolean {
        return when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) -> {
                // You can use the API that requires the permission.
                true
            }
            else -> {
                // You can directly ask for the permission.
                false
            }
        }
    }

    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    requestLocationData()
                    Toast.makeText(
                        requireContext(),
                        "Precise & Approximate are Granted",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    requestLocationData()
                    Toast.makeText(requireContext(), "Only approximate Granted", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    // No location access granted.
                    Toast.makeText(requireContext(), "Access Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted.
                requestLocationData()

            } else {
                // Explain to the user that the feature is unavailable because the
                // feature requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }


    @SuppressLint("MissingPermission")
    private fun requestLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        val mLocationRequest = LocationRequest.Builder(1000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setMaxUpdates(1)
            .build()

        // setting LocationRequest
        // on FusedLocationClient
        startLocationUpdates(mLocationRequest, locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(locationRequest: LocationRequest, callback: LocationCallback) {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            callback,
            Looper.getMainLooper()
        )
    }

    private fun getAddress(location: Location): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

            val address = addresses!![0]
            val streetAddress = address.getAddressLine(0)

            // Get the full address by concatenating all the address lines
            val country = address.countryName ?: ""
            val city = address.locality ?: ""
            val state = address.adminArea?: ""

            "$streetAddress, $city, $state, $country"
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }
        
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }


}