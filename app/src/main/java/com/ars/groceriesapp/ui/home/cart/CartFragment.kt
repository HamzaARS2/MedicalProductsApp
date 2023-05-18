package com.ars.groceriesapp.ui.home.cart

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ars.domain.model.CartItem
import com.ars.domain.model.OrderRequest
import com.ars.domain.utils.Response
import com.ars.domain.utils.asOrderItem
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentCartBinding
import com.ars.groceriesapp.ui.home.HomeViewModel
import java.math.BigDecimal


class CartFragment : Fragment(R.layout.fragment_cart) {


    private val binding by lazy { FragmentCartBinding.inflate(layoutInflater) }
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val viewModel by activityViewModels<CartViewModel>()

    private lateinit var navController: NavController

    private lateinit var cartAdapter: CartAdapter

    private var totalPrice = "$0.00"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewModel.getCustomerCartItems(homeViewModel.getCustomer()?.id ?: "")
        cartAdapter =
            CartAdapter(::onItemQuantityChanged, ::onRemoveItemClick)
        getCustomerCartItems()
        addCartItemsListener()

        binding.cartRv.apply {
            adapter = cartAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), LinearLayoutManager.VERTICAL
                )
            )
        }

        binding.cartCheckoutBtn.setOnClickListener {
            val newOrder = createOrderRequest()
            newOrder ?: return@setOnClickListener
            navController.navigate(CartFragmentDirections.cartToCheckout(newOrder))
        }

        homeViewModel.wholesaleMode.observe(viewLifecycleOwner) { isActive ->
            Log.d("wholesaleMode", "wholesaleMode: is active $isActive")
            val minQuantity = if (isActive) 10 else 1
            val cartItems = cartAdapter.differ.currentList
            cartItems.forEach {
                it.id ?: return@forEach
                viewModel.updateCartItemQuantity(it.id!!, minQuantity)
            }
            cartAdapter.setMinQuantity(minQuantity)
        }


    }

    private fun createOrderRequest(): OrderRequest? {
        val orderItems = cartAdapter.differ.currentList.map { it.asOrderItem() }
        val customer = homeViewModel.getCustomer()
        customer?.id ?: return null
        val totalPrice = orderItems.sumOf { it.subTotalPrice }
        return OrderRequest(
            customerId = customer.id,
            addressId = customer.address?.id!!,
            totalPrice = totalPrice,
            orderItems
        )
    }

    private fun getCustomerCartItems() {
        val customer = homeViewModel.getCustomer()
        if (customer == null) {
            // Show a dialog to ask the user to login first
            displayDialog()
            return
        }

        viewModel.cartItems
            .observe(viewLifecycleOwner) { response ->
                Log.d("getCustomerCartItems", "getCustomerCartItems:" + response.data?.map { it.quantity })
                binding.cartProgress.isVisible = response is Response.Loading
                binding.cartCheckoutBtn.isVisible = response !is Response.Loading
                cartAdapter.differ.submitList(response.data)
                binding.cartTotalPriceTv.text = calculateTotalPrice(response.data ?: emptyList())

            }
    }

    private fun calculateTotalPrice(items: List<CartItem>): String {
        return "$" + items.sumOf { item ->
            (item.product!!.price).times(item.quantity.toBigDecimal())
        }
    }

    private fun displayDialog() {
    }

    private fun addCartItemsListener() {
        cartAdapter.differ.addListListener { _, currentList ->
            binding.apply {
                cartCheckoutBtn.isVisible = currentList.isNotEmpty()
                cartTotalPriceTv.isVisible = currentList.isNotEmpty()
                cartIsEmptyTv.isVisible = currentList.isEmpty()
            }
        }
    }


    private fun onItemQuantityChanged(quantity: Int, id: Int) {
        viewModel.updateCartItemQuantity(id, quantity)
    }

    private fun onRemoveItemClick(cartItem: CartItem, onFinish: () -> Unit) {
        cartItem.id ?: return
        viewModel.removeItemFromCart(cartItem.id!!).observe(viewLifecycleOwner) { response ->
            binding.cartProgress.isVisible = response is Response.Loading
            when (response) {
                is Response.Success -> {
                    onFinish()
                }
                is Response.Error -> {
                    onFinish()
                    Toast.makeText(requireContext(), response.data, Toast.LENGTH_SHORT)
                        .show()
                }
                is Response.Loading -> {
                }
            }
        }
    }


}