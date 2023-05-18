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
import com.ars.domain.model.OrderRequest
import com.ars.domain.model.OrderItem
import com.ars.domain.model.Product
import com.ars.domain.model.ProductDetails
import com.ars.domain.utils.Response
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentProductDetailsBinding
import com.ars.groceriesapp.ui.epoxy.controller.ProductDetailsEpoxyController
import com.ars.groceriesapp.ui.home.HomeViewModel


class ProductDetailsFragment : Fragment() {


    private val viewModel by activityViewModels<ProductDetailsViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val args by navArgs<ProductDetailsFragmentArgs>()

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var controller: ProductDetailsEpoxyController
    private lateinit var navController: NavController

    private var productDetails: ProductDetails? = null

    private var productQuantity: Int = 1


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
            homeViewModel,
            viewLifecycleOwner,
            ::onBackClick,
            ::onAddToCartClick,
            ::onFavoriteStateChanged,
            ::onAddProductToCartClick,
            ::onProductClick,
            ::onQuantityChanged
        )
        binding.productDetailsEpoxyRv.setController(controller)
        viewModel.getProductDetails(homeViewModel.getCustomer()?.id, productId)
        observeProductDetails()
        observeWholesaleMode()

        binding.productDetailsCheckoutBtn.setOnClickListener {
            val customer = homeViewModel.getCustomer()
            if (customer == null) {
                navigateToLogin()
                return@setOnClickListener
            }
            val newOrder = createOrder()
            newOrder ?: return@setOnClickListener
            navController.navigate(
                ProductDetailsFragmentDirections.productDetailsToCheckout(
                    newOrder
                )
            )
        }

    }

    private fun observeWholesaleMode() {
        homeViewModel.wholesaleMode.observe(viewLifecycleOwner) { isActive ->

        }
    }

    private fun onQuantityChanged(quantity: Int) {
        productQuantity = quantity
    }

    private fun createOrder(): OrderRequest? {

        productDetails ?: return null
        val subTotalPrice = productDetails?.price!!.times(productQuantity.toBigDecimal())
        val orderItems = listOf(
            OrderItem(
                productId = productDetails?.id!!,
                productName = productDetails?.name!!,
                productImage = productDetails?.image!!,
                productUnitPrice = productDetails?.priceUnit!!,
                quantity = productQuantity,
                subTotalPrice = subTotalPrice
            )
        )

        val customer = homeViewModel.getCustomer()!!

        return OrderRequest(
            customerId = customer.id,
            addressId = customer.address?.id!!,
            totalPrice = subTotalPrice,
            orderItems
        )
    }

    private fun observeProductDetails() {
        viewModel.liveProductDetails
            .observe(viewLifecycleOwner) { response ->

                binding.run {
                    productDetailsProgress.isVisible = response is Response.Loading
                    productDetailsCheckoutBtn.isVisible = response is Response.Success

                }

                when (response) {
                    is Response.Success -> {
                        this.productDetails = response.data
                        controller.setData(response.data)
                    }
                    is Response.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Error " + response.error?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("ProductDetails", "observeProductDetails: ${response.error?.message}")
                        navController.popBackStack()
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
        navController.navigate(R.id.auth_graph)
    }


}