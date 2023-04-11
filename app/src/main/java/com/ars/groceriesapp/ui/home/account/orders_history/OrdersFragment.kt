package com.ars.groceriesapp.ui.home.account.orders_history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ars.domain.utils.Response
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentOrdersBinding
import com.ars.groceriesapp.ui.home.HomeViewModel
import com.ars.groceriesapp.utils.ACTIVE_ORDERS
import com.ars.groceriesapp.utils.DELIVERED_ORDERS
import com.google.android.material.button.MaterialButton

class OrdersFragment : Fragment(R.layout.fragment_orders) {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<OrdersViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private lateinit var navController: NavController
    private lateinit var ordersAdapter: OrdersAdapter

    private lateinit var customerId: String




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrdersBinding.bind(view)
        navController = Navigation.findNavController(view)
        customerId = homeViewModel.getCustomer()?.id ?: ""
        initRecyclerView()
        observeOrders()

        binding.ordersHistoryBackBtn.setOnClickListener {
            navController.popBackStack()
        }

        binding.toggleButton.addOnButtonCheckedListener { group, _, _ ->
            if (group.checkedButtonId == R.id.orders_active_btn) {
                ordersAdapter.setOnSectionChanged(ACTIVE_ORDERS)
            } else {
                ordersAdapter.setOnSectionChanged(DELIVERED_ORDERS)
            }
        }

    }

    private fun observeOrders() {
        viewModel.getOrdersLiveData(customerId).observe(viewLifecycleOwner) { response ->
            binding.ordersProgress.isVisible = response is Response.Loading
            when (response) {
                is Response.Success ->
                    ordersAdapter.setData(response.data)
                is Response.Error ->
                    Toast.makeText(
                        requireContext(),
                        response.error?.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                is Response.Loading -> Unit
            }
        }
    }

    private fun initRecyclerView() {
        ordersAdapter = OrdersAdapter()
        binding.ordersRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ordersAdapter
            setHasFixedSize(true)
        }
    }


}