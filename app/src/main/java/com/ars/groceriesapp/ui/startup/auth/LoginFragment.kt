package com.ars.groceriesapp.ui.startup.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ars.domain.utils.Resource
import com.ars.groceriesapp.databinding.FragmentLoginBinding
import com.ars.groceriesapp.ui.home.TAG


class LoginFragment : Fragment() {


    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    private val viewModel: AuthViewModel by activityViewModels()

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

        binding.loginLoginBtn.setOnClickListener {
            val email = binding.loginEmailLayout.editText!!.text.toString()
            val password = binding.loginPasswordLayout.editText!!.text.toString()
            viewModel.login(email, password)
        }

        binding.imageView.setOnClickListener {
            viewModel.logout()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.customerState.collect { state ->
                when(state) {
                    is Resource.Success -> {
                        Toast.makeText(requireContext(), "Logged in Successfully!", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "onViewCreated: Customer = ${state.result}")
                    }
                    is Resource.Failure -> {
                        // TODO: Show an error to the user
                        Toast.makeText(requireContext(), "Error : ${state.e.message}", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "onCreate Failure : ${state.e}")
                    }
                    else -> {
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                        // TODO: Show loading view
                    }
                }
            }
        }


    }


}