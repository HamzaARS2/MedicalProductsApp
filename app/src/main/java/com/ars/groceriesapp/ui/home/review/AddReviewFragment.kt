package com.ars.groceriesapp.ui.home.review

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ars.domain.model.Product
import com.ars.domain.model.ProductReview
import com.ars.domain.utils.Response
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentAddReviewBinding


class AddReviewFragment : Fragment(R.layout.fragment_add_review) {

    private var _binding: FragmentAddReviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var productReviewAdapter: ProductReviewAdapter

    private val args by navArgs<AddReviewFragmentArgs>()
    private val viewModel by activityViewModels<ReviewViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddReviewBinding.bind(view)
        navController = Navigation.findNavController(view)
        productReviewAdapter = ProductReviewAdapter(::onProductClick)
        observeOrderProducts()

        binding.reviewProductsRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productReviewAdapter
            setHasFixedSize(true)
        }

        binding.addReviewBackBtn.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun observeOrderProducts() {
        viewModel.getOrderProducts(args.order.id).observe(viewLifecycleOwner) { response ->
            when(response) {
                is Response.Success -> productReviewAdapter.differ.submitList(response.data)
                is Response.Error -> Toast.makeText(requireContext(), response.error?.localizedMessage, Toast.LENGTH_SHORT).show()
                is Response.Loading -> Unit
            }
        }
    }

    private fun onProductClick(productReview: ProductReview?) {
        productReview ?: return
        navController.navigate(AddReviewFragmentDirections.addReviewToReviewProduct(productReview, args.order.customerId))
    }
}