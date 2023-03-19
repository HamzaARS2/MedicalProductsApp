package com.ars.groceriesapp.ui.home.search.filter

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
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

    private lateinit var categoryFilterAdapter: FilterListAdapter
    private lateinit var sortingAdapter: FilterListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductsFilterBinding.bind(view)

        sortingAdapter = FilterListAdapter(requireContext())
        categoryFilterAdapter = FilterListAdapter(requireContext())

        binding.categoryFilterRv.apply {
            adapter = categoryFilterAdapter
            itemAnimator = null
        }
        binding.sortingRv.apply {
            adapter = sortingAdapter
            itemAnimator = null
        }


        viewModel.filterCategory.observe(viewLifecycleOwner) {
            Log.d("filterCategory", "onViewCreated: dataList = $it")
            categoryFilterAdapter.setData(it ?: emptyList())
        }

        viewModel.sortValues.observe(viewLifecycleOwner) {
            sortingAdapter.setData(it ?: emptyList())
        }



//        binding.filterApplyBtn.setOnClickListener {
//            dismiss()
//        }

        binding.filterResetBtn.setOnClickListener {
            categoryFilterAdapter.resetList()
            sortingAdapter.resetList()
            dismiss()
        }


    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        //viewModel.updateFilterLists(categoryFilterAdapter.items, sortingAdapter.items)
    }



}