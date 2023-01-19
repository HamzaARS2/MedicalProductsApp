package com.ars.groceriesapp.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.ars.domain.utils.Resource
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentRegisterBinding
import com.ars.groceriesapp.ui.home.TAG


class RegisterFragment : Fragment() {


    private val binding by lazy { FragmentRegisterBinding.inflate(layoutInflater) }
    private val viewModel: AuthViewModel by activityViewModels()

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
                val phone = "unknown"
                val address = "unknown"

                viewModel.register(name, email, password, phone, address)
            }

            binding.registerSigninBtn.setOnClickListener {
                navController
                    .popBackStack()
            }

            lifecycleScope.launchWhenStarted {
                viewModel.customerState.collect { state ->
                    when (state) {
                        is Resource.Success -> {
                            Toast.makeText(
                                requireContext(),
                                "Register Successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController
                                .setGraph(
                                    R.navigation.phone_location_nav_graph,
                                    bundleOf("customerDocId" to state.result?.docId)
                                )
                        }
                        is Resource.Failure -> {
                            // TODO: Show an error to the user
                            Toast.makeText(
                                requireContext(),
                                "Error : ${state.e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
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

}