package com.ars.groceriesapp.ui.home.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.ars.domain.model.Category
import com.ars.domain.model.Product
import com.ars.domain.utils.Response
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentShopBinding
import com.ars.groceriesapp.ui.epoxy.controller.ShopEpoxyController
import com.ars.groceriesapp.ui.home.HomeViewModel
import com.ars.groceriesapp.ui.home.search.filter.Filter
import com.ars.groceriesapp.ui.home.search.filter.Filter.Companion.EXCLUSIVE_FILTER
import com.ars.groceriesapp.ui.home.search.filter.Filter.Companion.HIGH_RATED
import com.ars.groceriesapp.ui.home.search.filter.Filter.Companion.ON_SALE_FILTER
import com.ars.groceriesapp.utils.EXCLUSIVE_SECTION
import com.ars.groceriesapp.utils.MOST_RATED_SECTION
import com.ars.groceriesapp.utils.ON_SALE_SECTION
import com.ars.groceriesapp.utils.POPULAR_CATEGORIES_SECTION
import dagger.hilt.android.AndroidEntryPoint


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
            ::onAddToCartClick,
            ::onSearchClick,
            ::onAddressClick,
            ::onSeeAllClick
        )
        binding.epoxyRv.setController(controller)


        viewModel.productsAndCategories.observe(viewLifecycleOwner) { response ->
            val (productsResponse, categoriesResponse) = response
            controller.setData(productsResponse.data, categoriesResponse.data)
        }


    }

    private fun onAddressClick() {
        val customer = homeViewModel.getCustomer()
        if (customer == null) {
            navigateToLogin()
            return
        }
        navController.navigate(ShopFragmentDirections.shopToAddress())
    }

    private fun onSeeAllClick(section: String) {
        val filter = Filter()
        when (section) {
            EXCLUSIVE_SECTION -> {
                filter.appliedFiltersMap[EXCLUSIVE_FILTER] = true
            }
            POPULAR_CATEGORIES_SECTION -> {
                navController.navigate(R.id.exploreFragment)
                return
            }
            ON_SALE_SECTION -> {
                filter.appliedFiltersMap[ON_SALE_FILTER] = true
            }
            MOST_RATED_SECTION -> {
                filter.appliedFiltersMap[HIGH_RATED] = true
            }
        }
            navController.navigate(ShopFragmentDirections.shopToSearch(filterInfo = filter))

    }
    private fun onCategoryClicked(category: Category) {
        Toast.makeText(requireContext(), category.name, Toast.LENGTH_SHORT).show()
    }

    private fun onProductClicked(product: Product) {
        navController.navigate(
            ShopFragmentDirections
                .shopFragToProductDetailsFrag(product.id)
        )
    }

    private fun onSearchClick() {
        navController.navigate(ShopFragmentDirections.shopToSearch())
    }

    private fun onAddToCartClick(productId: Int, onFinish: () -> Unit) {
        val customer = homeViewModel.getCustomer()
        if (customer == null) {
            navigateToLogin()
            onFinish()
            return
        }
        viewModel.saveCartItem(customer.id, productId)
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

    private fun navigateToLogin() {
        navController.navigate(R.id.auth_graph)
    }


}