package com.ars.groceriesapp.ui.auth.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.ars.domain.model.Customer
import com.ars.domain.utils.Resource
import com.ars.domain.validation.Validation
import com.ars.groceriesapp.AuthGraphDirections
import com.ars.groceriesapp.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest


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
            viewModel.login(email, password,::onValidation)

        }

        binding.loginSignupBtn.setOnClickListener {
            navController
                .navigate(LoginFragmentDirections.toRegister())
        }



        lifecycleScope.launchWhenStarted {
            viewModel.customerLoginFlow.collectLatest { state ->
                updateUi(state)
            }
        }
    }

    private fun updateUi(state: Resource<Customer>?) {
        Log.d("customerLoginFlow", "onViewCreated: State = $state")

        when (state) {
            is Resource.Success -> {
                Toast.makeText(requireContext(), "Logged in Successfully!", Toast.LENGTH_SHORT).show()

                val customer = state.result
                navController.navigate(AuthGraphDirections.toHomeGraph(customer))
            }
            is Resource.Failure -> {
                Snackbar.make(requireView(),"Error: ${state.e}",Snackbar.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                // TODO: Show loading view
            }
        }
    }

    private fun onValidation(response:  Validation.LoginResponse) {
        binding.run {
            loginEmailLayout.error = response.emailMessage
            loginPasswordLayout.error = response.passwordMessage
        }
    }


}