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
import com.ars.domain.utils.Response
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

        cartAdapter =
            CartAdapter(::onIncreaseQuantityClick, ::onDecreaseQuantityClick, ::onRemoveItemClick)

        viewModel.getCustomerCartItems(homeViewModel.getCustomer().docId)
            .observe(viewLifecycleOwner) { response ->
                cartManager = CartManager(response.data ?: emptyList())
                observeCartManager()
                binding.cartProgress.isVisible = response is Response.Loading
                binding.cartCheckoutBtn.isVisible = response !is Response.Loading

                cartAdapter.differ.submitList(response.data)

            }
        addCartItemsListener()

        binding.cartRv.apply {
            adapter = cartAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), LinearLayoutManager.VERTICAL
                )
            )
        }


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