package com.ars.groceriesapp.ui.home.review

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
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
import androidx.navigation.fragment.navArgs
import com.ars.domain.model.ProductReview
import com.ars.domain.model.ReviewRequest
import com.ars.domain.utils.Response
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentReviewBinding
import com.ars.groceriesapp.ui.home.HomeViewModel
import com.bumptech.glide.Glide

class ReviewFragment : Fragment(R.layout.fragment_review) {


    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    private val viewModel by activityViewModels<ReviewViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()

    private val args by navArgs<ReviewFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReviewBinding.bind(view)
        navController = Navigation.findNavController(view)

        displayProductReview(args.productReview)
        binding.reviewBackBtn.setOnClickListener {
            navController.popBackStack()
        }

        binding.reviewSubmitBtn.setOnClickListener {
            val productReview = args.productReview
            val customerId = args.customerId
            val rating = binding.reviewProductRatingBar.rating
            val comment = binding.reviewProductCommentEdt.text.toString()
            saveCustomerReview(productReview.id, customerId, rating, comment)
        }
    }

    private fun saveCustomerReview(productId: Int, customerId: String, rating: Float, comment: String) {
        viewModel.saveReview(productId, customerId, rating, comment).observe(viewLifecycleOwner) { response ->
            binding.reviewSubmitProgress.isVisible = response is Response.Loading
            when (response) {
                is Response.Success -> {
                    Toast.makeText(requireContext(), "Review Saved!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                    binding.reviewSubmitBtn.visibility = View.VISIBLE
                }
                is Response.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.error?.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.reviewSubmitBtn.visibility = View.VISIBLE
                }
                is Response.Loading -> {
                    binding.reviewSubmitBtn.visibility = View.INVISIBLE
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayProductReview(productReview: ProductReview) {
        binding.apply {
            Glide.with(reviewProductImageImv).load(productReview.image).into(reviewProductImageImv)
            reviewProductNameTv.text = productReview.name
            reviewProductUnitPriceTv.text = productReview.priceUnit
            reviewProductPriceTv.text = "$${productReview.price}"
            reviewProductRatingBar.rating = productReview.customerRating
            reviewProductCommentEdt.setText(productReview.customerComment)
        }
    }


}