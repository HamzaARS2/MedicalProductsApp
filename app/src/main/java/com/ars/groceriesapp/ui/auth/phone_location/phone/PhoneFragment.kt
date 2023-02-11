package com.ars.groceriesapp.ui.auth.phone_location.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.ars.groceriesapp.databinding.FragmentPhoneBinding
import com.ars.groceriesapp.ui.auth.phone_location.PhoneLocationViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


const val TAG = "PhoneFragmentTag"

@AndroidEntryPoint
class PhoneFragment : Fragment() {

    private val binding by lazy { FragmentPhoneBinding.inflate(layoutInflater) }
    private val viewModel: PhoneLocationViewModel by activityViewModels()
    private val args by navArgs<PhoneFragmentArgs>()

    private val navController by lazy { Navigation.findNavController(requireView()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel.customer = args.customer
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), "customerDocId = ${viewModel.customer.docId}", Toast.LENGTH_SHORT).show()

        binding.textView7.setOnClickListener {
           verifyPhoneNumber(binding.phonenumberEdt.text.toString())
        }
    }

    private fun verifyPhoneNumber(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder()
            .setPhoneNumber(phoneNumber)
            .setActivity(requireActivity())
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credentials: PhoneAuthCredential) {
                }

                override fun onVerificationFailed(e: FirebaseException) {
                }

                override fun onCodeSent(
                    verificationId: String,
                    resendToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    viewModel.verificationId = verificationId
                    navController.navigate(PhoneFragmentDirections.toPhoneVerificationFrag())
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

}