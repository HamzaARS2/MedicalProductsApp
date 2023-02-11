package com.ars.groceriesapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.ActivityMainBinding
import com.ars.groceriesapp.utils.hideNavigationBars
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var navController: NavController

    private var firstEmission = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //setBlackStatusBarIcons(window)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = navHostFragment.navController
        //navController.setGraph(R.navigation.startup_nav_graph)
        setBottomNavigationBarVisibility(navController)
        binding.bottomNavigationView.setupWithNavController(navController)


    }


    private fun setBottomNavigationBarVisibility(navController: NavController) {
        navController.addOnDestinationChangedListener { _, dest, _ ->

            if (dest.id == R.id.shopFragment || dest.id == R.id.exploreFragment ||
                dest.id == R.id.cartFragment || dest.id == R.id.favoriteFragment ||
                dest.id == R.id.accountFragment
            ) {
                binding.bottomNavigationView.visibility = View.VISIBLE
                hideNavigationBars(window, binding.root, false)
                navController.graph.setStartDestination(R.id.home_graph)
            }
            else {
                binding.bottomNavigationView.visibility = View.GONE
            hideNavigationBars(window, binding.root, true)
                navController.graph.setStartDestination(R.id.starting_graph)
        }

        }
    }

}




















