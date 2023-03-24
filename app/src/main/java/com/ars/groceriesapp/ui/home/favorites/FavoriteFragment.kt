package com.ars.groceriesapp.ui.home.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ars.domain.model.FavoriteProduct
import com.ars.domain.utils.Response
import com.ars.groceriesapp.databinding.FragmentFavoriteBinding
import com.ars.groceriesapp.ui.home.HomeViewModel


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
        fetchFavorites()


        handleAddAllToCart()
    }

    private fun fetchFavorites() {

        val customer = homeViewModel.getCustomer()
        if (customer == null) {
            // Show a dialog to ask the user to login first
            displayDialog()
            return
        }
        viewModel.fetchFavoriteProducts(customer.id)
            .observe(viewLifecycleOwner) { response ->
                favoritesAdapter.differ.submitList(response.data)
            }
    }

    private fun displayDialog() {

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
            val customer = homeViewModel.getCustomer()
            if (customer == null) {
                // Show a dialog to ask the user to login first
                displayDialog()
                return@setOnClickListener
            }
            binding.run {
                favoriteProgress.isVisible = true
                favoriteAddToCartBtn.visibility = View.INVISIBLE
            }
            viewModel.saveMultipleCartItems(customer.id, favoritesAdapter.differ.currentList)
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
        navController.navigate(FavoriteFragmentDirections.favoriteToProductDetails(favoriteProduct.product.id))
    }

    private fun onDeleteFavoriteProduct(favoriteProduct: FavoriteProduct, onFinish: () -> Unit) {
        val customer = homeViewModel.getCustomer() ?: return
        viewModel.removeProductFromFavorites(
            customer.id,
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