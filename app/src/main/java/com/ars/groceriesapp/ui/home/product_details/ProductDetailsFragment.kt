package com.ars.groceriesapp.ui.home.product_details

import android.os.Bundle
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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.ars.domain.model.ProductDetails
import com.ars.domain.utils.Resource
import com.ars.groceriesapp.databinding.FragmentProductDetailsBinding
import com.ars.groceriesapp.mapper.toCartItem
import com.ars.groceriesapp.mapper.toFavoriteProduct
import com.ars.groceriesapp.ui.epoxy.controller.ProductDetailsEpoxyController
import com.ars.groceriesapp.ui.home.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductDetailsFragment : Fragment() {


    private val viewModel by activityViewModels<ProductDetailsViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val args by navArgs<ProductDetailsFragmentArgs>()

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var controller: ProductDetailsEpoxyController
    private lateinit var navController: NavController

    private var productDetails: ProductDetails? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewModel.getProductDetails(args.product.id)
        viewModel.getProductReviews(args.product.id)
        controller = ProductDetailsEpoxyController(
            requireContext(),
            ::onBackClick,
            ::onAddToCartClick,
            ::onFavoriteStateChanged,
        )
        binding.productDetailsEpoxyRv.setController(controller)

        collectReviews()

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productDetailsFlow.collectLatest { response ->
                    when (response) {
                        is Resource.Success -> {
                            controller.setData(response.result)
                            productDetails = response.result
                            binding.productDetailsProgress.isVisible = false
                        }
                        is Resource.Failure -> {
                            Toast.makeText(
                                requireContext(),
                                "Error " + response.e.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.productDetailsProgress.isVisible = false
                        }
                        else -> binding.productDetailsProgress.isVisible = true
                    }
                }
            }
        }

    }

    private fun collectReviews() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.reviewsFlow.collectLatest { response ->
                    when(response) {
                        is Resource.Success -> {

                        }
                        is Resource.Failure -> {

                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }

    private fun onBackClick() {
        navController.popBackStack()
    }

    private fun onAddToCartClick() {
        viewModel.saveCartItem(args.product.toCartItem(homeViewModel.getCustomer().docId), {
            // Added to cart successfully!
            Toast.makeText(requireContext(), "Added successfully!", Toast.LENGTH_SHORT).show()
        }) {
            // Failed
            Toast.makeText(requireContext(), "Error " + it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onFavoriteStateChanged(isChecked: Boolean, onFinish: () -> Unit) {
        if (isChecked) {
            saveProductToFavorites(onFinish)
        } else {
            removeProductFromFavorites(onFinish)
        }

    }


    private fun saveProductToFavorites(onFinish: () -> Unit) {
        viewModel.saveFavoriteProduct(
            args.product.toFavoriteProduct(homeViewModel.getCustomer().docId),
            {
                Toast.makeText(requireContext(), "Added successfully!", Toast.LENGTH_SHORT).show()
                onFinish()
            }) {
            // Failed
            onFinish()
            Toast.makeText(requireContext(), "Error " + it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeProductFromFavorites(onFinish: () -> Unit) {
        viewModel.removeProductFromFavorites(homeViewModel.getCustomer().docId, args.product.id, {
            onFinish()
            Toast.makeText(requireContext(), "Removed from favorites", Toast.LENGTH_SHORT).show()
        }) {
            // Failed
            onFinish()
            Toast.makeText(requireContext(), "Error " + it.message, Toast.LENGTH_SHORT).show()
        }
    }


}