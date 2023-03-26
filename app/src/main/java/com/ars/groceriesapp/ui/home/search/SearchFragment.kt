package com.ars.groceriesapp.ui.home.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.ars.domain.utils.Response
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentSearchBinding
import com.ars.groceriesapp.ui.home.HomeViewModel
import com.ars.groceriesapp.ui.home.search.filter.Filter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {


    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<SearchViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private lateinit var navController: NavController
    private val args by navArgs<SearchFragmentArgs>()

    private lateinit var searchProductsAdapter: SearchProductsAdapter
    private lateinit var filterItemAdapter: FilterItemAdapter

    private var filter: Filter = Filter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        navController = Navigation.findNavController(view)

        viewModel.categoryId = args.categoryId
        viewModel.searchProducts(binding.exploreHeaderSearchSv.query.toString(), viewModel.categoryId)

        searchProductsAdapter = SearchProductsAdapter(
            ::onProductClick,
            ::onProductAddToCartClick
        )

        filterItemAdapter = FilterItemAdapter()

        binding.searchFilterItemsRv.adapter = filterItemAdapter

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
                    val sortedList = filter.sort(response.data)
                    searchProductsAdapter.differ.submitList(sortedList)
                    binding.searchRv.smoothScrollToPosition(0)
                }
                is Response.Error -> {
                    Toast.makeText(requireContext(), response.error?.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                is Response.Loading -> {

                }
            }
        }

        viewModel.appliedFilters.observe(viewLifecycleOwner) { updatedFilter ->
            this.filter = updatedFilter
            val query = binding.exploreHeaderSearchSv.query.toString()
            viewModel.searchProducts(query, viewModel.categoryId)
            filterItemAdapter.setItems(filter.getAppliedFiltersNames())

        }

        binding.searchFilterBtn.setOnClickListener {
            navController.navigate(SearchFragmentDirections.searchToFilter())
        }



    }


    private fun handleSearch() {
        binding.exploreHeaderSearchSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchProducts(query ?: "", viewModel.categoryId)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "") {
                    viewModel.searchProducts("", viewModel.categoryId)
                }
                return false
            }

        })
    }


    private fun onProductClick(productId: Int) {
        navController.navigate(SearchFragmentDirections.searchToProductDetails(productId))
    }

    private fun onProductAddToCartClick(productId: Int, onFinish: () -> Unit) {
        val customer = homeViewModel.getCustomer() ?: return
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



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}