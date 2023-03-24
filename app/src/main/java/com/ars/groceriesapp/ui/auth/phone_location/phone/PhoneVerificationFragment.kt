package com.ars.groceriesapp.ui.auth.phone_location.phone

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.ars.domain.validation.Validation
import com.ars.groceriesapp.AuthGraphDirections
import com.ars.groceriesapp.databinding.FragmentPhoneVerificationBinding
import com.ars.groceriesapp.ui.auth.phone_location.PhoneLocationViewModel
import com.ars.groceriesapp.PhoneVerifier
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider


class PhoneVerificationFragment : Fragment() {

    private val binding by lazy { FragmentPhoneVerificationBinding.inflate(layoutInflater) }
    private val viewModel: PhoneLocationViewModel by activityViewModels()

    private lateinit var phoneVerifier: PhoneVerifier

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.phoneVerifyNextBtn.setOnClickListener {
            val smsCode = binding.phoneVerifySmsCodeEdt.text.toString()
            val verificationId = viewModel.verificationId
            val result = Validation.validateSmsCode(smsCode)
            if (result.isValid)
            viewModel.linkPhoneWithCustomerAccount(verificationId,smsCode,::onSuccess,::onFailure)
            else
                Toast.makeText(requireContext(), "${result.message}", Toast.LENGTH_SHORT).show()
        }

        binding.phoneVerifyResendCodeBtn.setOnClickListener {
            resendSMSCode()
        }
    }

    private fun onSuccess(phone: String) {
        // Phone verified successfully
        viewModel.customer?.phone = phone
        Navigation
            .findNavController(requireView())
            .navigate(AuthGraphDirections.toHomeGraph(viewModel.customer!!))
    }

    private fun onFailure(e: Exception) {
        // Phone verification failed!
        Toast.makeText(requireContext(), "Error : ${e.message}", Toast.LENGTH_SHORT).show()

    }

    private val mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credentials: PhoneAuthCredential) {}
        override fun onVerificationFailed(e: FirebaseException) {
            Log.d("verifyPhoneNumber", "onVerificationFailed: ${e.message}")
            Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
        }

        override fun onCodeSent(
            verificationId: String,
            resendToken: PhoneAuthProvider.ForceResendingToken
        ) {

            viewModel.verificationId = verificationId
            viewModel.resendToken = resendToken
            Toast.makeText(requireContext(), "id = ${viewModel.verificationId} and resendCode = ${viewModel.resendToken}", Toast.LENGTH_SHORT).show()

        }
    }

    private fun resendSMSCode() {
        phoneVerifier = PhoneVerifier(viewModel.phoneNumber,requireActivity(),mCallback)
        phoneVerifier.resendSMSCode(viewModel.resendToken)

    }


}