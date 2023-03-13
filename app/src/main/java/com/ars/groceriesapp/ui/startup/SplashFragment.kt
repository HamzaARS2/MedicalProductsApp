package com.ars.groceriesapp.ui.startup

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import com.ars.groceriesapp.databinding.FragmentSplashBinding
import kotlinx.coroutines.flow.collectLatest

class SplashFragment : Fragment() {


    private val binding by lazy { FragmentSplashBinding.inflate(layoutInflater) }
    private val viewModel: StartingViewModel by activityViewModels()

    private val navController by lazy { Navigation.findNavController(requireView()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            checkCustomerState()
        }, 2000)
        
    }


    private fun checkCustomerState() {
        if (viewModel.isFirstTimeLaunch()) {
            navController.navigate(SplashFragmentDirections.toGetStartedFrag())
            viewModel.setIsFirstTimeLaunch(false)
        } else {
            val isLoggedIn = viewModel.isLoggedIn().first
            val id = viewModel.isLoggedIn().second
            if (isLoggedIn) {
                observeCustomer(id!!)
            } else {
                navController.navigate(SplashFragmentDirections.toAuthGraph())
            }

        }
    }

    private fun observeCustomer(id: String) {
        viewModel.getCustomer(id).observe(viewLifecycleOwner) { response ->
            when(response) {
                is Response.Success -> {
                    val customer = response.data
                    if (customer != null)
                    navController.navigate(
                        SplashFragmentDirections.actionGlobalHomeGraph(
                            customer
                        )
                    )
                    else navController.navigate(SplashFragmentDirections.toAuthGraph())
                }
                is Response.Error -> {
                    val customer = response.data
                    if (customer != null)
                        navController.navigate(
                            SplashFragmentDirections.actionGlobalHomeGraph(
                                customer
                            )
                        )
                    else navController.navigate(SplashFragmentDirections.toAuthGraph())
                }
                is Response.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}