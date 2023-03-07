package com.ars.groceriesapp.ui.home.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ars.domain.model.FavoriteProduct
import com.ars.domain.utils.Resource
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentFavoriteBinding
import com.ars.groceriesapp.mapper.toCartItem
import com.ars.groceriesapp.ui.home.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment() {

    private val binding by lazy { FragmentFavoriteBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<FavoritesViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()

    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var deleteOnSwipe: DeleteOnSwipe
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deleteOnSwipe = DeleteOnSwipe(requireContext())
        favoritesAdapter =
            FavoritesAdapter(deleteOnSwipe, ::onFavoriteProductClick, ::onDeleteFavoriteProduct)
        initRecyclerView()
        viewModel.fetchFavoriteProducts(homeViewModel.getCustomer().docId)
        collectFavoriteProducts()

        handleAddAllToCart()

    }

    private fun handleAddAllToCart() {
        binding.favoriteAddToCartBtn.setOnClickListener {
            val cartItems = favoritesAdapter.differ.currentList.map { it.toCartItem() }
            viewModel.addMultipleCartItems(cartItems, {
                // Success
                Toast.makeText(requireContext(), "Added Successfully!", Toast.LENGTH_SHORT).show()
            }) {
                // Failed
                Toast.makeText(requireContext(), "Error " + it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onFavoriteProductClick(favoriteProduct: FavoriteProduct) {
        Toast.makeText(requireContext(), favoriteProduct.product!!.name, Toast.LENGTH_SHORT).show()
    }

    private fun onDeleteFavoriteProduct(favoriteProduct: FavoriteProduct, onFinish: () -> Unit) {
        viewModel.removeProductFromFavorites(favoriteProduct.id!!, {
            viewModel.fetchFavoriteProducts(homeViewModel.getCustomer().docId)
        }) {
            Toast.makeText(requireContext(), "Error : " + it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun collectFavoriteProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteProductsFlow.collectLatest { response ->
                    if (response is Resource.Success) {
                        favoritesAdapter.differ.submitList(response.result)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.favoritesRv.apply {
            ItemTouchHelper(deleteOnSwipe).attachToRecyclerView(this)
            adapter = favoritesAdapter
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), LinearLayoutManager.VERTICAL
                )
            )

        }
    }


}