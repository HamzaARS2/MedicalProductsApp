package com.ars.groceriesapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.ars.domain.model.Category
import com.ars.domain.utils.Resource
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentShopBinding
import com.ars.groceriesapp.ui.epoxy.ShopEpoxyController
import com.ars.groceriesapp.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


const val TAG = "ShopFragment"
@AndroidEntryPoint
class ShopFragment : Fragment() {

    private val binding by lazy { FragmentShopBinding.inflate(layoutInflater) }
    private val viewModel: ShopViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var controller: ShopEpoxyController

    private val customerDocId by lazy { requireArguments().getString("customerDocId") }


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
        Toast.makeText(requireContext(), "customerDocId = $customerDocId", Toast.LENGTH_SHORT).show()

        binding.button.setOnClickListener {
            authViewModel.logout()
            val navController = Navigation
                .findNavController(requireView())
            navController.setGraph(R.navigation.auth_nav_graph)
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