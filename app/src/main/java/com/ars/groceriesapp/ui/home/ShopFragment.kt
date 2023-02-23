package com.ars.groceriesapp.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.ars.domain.model.Category
import com.ars.domain.utils.Resource
import com.ars.groceriesapp.HomeGraphDirections
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentShopBinding
import com.ars.groceriesapp.ui.epoxy.ShopEpoxyController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


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
        controller = ShopEpoxyController(requireContext(),viewModel.customer)
        binding.epoxyRv.setController(controller)
        collectExclusiveProducts()
        collectOnSaleProducts()
        collectMostRatedProducts()
        collectCategories()
        Toast.makeText(
            requireContext(),
            "customer = ${viewModel.customer.name}",
            Toast.LENGTH_SHORT
        ).show()

        binding.button.setOnClickListener {
            viewModel.logOut()
            navController.navigate(HomeGraphDirections.actionGlobalAuthGraph())
        }


    }


    private fun collectExclusiveProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.exclusivesFlow.collect { response ->
                    controller.setExclusiveProducts(response)
                }
            }
        }
    }

    private fun collectOnSaleProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.onSaleProductsFlow.collect { response ->
                    controller.setOnSaleProducts(response)
                }
            }
        }
    }

    private fun collectMostRatedProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mostRatedFlow.collect { response ->
                    controller.setMostRatedProducts(response)
                }
            }
        }
    }

    private fun collectCategories() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categoriesFlow.collect { response ->
                    Log.d("collectCategories", "collectCategories: $response")
                    controller.setCategories(response)
                }
            }
        }
    }


//    private fun getCategories(): List<Category> =
//        listOf(
//            Category(name = "Fruits & Vegetables"),
//            Category(
//                name = "Bakery & Snacks", image = R.drawable.baker_category_image,
//                color = "#40D3B0E0"
//            )
//        )


}