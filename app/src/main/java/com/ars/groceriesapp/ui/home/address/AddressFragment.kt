package com.ars.groceriesapp.ui.home.address

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ars.domain.model.Address
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import com.ars.domain.validation.Validation
import com.ars.domain.validation.ValidationResponse
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentAddressBinding
import com.ars.groceriesapp.ui.home.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class AddressFragment : Fragment(R.layout.fragment_address) {

    private var _binding: FragmentAddressBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<AddressViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddressBinding.bind(view)
        navController = Navigation.findNavController(view)

        displayCurrentAddress()


        binding.addressSaveBtn.setOnClickListener {
            updateCustomerAddress()
        }

        binding.addressBackBtn.setOnClickListener {
            navController.popBackStack()
        }

    }

    private fun updateCustomerAddress() {
        val firstName = binding.addressFirstNameEdt.text.toString()
        val lastName = binding.addressLastNameEdt.text.toString()
        val address = binding.addressAddressEdt.text.toString()
        val phone = binding.addressPhoneEdt.text.toString()
        val city = binding.addressCityEdt.text.toString()

        val firstNameResponse = Validation.validateFirstname(firstName)
        val lastNameResponse = Validation.validateLastName(lastName)
        val phoneResponse = Validation.validatePhoneNumber(phone)
        val addressResponse = if (address.isBlank()) ValidationResponse(
            false,
            "Please enter your address"
        ) else ValidationResponse(true)
        val cityResponse = if (city.isBlank()) ValidationResponse(
            false,
            "Please enter your city"
        ) else ValidationResponse(true)

        if (!firstNameResponse.isValid || !lastNameResponse.isValid || !phoneResponse.isValid || !addressResponse.isValid || !cityResponse.isValid) {
            binding.run {
                addressFirstNameLayout.error = firstNameResponse.message
                addressLastNameLayout.error = lastNameResponse.message
                addressPhoneLayout.error = phoneResponse.message
                addressAddressLayout.error = addressResponse.message
                addressCityLayout.error = cityResponse.message
            }
            return
        }

        sendUpdateAddressRequest(
            Address(
                id = homeViewModel.getCustomer()?.address?.id,
                firstName = firstName,
                lastName = lastName,
                phone = phone,
                streetAddress = address,
                city = city,
            )
        )
    }

    private fun sendUpdateAddressRequest(address: Address) {
        viewModel.saveAddress(address, homeViewModel.getCustomer()?.id ?: "").observe(viewLifecycleOwner) { response ->
            binding.run {
                addressSaveBtn.isVisible = response !is Response.Loading
                addressProgress.isVisible = response is Response.Loading
            }

            when(response) {
                is Response.Success -> {
                    Toast.makeText(requireContext(), "Saved successfully!", Toast.LENGTH_SHORT).show()
                    val updatedCustomer = homeViewModel.getCustomer().apply {
                        this?.address = response.data
                    }
                    homeViewModel.setCustomer(updatedCustomer)
                }
                is Response.Error -> {
                    Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show()
                    Log.e("AddressFragment", "sendUpdateAddressRequest: ", response.error)
                }
                is Response.Loading -> {

                }
            }
        }
    }

    private fun displayCurrentAddress() {
        val address = homeViewModel.getCustomer()?.address
        address ?: return

        binding.run {
            addressFirstNameEdt.setText(address.firstName)
            addressLastNameEdt.setText(address.lastName)
            addressAddressEdt.setText(address.streetAddress)
            addressPhoneEdt.setText(address.phone)
            addressCityEdt.setText(address.city)
        }
    }


}