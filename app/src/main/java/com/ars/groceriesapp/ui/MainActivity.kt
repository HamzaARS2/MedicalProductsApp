package com.ars.groceriesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.ActivityMainBinding
import com.ars.groceriesapp.ui.auth.AuthViewModel
import com.ars.groceriesapp.utils.hideNavigationBars
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*


const val TAG = "MainActivityLog"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: AuthViewModel by viewModels()

    private lateinit var navController: NavController

    private var firstEmission = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //setBlackStatusBarIcons(window)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = navHostFragment.navController
        setBottomNavigationBarVisibility(navController)
        binding.bottomNavigationView.setupWithNavController(navController)


    }





//    private fun checkLoginState() {
//        lifecycleScope.launchWhenStarted {
//            viewModel.loginState
//                .collect { isLoggedIn ->
//                    if (!firstEmission) {
//                        Log.d(TAG, "checkLoginState: isLoggedIn = $isLoggedIn")
//
//                        if (isLoggedIn) {
//                            navController.setGraph(R.navigation.main_nav_graph)
//                            binding.bottomNavigationView.setupWithNavController(navController)
//                        } else {
//                            navController.setGraph(R.navigation.starting_nav_graph)
//                        }
//
//                    } else firstEmission = false
//
//
//                }
//        }
//    }

    private fun setBottomNavigationBarVisibility(navController: NavController) {
        navController.addOnDestinationChangedListener { _, dest, _ ->

            if (dest.id == R.id.shopFragment || dest.id == R.id.exploreFragment ||
                dest.id == R.id.cartFragment || dest.id == R.id.favoriteFragment ||
                dest.id == R.id.accountFragment
            ) {
                binding.bottomNavigationView.visibility = View.VISIBLE
                hideNavigationBars(window, binding.root, false)
            }
            else {
                binding.bottomNavigationView.visibility = View.GONE
            hideNavigationBars(window, binding.root, true)
        }

        }
    }

}




















