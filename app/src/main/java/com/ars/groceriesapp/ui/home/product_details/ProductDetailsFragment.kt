package com.ars.groceriesapp.ui.home.product_details

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
import androidx.navigation.fragment.navArgs
import com.ars.domain.model.Product
import com.ars.domain.utils.Response
import com.ars.groceriesapp.HomeGraphDirections
import com.ars.groceriesapp.databinding.FragmentProductDetailsBinding
import com.ars.groceriesapp.ui.epoxy.controller.ProductDetailsEpoxyController
import com.ars.groceriesapp.ui.home.HomeViewModel
import com.ars.groceriesapp.ui.home.shop.ShopFragmentDirections


class ProductDetailsFragment : Fragment() {


    private val viewModel by activityViewModels<ProductDetailsViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val args by navArgs<ProductDetailsFragmentArgs>()

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var controller: ProductDetailsEpoxyController
    private lateinit var navController: NavController


    private var productId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        productId = args.productId
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        controller = ProductDetailsEpoxyController(
            requireContext(),
            ::onBackClick,
            ::onAddToCartClick,
            ::onFavoriteStateChanged,
            ::onAddProductToCartClick,
            ::onProductClick
        )
        binding.productDetailsEpoxyRv.setController(controller)
        viewModel.getProductDetails(homeViewModel.getCustomer()?.id, productId)
        observeProductDetails()

        binding.productDetailsCheckoutBtn.setOnClickListener {
            navController.navigate(ProductDetailsFragmentDirections.productDetailsToCheckout())
        }

    }

    private fun observeProductDetails() {
        viewModel.productDetails
            .observe(viewLifecycleOwner) { response ->

                controller.setData(response.data)
                binding.run {
                    productDetailsProgress.isVisible = response is Response.Loading
                    productDetailsCheckoutBtn.isVisible = response is Response.Success

                }

                when(response) {
                    is Response.Success -> {
                    }
                    is Response.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Error " + response.error?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("ProductDetails", "observeProductDetails: ${response.error?.message}")
                    }
                    is Response.Loading -> {
                    }

                }
            }
    }

    private fun onAddProductToCartClick(productId: Int, onFinish: () -> Unit) {
        val customer = homeViewModel.getCustomer()
        if (customer == null) {
            navigateToLogin()
            return
        }
        viewModel.saveCartItem(customer.id, productId)
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Response.Success -> {
                        Toast.makeText(requireContext(), response.data, Toast.LENGTH_SHORT).show()
                        onFinish()
                    }
                    is Response.Error -> {
                        Toast.makeText(requireContext(), response.data, Toast.LENGTH_SHORT).show()
                        onFinish()
                    }
                    is Response.Loading -> {

                    }
                }
            }
    }

    private fun onProductClick(product: Product) {
        val customer = homeViewModel.getCustomer()
        if (customer == null) {
            navigateToLogin()
            return
        }
        this.productId = product.id
        viewModel.getProductDetails(customer.id, product.id)
        binding.productDetailsEpoxyRv.smoothScrollToPosition(0)
    }

    private fun onBackClick() {
        navController.popBackStack()
    }

    private fun onAddToCartClick() {
        val customer = homeViewModel.getCustomer()
        if (customer == null) {
            navigateToLogin()
            return
        }
        viewModel.saveCartItem(customer.id, productId)
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Response.Success -> {
                        Toast.makeText(requireContext(), response.data, Toast.LENGTH_SHORT).show()
                    }
                    is Response.Error -> {
                        Toast.makeText(requireContext(), response.data, Toast.LENGTH_SHORT).show()
                    }
                    is Response.Loading -> {
                    }
                }
            }
    }

    private fun onFavoriteStateChanged(isChecked: Boolean, onFinish: () -> Unit) {
        val customer = homeViewModel.getCustomer()
        if (customer == null) {
            navigateToLogin()
            return
        }
        if (isChecked) {
            saveProductToFavorites(customer.id, onFinish)
        } else {
            removeProductFromFavorites(customer.id, onFinish)
        }

    }


    private fun saveProductToFavorites(customerId: String, onFinish: () -> Unit) {
        viewModel.saveFavoriteProduct(productId, customerId)
            .observe(viewLifecycleOwner) {
                if (it is Response.Error)
                Toast.makeText(requireContext(), it.data, Toast.LENGTH_SHORT).show()
                onFinish()
            }
    }

    private fun removeProductFromFavorites(customerId: String, onFinish: () -> Unit) {

        viewModel.removeProductFromFavorites(customerId, productId)
            .observe(viewLifecycleOwner) {
                if (it is Response.Error)
                Toast.makeText(requireContext(), it.data, Toast.LENGTH_SHORT).show()
                onFinish()
            }
    }

    private fun navigateToLogin() {
        navController.navigate(HomeGraphDirections.actionGlobalAuthGraph())
    }


}