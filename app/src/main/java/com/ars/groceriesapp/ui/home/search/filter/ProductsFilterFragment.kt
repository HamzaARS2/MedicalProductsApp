package com.ars.groceriesapp.ui.home.search.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentProductsFilterBinding
import com.ars.groceriesapp.ui.home.search.SearchViewModel
import com.ars.groceriesapp.ui.home.search.filter.Filter.Companion.ASCENDING_PRICE
import com.ars.groceriesapp.ui.home.search.filter.Filter.Companion.DESCENDING_PRICE
import com.ars.groceriesapp.ui.home.search.filter.Filter.Companion.HIGH_RATED
import com.ars.groceriesapp.ui.home.search.filter.Filter.Companion.NEWEST
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ProductsFilterFragment : BottomSheetDialogFragment(R.layout.fragment_products_filter) {

    private var _binding: FragmentProductsFilterBinding? = null
    private val binding get() = _binding!!


    private val viewModel by activityViewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductsFilterBinding.bind(view)


        viewModel.appliedFilters.observe(viewLifecycleOwner) { filter ->
            binding.apply {
                highRatedFilterItem.isChecked = filter.appliedFiltersMap[HIGH_RATED]!!
                newestFilterItem.isChecked = filter.appliedFiltersMap[NEWEST]!!
                highToLowFilterItem.isChecked = filter.appliedFiltersMap[DESCENDING_PRICE]!!
                lowToHighFilterItem.isChecked = filter.appliedFiltersMap[ASCENDING_PRICE]!!
            }
        }


        binding.filterApplyBtn.setOnClickListener {
            val ids =  binding.filterGroup.checkedChipIds + binding.chipGroup.checkedChipIds
            val filter = Filter()
            filter.appliedFiltersMap[HIGH_RATED] = R.id.high_rated_filter_item in ids
            filter.appliedFiltersMap[NEWEST] = R.id.newest_filter_item in ids
            filter.appliedFiltersMap[DESCENDING_PRICE] = R.id.high_to_low_filter_item in ids
            filter.appliedFiltersMap[ASCENDING_PRICE] = R.id.low_to_high__filter_item in ids
            viewModel.updateFilters(filter)
            dismiss()
        }

        binding.filterResetBtn.setOnClickListener {
            viewModel.updateFilters(Filter())
            dismiss()
        }

    }






}