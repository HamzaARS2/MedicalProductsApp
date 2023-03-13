package com.ars.groceriesapp.ui.home.explore

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ars.domain.model.Category
import com.ars.domain.model.Product
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentExploreBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ExploreFragment : Fragment() {


    private val binding by lazy { FragmentExploreBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<ExploreViewModel>()
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var productsAdapter: ProductsAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoriesAdapter = CategoriesAdapter(::onCategoryClick)
        productsAdapter = ProductsAdapter(::onProductClick, ::onProductAddToCartClick)
        binding.exploreRv.run {
            adapter = categoriesAdapter
            setHasFixedSize(true)
        }

        collectSearchedProducts()

        binding.clearSearchBtn.setOnClickListener {
            binding.exploreHeaderSearchEdt.setText("")
        }

        handleSearchViewsChanges()

        binding.exploreHeaderSearchEdt.addTextChangedListener(textWatcher)

        binding.exploreSearchIcon.setOnClickListener {
            binding.run {
                exploreHeaderSearchEdt.clearFocus()
                exploreRv.adapter = categoriesAdapter
                exploreSearchIcon.setImageResource(R.drawable.ic_search)
                binding.exploreHeaderSearchEdt.setText("")
                clearSearchBtn.isVisible = false
            }

        }

        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { response ->
            categoriesAdapter.differ.submitList(response.data)
            when(response) {
                is Response.Success -> {
                }
                is Response.Error -> {
                }
                is Response.Loading -> {

                }
            }
       }




    }

    private val textWatcher = object : TextWatcher {
        private val handler = Handler(Looper.getMainLooper())
        private var runnable: Runnable? = null

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            runnable?.let {
                handler.removeCallbacks(it)
            }
        }

        override fun afterTextChanged(editable: Editable?) {
            runnable = Runnable {
                editable?.let {
                    if (it.toString().isBlank()) return@let
                    viewModel.searchProduct(it.toString())
                }
            }
            handler.postDelayed(runnable!!, 500)
        }

    }


    private fun handleSearchViewsChanges() {
        binding.exploreHeaderSearchEdt.setOnFocusChangeListener { _, isFocused ->
            if (isFocused) {
                binding.run {
                    exploreRv.adapter = productsAdapter
                    exploreSearchIcon.setImageResource(R.drawable.ic_back_arrow_2)
                    clearSearchBtn.isVisible = true
                }
            }

            binding.exploreFindProductsTitle.isVisible = !isFocused

        }
    }

    private fun collectSearchedProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productsFlow.collectLatest { response ->
                    if (response is Resource.Success) {
                        productsAdapter.differ.submitList(response.result!!)
                    }
                }
            }
        }
    }


    private fun onCategoryClick(category: Category) {
        Toast.makeText(requireContext(), category.name, Toast.LENGTH_SHORT).show()
    }

    private fun onProductClick(product: Product) {
        Toast.makeText(requireContext(), product.name, Toast.LENGTH_SHORT).show()
    }

    private fun onProductAddToCartClick(product: Product) {
        Toast.makeText(requireContext(), product.name + " Added to cart", Toast.LENGTH_SHORT).show()
    }


}