package com.ars.groceriesapp.ui.auth.phone_location.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.ars.domain.utils.Resource
import com.ars.groceriesapp.PhoneLocationGraphDirections
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentLocationBinding
import com.ars.groceriesapp.ui.auth.AuthViewModel
import com.ars.groceriesapp.ui.auth.phone_location.PhoneLocationViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest


class LocationFragment : Fragment() {
    private val binding by lazy { FragmentLocationBinding.inflate(layoutInflater) }
    private val viewModel: PhoneLocationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView7.setOnClickListener {
            viewModel.customer.address = "Updated location"
            viewModel.updateCustomer()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.updatedCustomer.collectLatest {response ->
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

                }

            }
        }
    }
}