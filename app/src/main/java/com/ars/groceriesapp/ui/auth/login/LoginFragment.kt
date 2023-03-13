package com.ars.groceriesapp.ui.auth.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.ars.domain.model.Customer
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import com.ars.domain.validation.Validation
import com.ars.groceriesapp.AuthGraphDirections
import com.ars.groceriesapp.databinding.FragmentLoginBinding
import com.ars.groceriesapp.ui.startup.SplashFragmentDirections
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragment : Fragment() {


    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by activityViewModels()

    private val navController by lazy { Navigation.findNavController(requireView()) }

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
            val customerLiveData = viewModel.login(email, password, ::onValidation)
            observeCustomer(customerLiveData)

        }

        binding.loginSignupBtn.setOnClickListener {
            navController
                .navigate(LoginFragmentDirections.toRegister())
        }

        GlobalScope.launch {
            val goal = flowOf("ssss", "dddd", "aaa").toList()
            val assist = flowOf("aaa", "www", "edd").toList()
            val list = (goal + assist).distinctBy { it.first() }
            flowOf(list)
            Log.d("GlobalScope", "onViewCreated: $list")

        }

    }

    private fun observeCustomer(customerLiveData: LiveData<Response<Customer>>) {
        customerLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> {

                    val customer = response.data
                    if (customer != null) {
                        navController.navigate(
                            AuthGraphDirections.toHomeGraph(
                                customer
                            )
                        )
                        Toast.makeText(
                            requireContext(),
                            "Logged in Successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else Toast.makeText(
                        requireContext(),
                        "Something went wrong!",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                is Response.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${response.error?.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                is Response.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onValidation(response: Validation.LoginResponse) {
        binding.run {
            loginEmailLayout.error = response.emailMessage
            loginPasswordLayout.error = response.passwordMessage
        }
    }


}