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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.ars.domain.model.CartItem
import com.ars.domain.model.Category
import com.ars.domain.model.FavoriteProduct
import com.ars.domain.model.Product
import com.ars.domain.utils.Response
import com.ars.groceriesapp.HomeGraphDirections
import com.ars.groceriesapp.databinding.FragmentShopBinding
import com.ars.groceriesapp.ui.epoxy.controller.ShopEpoxyController
import com.ars.groceriesapp.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ShopFragment : Fragment() {

    companion object {
        const val TAG = "ShopFragmentTag"
    }

    private val binding by lazy { FragmentShopBinding.inflate(layoutInflater) }
    private val args by navArgs<ShopFragmentArgs>()
    private val viewModel: ShopViewModel by activityViewModels()
    private val homeViewModel by activityViewModels<HomeViewModel>()

    private lateinit var controller: ShopEpoxyController
    private lateinit var navController: NavController


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
        navController = Navigation.findNavController(view)
        controller = ShopEpoxyController(
            requireContext(),
            homeViewModel.getCustomer(),
            ::onCategoryClicked,
            ::onProductClicked,
            ::onAddToCartClick
        )
        binding.epoxyRv.setController(controller)


        viewModel.productsAndCategories.observe(viewLifecycleOwner) { response ->
            val (productsResponse, categoriesResponse) = response
            controller.setData(productsResponse.data, categoriesResponse.data)
        }


    }

    private fun onCategoryClicked(category: Category) {
        Toast.makeText(requireContext(), category.name, Toast.LENGTH_SHORT).show()
    }

    private fun onProductClicked(product: Product) {
        navController.navigate(
            ShopFragmentDirections
                .shopFragToProductDetailsFrag(product)
        )
    }

    private fun onAddToCartClick(productId: Int, onFinish: () -> Unit) {
        viewModel.saveCartItem(homeViewModel.getCustomer().docId, productId)
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Response.Success -> {
                        onFinish()
                        Toast.makeText(requireContext(), response.data, Toast.LENGTH_SHORT).show()
                    }
                    is Response.Error -> {
                        onFinish()
                        Toast.makeText(requireContext(), response.data, Toast.LENGTH_SHORT).show()
                    }
                    is Response.Loading -> {

                    }
                }
            }
    }


}