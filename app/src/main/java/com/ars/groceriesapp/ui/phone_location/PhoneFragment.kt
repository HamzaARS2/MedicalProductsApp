package com.ars.groceriesapp.ui.phone_location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.ars.groceriesapp.databinding.FragmentPhoneBinding
import com.ars.groceriesapp.ui.startup.StartupViewModel


class PhoneFragment : Fragment() {

    private val binding by lazy { FragmentPhoneBinding.inflate(layoutInflater) }
    private val viewModel: PhoneLocationViewModel by activityViewModels()

    private val customerDocId by lazy { requireArguments().getString("customerDocId") }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), "customerDocId = $customerDocId", Toast.LENGTH_SHORT).show()
        viewModel.customerDocId = customerDocId!!
        binding.textView7.setOnClickListener {
            Navigation
                .findNavController(requireView())
                .navigate(PhoneFragmentDirections.toPhoneVerificationFrag())
        }
    }

}