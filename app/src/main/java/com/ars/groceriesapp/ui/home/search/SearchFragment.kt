package com.ars.groceriesapp.ui.home.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ars.domain.model.Product
import com.ars.domain.utils.Response
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentSearchBinding
import com.ars.groceriesapp.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {


    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<SearchViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private lateinit var navController: NavController

    private lateinit var searchProductsAdapter: SearchProductsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        navController = Navigation.findNavController(view)
        searchProductsAdapter = SearchProductsAdapter(
            ::onProductClick,
            ::onProductAddToCartClick
        )

        binding.searchRv.apply {
            setHasFixedSize(true)
            adapter = searchProductsAdapter
        }

        binding.searchCancelBtn.setOnClickListener {
            navController.popBackStack()
        }

        handleSearch()

        viewModel.products.observe(viewLifecycleOwner) { response ->
            binding.searchProgress.isVisible = response is Response.Loading
            when(response) {
                is Response.Success -> {
                    searchProductsAdapter.differ.submitList(response.data)
                }
                is Response.Error -> {
                    Toast.makeText(requireContext(), response.error?.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                is Response.Loading -> {

                }
            }
        }

        binding.searchFilterBtn.setOnClickListener {
            navController.navigate(SearchFragmentDirections.searchToFilter())
        }

        observeAppliedFilter()

    }



    private fun handleSearch() {
        binding.exploreHeaderSearchSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchProducts(query ?: "",0)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "") {
                    viewModel.searchProducts("", 0)
                }
                return false
            }

        })
    }


    private fun onProductClick(productId: Int) {
        navController.navigate(SearchFragmentDirections.searchToProductDetails(productId))
    }

    private fun onProductAddToCartClick(productId: Int, onFinish: () -> Unit) {
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

    private fun observeAppliedFilter() {
        viewModel.filterCategory.observe(viewLifecycleOwner) { filterItem ->
            Log.d("appliedFilter", "observeAppliedFilter: filterCategory = " + filterItem?.filter { it.selected })
        }

        viewModel.sortValues.observe(viewLifecycleOwner) { filterItem ->
            Log.d("appliedFilter", "observeAppliedFilter: sortValues = " + filterItem?.filter { it.selected })
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}