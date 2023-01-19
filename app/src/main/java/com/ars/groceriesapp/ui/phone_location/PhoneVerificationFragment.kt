package com.ars.groceriesapp.ui.phone_location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.ars.groceriesapp.databinding.FragmentPhoneVerificationBinding
import com.ars.groceriesapp.ui.startup.StartupViewModel


class PhoneVerificationFragment : Fragment() {

    private val binding by lazy { FragmentPhoneVerificationBinding.inflate(layoutInflater) }
    private val viewModel: PhoneLocationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView7.setOnClickListener {
            Navigation
                .findNavController(requireView())
                .navigate(PhoneVerificationFragmentDirections.toLocationFrag())
        }
    }


}