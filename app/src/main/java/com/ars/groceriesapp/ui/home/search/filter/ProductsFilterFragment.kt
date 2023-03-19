package com.ars.groceriesapp.ui.home.search.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentProductsFilterBinding
import com.ars.groceriesapp.ui.home.search.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ProductsFilterFragment : BottomSheetDialogFragment(R.layout.fragment_products_filter) {

    private var _binding: FragmentProductsFilterBinding? = null
    private val binding get() = _binding!!


    private val viewModel by activityViewModels<SearchViewModel>()

    private lateinit var categoryFilterAdapter: FilterListAdapter<FilterCategory>
    private lateinit var sortingAdapter: FilterListAdapter<Sort>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductsFilterBinding.bind(view)

        sortingAdapter = FilterListAdapter(requireContext())
        categoryFilterAdapter = FilterListAdapter(requireContext())

        binding.categoryFilterRv.apply {
            adapter = categoryFilterAdapter
        }
        binding.sortingRv.apply {
            adapter = sortingAdapter
        }


        viewModel.filterCategory.observe(viewLifecycleOwner) {
            categoryFilterAdapter.setData(it ?: emptyList())
        }

        viewModel.sortValues.observe(viewLifecycleOwner) {
            sortingAdapter.setData(it ?: emptyList())
        }



        binding.filterApplyBtn.setOnClickListener {
            dismiss()
        }

        binding.filterResetBtn.setOnClickListener {
            viewModel.resetFilter()
            dismiss()
        }


    }



}