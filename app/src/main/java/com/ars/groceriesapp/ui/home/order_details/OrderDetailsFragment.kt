package com.ars.groceriesapp.ui.home.order_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentOrderDetailsBinding
import com.ars.groceriesapp.ui.home.HomeViewModel


class OrderDetailsFragment : Fragment(R.layout.fragment_order_details) {

    private var _binding: FragmentOrderDetailsBinding? = null
    val binding get() = _binding!!

    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val args by navArgs<OrderDetailsFragmentArgs>()

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderDetailsBinding.bind(view)
        navController = Navigation.findNavController(view)

        binding.checkoutOrderItemsRv.apply {
            adapter = OrderItemsAdapter(args.orderInfo.orderItems)
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        binding.checkoutAddressTv.text = homeViewModel.getCustomer()?.address?.getFullAddress()

        setButtonsClickListeners()

    }

    private fun setButtonsClickListeners() {
        binding.run {
            checkoutBackBtn.setOnClickListener {
                navController.popBackStack()
            }
            checkoutAddressEditBtn.setOnClickListener {
                navController.navigate(R.id.addressFragment)
            }
            checkoutCodeApplyBtn.setOnClickListener {
                // TODO(Apply the promo code to the current order)
            }
            checkoutOrderBtn.setOnClickListener {
                Toast.makeText(requireContext(), "Ordered successfully!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}