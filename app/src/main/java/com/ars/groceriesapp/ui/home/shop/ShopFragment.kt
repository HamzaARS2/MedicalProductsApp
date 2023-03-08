package com.ars.groceriesapp.ui.home.shop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.ars.domain.model.CartItem
import com.ars.domain.model.Category
import com.ars.domain.model.FavoriteProduct
import com.ars.domain.model.Product
import com.ars.groceriesapp.HomeGraphDirections
import com.ars.groceriesapp.databinding.FragmentShopBinding
import com.ars.groceriesapp.ui.epoxy.controller.ShopEpoxyController
import com.ars.groceriesapp.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ShopFragment : Fragment() {

    companion object {
        const val TAG = "ShopFragment"
    }

    private val binding by lazy { FragmentShopBinding.inflate(layoutInflater) }
    private val args by navArgs<ShopFragmentArgs>()
    private val viewModel: ShopViewModel by activityViewModels()
    private val homeViewModel by activityViewModels<HomeViewModel>()

    private val navController by lazy { Navigation.findNavController(requireView()) }

    private lateinit var controller: ShopEpoxyController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        args.customer?.let {
            homeViewModel.setCustomer(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = ShopEpoxyController(
            requireContext(),
            homeViewModel.getCustomer(),
            ::onCategoryClicked,
            ::onProductClicked,
            ::onAddToCartClick
        )
        binding.epoxyRv.setController(controller)
        collectExclusiveProducts()
        collectCategories()
        collectOnSaleProducts()
        collectMostRatedProducts()




    }

    private fun onCategoryClicked(category: Category) {
        Toast.makeText(requireContext(), category.name, Toast.LENGTH_SHORT).show()
    }

    private fun onProductClicked(product: Product) {
        viewModel.saveFavoriteProduct(FavoriteProduct(
            customerId = homeViewModel.getCustomer().docId,
            productId = product.id
        ), {
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
        }) {
            Toast.makeText(requireContext(), "Error " + it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onAddToCartClick(product: Product, onFinish: () -> Unit) {
        viewModel.saveCartItem(CartItem(
            customerId = homeViewModel.getCustomer().docId,
            productId = product.id,
            quantity = 1
        ), {
            // Cart item saved successfully!
            onFinish()
            Toast.makeText(requireContext(), "Added Successfully", Toast.LENGTH_SHORT).show()
        }) {
            // Saving cart item failed!
            onFinish()
            Toast.makeText(requireContext(), "Error " + it.message, Toast.LENGTH_SHORT).show()
        }
    }


    private fun collectExclusiveProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.exclusivesFlow.collect { response ->
                    controller.setExclusiveProducts(response)

                }
            }
        }
    }

    private fun collectOnSaleProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.onSaleProductsFlow.collect { response ->
                    controller.setOnSaleProducts(response)
                }
            }
        }
    }

    private fun collectMostRatedProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mostRatedFlow.collect { response ->
                    controller.setMostRatedProducts(response)
                }
            }
        }
    }

    private fun collectCategories() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categoriesFlow.collect { response ->
                    Log.d("collectCategories", "collectCategories: $response")
                    controller.setCategories(response)
                }
            }
        }
    }


}