package com.ars.groceriesapp.ui.home.account.orders_history.track_order

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.ars.domain.model.Order
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentTrackOrderBinding
import com.ars.groceriesapp.utils.*

class TrackOrderFragment : Fragment(R.layout.fragment_track_order) {

    private var _binding: FragmentTrackOrderBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<TrackOrderFragmentArgs>()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTrackOrderBinding.bind(view)
        navController = Navigation.findNavController(view)
        displayOrderDetails(args.orderInfo)

        binding.trackOrderBackBtn.setOnClickListener {
            navController.popBackStack()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayOrderDetails(order: Order) = binding.apply {
        trackOrderIdTv.text = "Order#${order.id}"
        trackOrderDateTv.text = order.createdAt
        setOrderProgress(order.status)
    }

    private fun setOrderProgress(status: String) {
        when(status) {
            STATUS_DELIVERED -> setStepCompleted(4)
            STATUS_SHIPPED -> setStepCompleted(3)
            STATUS_PROCESSING -> setStepCompleted(2)
            STATUS_PENDING -> setStepCompleted(1)
        }
    }

    private fun setStepCompleted(completedSteps: Int) = binding.apply {
        val imageViews = listOf(trackOrderPlacedImv, trackOrderProcessingImv, trackOrderShippedImv, trackOrderDeliveredImv)
       repeat(completedSteps) { position ->
           imageViews[position].setImageResource(R.drawable.ic_check)
       }
    }

}