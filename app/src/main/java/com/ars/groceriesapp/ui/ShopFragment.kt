package com.ars.groceriesapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ars.domain.model.Category
import com.ars.domain.utils.Resource
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentShopBinding
import com.ars.groceriesapp.ui.auth.AuthViewModel
import com.ars.groceriesapp.ui.epoxy.ShopEpoxyController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopFragment : Fragment() {

    private val binding by lazy { FragmentShopBinding.inflate(layoutInflater) }
    private val viewModel: ShopViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var controller: ShopEpoxyController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = ShopEpoxyController(requireContext())
        binding.epoxyRv.setController(controller)
        viewModel.fetchProducts()
        collectExclusiveProducts()
        collectProducts()

        binding.button.setOnClickListener {
             authViewModel.logout()
        }




    }

    private fun collectProducts() {
        lifecycleScope.launchWhenStarted {
            viewModel.products.collect { state ->
                when(state) {
                    is Resource.Success -> {
                        controller.setProducts(state.result)
                    }
                    is Resource.Failure -> {
                        // TODO: Show an error to the user
                        Log.d(TAG, "onCreate Failure : ${state.e}")
                    }
                    else -> {
                        // TODO: Show loading view
                    }
                }
            }
        }
    }

    private fun collectExclusiveProducts() {
        lifecycleScope.launchWhenStarted {
            viewModel.exclusiveProducts.collect { state ->
                when(state) {
                    is Resource.Success -> {
                        controller.setExclusiveProducts(state.result)
                        controller.setCategories(getCategories())
                    }
                    is Resource.Failure -> {
                        // TODO: Show an error to the user
                        Log.d(TAG, "onCreate Failure : ${state.e}")
                    }
                    else -> {
                        // TODO: Show loading view
                    }
                }
            }
        }
    }

    private fun getCategories(): List<Category> =
        listOf(
            Category(name = "Fruits & Vegetables"),
            Category(name = "Bakery & Snacks", image = R.drawable.baker_category_image,
                color = "#40D3B0E0")
        )

}