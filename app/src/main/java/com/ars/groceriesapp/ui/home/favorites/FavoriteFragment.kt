package com.ars.groceriesapp.ui.home.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ars.domain.model.FavoriteProduct
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
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

    private lateinit var navController: NavController
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
        navController = Navigation.findNavController(view)
        deleteOnSwipe = DeleteOnSwipe(requireContext())
        favoritesAdapter =
            FavoritesAdapter(deleteOnSwipe, ::onFavoriteProductClick, ::onDeleteFavoriteProduct)
        initRecyclerView()
        viewModel.fetchFavoriteProducts(homeViewModel.getCustomer().docId)
            .observe(viewLifecycleOwner) { response ->
                favoritesAdapter.differ.submitList(response.data)
            }

        handleAddAllToCart()
    }

    private fun initRecyclerView() {
        binding.favoritesRv.apply {
            adapter = favoritesAdapter
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), LinearLayoutManager.VERTICAL
                )
            )

        }
    }

    private fun handleAddAllToCart() {
        binding.favoriteAddToCartBtn.setOnClickListener {
            binding.run {
                favoriteProgress.isVisible = true
                favoriteAddToCartBtn.visibility = View.INVISIBLE
            }
            viewModel.saveMultipleCartItems(homeViewModel.getCustomer().docId,favoritesAdapter.differ.currentList)
                .observe(viewLifecycleOwner) { response ->
                    binding.run {
                        favoriteProgress.isVisible = false
                        favoriteAddToCartBtn.visibility = View.VISIBLE
                    }
                    Toast.makeText(requireContext(), response.data, Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun onFavoriteProductClick(favoriteProduct: FavoriteProduct) {
        navController.navigate(FavoriteFragmentDirections.favoriteToProductDetails(favoriteProduct.product))
    }

    private fun onDeleteFavoriteProduct(favoriteProduct: FavoriteProduct, onFinish: () -> Unit) {
        viewModel.removeProductFromFavorites(
            homeViewModel.getCustomer().docId,
            favoriteProduct.product.id
        )
            .observe(viewLifecycleOwner) {
                if (it is Response.Error) {
                    Toast.makeText(requireContext(), it.data, Toast.LENGTH_SHORT).show()
                }
                onFinish()
            }
    }




}