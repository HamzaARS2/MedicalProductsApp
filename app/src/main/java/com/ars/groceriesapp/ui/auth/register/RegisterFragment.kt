package com.ars.groceriesapp.ui.auth.register

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
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Validation
import com.ars.groceriesapp.AuthGraphDirections
import com.ars.groceriesapp.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest


class RegisterFragment : Fragment() {


    private val binding by lazy { FragmentRegisterBinding.inflate(layoutInflater) }
    private val viewModel: RegisterViewModel by activityViewModels()

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

        binding.registerSignupBtn.setOnClickListener {
            binding.apply {
                val name = registerUsernameLayout.editText!!.text.toString()
                val email = registerEmailLayout.editText!!.text.toString()
                val password = registerPasswordLayout.editText!!.text.toString()

                viewModel.register(name, email, password, ::onValidation)
            }

            binding.registerSigninBtn.setOnClickListener {
                navController
                    .popBackStack()
            }

            lifecycleScope.launchWhenStarted {
                viewModel.customerRegisterFlow.collectLatest { state ->
                    Log.d("customerRegisterFlow", "onViewCreated: State = $state")
                    when (state) {
                        is Resource.Success -> {
                            Toast.makeText(requireContext(), "Register Successfully!", Toast.LENGTH_SHORT).show()
                            val customer = state.result
                            navController.navigate(AuthGraphDirections.toPhoneLocationGraph(customer))
                        }
                        is Resource.Failure -> {
                            // TODO: Show an error to the user
                            Snackbar.make(requireView(),state.e.message ?: "Something went wrong!",Snackbar.LENGTH_SHORT).show()
                            Log.d("RegisterFragment", "onCreate Failure : ${state.e}")
                        }
                        else -> {
                            Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                            Log.d("RegisterLoading", "onViewCreated: Register Loading")
                            // TODO: Show loading view
                        }
                    }
                }
            }

        }


    }

    private fun onValidation(response: Validation.RegisterResponse) {
        binding.run {
            registerUsernameLayout.error = response.usernameMessage
            registerEmailLayout.error = response.emailMessage
            registerPasswordLayout.error = response.passwordMessage
        }
    }

}