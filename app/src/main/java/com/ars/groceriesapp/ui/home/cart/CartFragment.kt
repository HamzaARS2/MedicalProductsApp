package com.ars.groceriesapp.ui.home.cart

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ars.domain.model.CartItem
import com.ars.domain.model.Product
import com.ars.domain.utils.Resource
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentCartBinding
import com.ars.groceriesapp.ui.home.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.math.BigDecimal


class CartFragment : Fragment(R.layout.fragment_cart) {


    private val binding by lazy { FragmentCartBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<CartViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()


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
        viewModel.getCustomerCartItems(homeViewModel.getCustomer().docId)
        collectCartItems()
        cartAdapter = CartAdapter(::onIncreaseQuantityClick, ::onDecreaseQuantityClick, ::onRemoveItemClick)

        binding.cartRv.apply {
            adapter = cartAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), LinearLayoutManager.VERTICAL
                )
            )
        }

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
                cartAdapter.updateCartItem(cartItem, position)
            }

            totalPrice.observe(viewLifecycleOwner) { totalPrice ->
                binding.cartTotalPriceTv.text = totalPrice
            }
        }
    }

    private fun collectCartItems() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
               viewModel.cartItems.collectLatest { response ->
                   when(response) {
                       is Resource.Success -> {
                           cartManager = CartManager(response.result)
                           cartAdapter.differ.submitList(response.result)
                           observeCartManager()
                           binding.cartProgress.isVisible = false

                       }
                       is Resource.Failure -> {
                           binding.cartProgress.isVisible = false
                           Toast.makeText(requireContext(), response.e.message, Toast.LENGTH_SHORT).show()
                       }
                       else -> {
                           // Loading
                           binding.cartProgress.isVisible = true
                       }
                   }
               }
            }
        }
    }

    private fun onIncreaseQuantityClick(position: Int) {
        cartManager.increaseQuantity(position)
    }

    private fun onDecreaseQuantityClick(position: Int) {
        cartManager.decreaseQuantity(position)
    }

    private fun onRemoveItemClick(cartItem: CartItem, onFinish:() -> Unit) {
        cartItem.id ?: return
        viewModel.removeItemFromCart(cartItem.id!!,{
            // Cart item deleted successfully!
            viewModel.getCustomerCartItems(homeViewModel.getCustomer().docId)
            onFinish()
        }) {
            // Deleting cart item failed!
            Toast.makeText(requireContext(), "Error " + it.message, Toast.LENGTH_SHORT).show()
            onFinish()
        }
    }




}