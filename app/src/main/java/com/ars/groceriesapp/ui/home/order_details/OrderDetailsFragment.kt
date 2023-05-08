package com.ars.groceriesapp.ui.home.order_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import com.ars.domain.model.Order
import com.ars.domain.utils.Response
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentOrderDetailsBinding
import com.ars.groceriesapp.ui.home.HomeViewModel


class OrderDetailsFragment : Fragment(R.layout.fragment_order_details) {

    private var _binding: FragmentOrderDetailsBinding? = null
    val binding get() = _binding!!

    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val viewModel by activityViewModels<OrderDetailsViewModel>()
    private val args by navArgs<OrderDetailsFragmentArgs>()

    private lateinit var navController: NavController

    var bool = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderDetailsBinding.bind(view)
        navController = Navigation.findNavController(view)

        binding.checkoutOrderItemsRv.apply {
            adapter = OrderItemsAdapter(args.orderInfo.orderItems)
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        setOrderInfo()

        setButtonsClickListeners()

    }

    @SuppressLint("SetTextI18n")
    private fun setOrderInfo() {
        binding.apply {
            checkoutAddressTv.text = homeViewModel.getCustomer()?.address?.getFullAddress()
            checkoutTotalPriceTv.text = "$${args.orderInfo.totalPrice}"
        }

    }

    private fun setButtonsClickListeners() {
        binding.run {
            checkoutBackBtn.setOnClickListener {
                navController.popBackStack()
            }
            checkoutAddressEditBtn.setOnClickListener {
                navController.navigate(R.id.addressFragment)
            }
            checkoutOrderBtn.setOnClickListener {
                viewModel.placeOrder(args.orderInfo)
                    .observe(viewLifecycleOwner) { response ->
                        when(response) {
                            is Response.Success -> {
                                navController.navigate(OrderDetailsFragmentDirections
                                    .orderDetailsToOrderDialog(response.data!!)
                                )
                            }
                            is Response.Error -> {
                                Log.d("PlaceOrder", "setButtonsClickListeners: " + response.error?.message)
                                navController.navigate(R.id.orderFailedDialogFragment)

                            }
                            is Response.Loading -> Unit
                        }
                    }
            }
        }
    }

}