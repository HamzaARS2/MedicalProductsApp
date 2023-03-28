package com.ars.groceriesapp.ui.home.cart

import android.os.Bundle
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
import com.ars.domain.model.Customer
import com.ars.domain.model.Order
import com.ars.domain.utils.Response
import com.ars.domain.utils.asOrderItem
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentCartBinding
import com.ars.groceriesapp.ui.home.HomeViewModel


class CartFragment : Fragment(R.layout.fragment_cart) {


    private val binding by lazy { FragmentCartBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<CartViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()

    private lateinit var navController: NavController

    private lateinit var cartManager: CartManager
    private lateinit var cartAdapter: CartAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        cartAdapter =
            CartAdapter(::onIncreaseQuantityClick, ::onDecreaseQuantityClick, ::onRemoveItemClick)
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
            val newOrder = createOrder()
            newOrder ?: return@setOnClickListener
            navController.navigate(CartFragmentDirections.cartToCheckout(newOrder))
        }


    }

    private fun createOrder(): Order? {
        val orderItems = cartAdapter.differ.currentList.map { it.asOrderItem() }
        val customerId = homeViewModel.getCustomer()?.id
        customerId ?: return null
        val totalPrice = orderItems.sumOf { it.subTotalPrice }
        return Order(
            customerId = customerId,
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

        viewModel.getCustomerCartItems(customer.id)
            .observe(viewLifecycleOwner) { response ->
                cartManager = CartManager(response.data ?: emptyList())
                observeCartManager()
                binding.cartProgress.isVisible = response is Response.Loading
                binding.cartCheckoutBtn.isVisible = response !is Response.Loading

                cartAdapter.differ.submitList(response.data)

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

    private fun observeCartManager() {
        cartManager.apply {
            cartItem.observe(viewLifecycleOwner) { (cartItem, position) ->
                cartAdapter.notifyItemChanged(position)
            }

            totalPrice.observe(viewLifecycleOwner) { totalPrice ->
                binding.cartTotalPriceTv.text = totalPrice
            }
        }
    }


    private fun onIncreaseQuantityClick(position: Int, id: Int) {
        val updatedQuantity = cartManager.increaseQuantity(position)
        viewModel.updateCartItemQuantity(id, updatedQuantity)
    }

    private fun onDecreaseQuantityClick(position: Int, id: Int) {
        val updatedQuantity = cartManager.decreaseQuantity(position)
        viewModel.updateCartItemQuantity(id, updatedQuantity)
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