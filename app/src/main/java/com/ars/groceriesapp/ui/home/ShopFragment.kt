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
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.ars.domain.model.Category
import com.ars.domain.utils.Resource
import com.ars.groceriesapp.HomeGraphDirections
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentShopBinding
import com.ars.groceriesapp.ui.epoxy.ShopEpoxyController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShopFragment : Fragment() {

    companion object {
        const val TAG = "ShopFragment"
    }

    private val binding by lazy { FragmentShopBinding.inflate(layoutInflater) }
    private val args by navArgs<ShopFragmentArgs>()
    private val viewModel: ShopViewModel by activityViewModels()

    private val navController by lazy { Navigation.findNavController(requireView()) }

    private lateinit var controller: ShopEpoxyController



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        args.customer?.let {
            viewModel.customer = it
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = ShopEpoxyController(requireContext())
        binding.epoxyRv.setController(controller)
        viewModel.fetchProducts()
        collectExclusiveProducts()
        collectProducts()
        Toast.makeText(requireContext(), "customerDocId = ${viewModel.customer.docId}", Toast.LENGTH_SHORT).show()

        binding.button.setOnClickListener {
            viewModel.logOut()
            navController.navigate(HomeGraphDirections.actionGlobalAuthGraph())
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