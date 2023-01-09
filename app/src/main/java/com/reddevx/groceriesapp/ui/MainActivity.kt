package com.reddevx.groceriesapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.reddevx.groceriesapp.databinding.ActivityMainBinding
import com.reddevx.groceriesapp.data.network.Resource
import com.reddevx.groceriesapp.ui.auth.RegisterViewModel
import com.reddevx.groceriesapp.utils.setBlackStatusBarIcons
import com.reddevx.groceriesapp.utils.setFullScreen
import dagger.hilt.android.AndroidEntryPoint


const val TAG = "MainActivityLog"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setFullScreen(window,binding.root)
        setBlackStatusBarIcons(window)
//        binding.signupBtn.setOnClickListener {
//            val name = binding.nameEdt.text.toString()
//            val email = binding.emailEdt.text.toString()
//            val password = binding.passwordEdt.text.toString()
//
//            viewModel.register(name,email, password)
//        }
//
//
//        binding.loginBtn.setOnClickListener {
//            val email = binding.emailEdt.text.toString()
//            val password = binding.passwordEdt.text.toString()
//
//            viewModel.login(email, password)
//        }
//
//        lifecycleScope.launchWhenStarted {
//            viewModel.customerState.collect { state ->
//                when(state) {
//                    is Resource.Success -> {
//                        setState("Success")
//                        Toast.makeText(this@MainActivity, state.result?.email, Toast.LENGTH_SHORT).show()
//                        Log.d(TAG, "onCreate: Customer = ${state.result.toString()}")
//                    }
//                    is Resource.Failure -> {
//                        setState("Failure")
//                        Log.d(TAG, "onCreate Failure : ${state.e}")
//                    }
//                    else -> {
//                        // Loading
//                        setState("Loading")
//                    }
//                }
//            }
//        }
//
//        binding.textView.setOnClickListener {
//            if (viewModel.loggedIn.value!!) {
//                viewModel.logout()
//            }
//        }
//
//            viewModel.loggedIn.observe(this) {
//                setIsLoggedIn(it)
//            }

    }

//    private fun setState(state: String) {
//        binding.stateTv.text = state
//    }
//
//    private fun setIsLoggedIn(isLoggedIn: Boolean) {
//        binding.textView.text = if (isLoggedIn)
//            "LoggedIn" else "LoggedOut"
//
//    }

}




















