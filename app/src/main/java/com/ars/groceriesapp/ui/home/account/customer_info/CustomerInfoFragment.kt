package com.ars.groceriesapp.ui.home.account.customer_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ars.domain.model.Customer
import com.ars.domain.utils.Response
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentCustomerInfoBinding
import com.ars.groceriesapp.ui.home.HomeViewModel

class CustomerInfoFragment : Fragment(R.layout.fragment_customer_info) {


    private var _binding: FragmentCustomerInfoBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val viewModel by activityViewModels<CustomerInfoViewModel>()

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCustomerInfoBinding.bind(view)
        navController = Navigation.findNavController(view)

        binding.customerInfoBackBtn.setOnClickListener {
            navController.popBackStack()
        }
        binding.infoSaveChangesBtn.setOnClickListener {
            val customer = homeViewModel.getCustomer()
            customer?.name = binding.customerInfoNameTv.text.toString()
            customer?.email = binding.customerInfoMailTv.text.toString()
            customer?.phone = binding.customerInfoPhoneTv.text.toString()
            customer ?: return@setOnClickListener
            updateCustomer(customer)
        }

        updateInfo(homeViewModel.getCustomer())
        observeCustomer()


    }

    private fun updateCustomer(customer: Customer?) {
        customer ?: return
        viewModel.updateCustomerChanges(customer).observe(viewLifecycleOwner) { response ->
            binding.apply {
                infoSaveChangesBtn.isVisible = response !is Response.Loading
                infoSaveChangesProgress.isVisible = response is Response.Loading
            }

            if (response is Response.Success) {
                homeViewModel.setCustomer(response.data)
                Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            }

            if (response is Response.Error) {
                Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeCustomer() {
        homeViewModel.getCustomerLiveData().observe(viewLifecycleOwner) { customer ->
            updateInfo(customer)
        }
    }

    private fun updateInfo(customer: Customer?) {
        customer ?: return
        binding.apply {
            customerInfoNameTv.setText(customer.name)
            customerInfoMailTv.setText(customer.email)
            customerInfoPhoneTv.setText(customer.phone)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}