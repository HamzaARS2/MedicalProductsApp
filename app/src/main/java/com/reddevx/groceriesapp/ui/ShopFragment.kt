package com.reddevx.groceriesapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.reddevx.groceriesapp.R
import com.reddevx.groceriesapp.data.network.Resource
import com.reddevx.groceriesapp.databinding.FragmentShopBinding
import com.reddevx.groceriesapp.model.Category
import com.reddevx.groceriesapp.model.Product
import com.reddevx.groceriesapp.ui.epoxy.ShopEpoxyController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopFragment : Fragment() {

    private val binding by lazy { FragmentShopBinding.inflate(layoutInflater) }
    private val viewModel: ShopViewModel by activityViewModels()

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
        //collectProducts()

        viewModel.liveProducts.observe(viewLifecycleOwner) {
            controller.setData(it,getCategories())
        }

    }

    private fun collectProducts() {
        lifecycleScope.launchWhenStarted {
            viewModel.products.collect { state ->
                when(state) {
                    is Resource.Success -> {
                        controller.setData(state.result,getCategories())
                        Log.d(TAG, "onCreate: Customer = ${state.result.toString()}")
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

    private fun getProducts(): List<Product> =
        listOf(
            Product(),
            Product(),
            Product(),
            Product(),
            Product(),
            Product(),
            Product(),
            Product()
        )

}