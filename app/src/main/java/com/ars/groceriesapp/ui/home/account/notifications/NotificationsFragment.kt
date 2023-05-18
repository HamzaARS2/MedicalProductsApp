package com.ars.groceriesapp.ui.home.account.notifications

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotificationsBinding.bind(view)
        navController = Navigation.findNavController(view)

        binding.notificationsRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = NotificationsAdapter()
            setHasFixedSize(true)
        }

        binding.notificationsBackBtn.setOnClickListener {
            navController.popBackStack()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}