package com.ars.groceriesapp.ui.startup

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {


    private val binding by lazy { FragmentSplashBinding.inflate(layoutInflater) }
    private val viewModel: StartupViewModel by activityViewModels()

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
            navController.setGraph(
                if (viewModel.isLoggedIn())
                    R.navigation.home_nav_graph
                else
                    R.navigation.auth_nav_graph
            , bundleOf("customerDocId" to viewModel.customerDocId)
            )

        }
    }


}