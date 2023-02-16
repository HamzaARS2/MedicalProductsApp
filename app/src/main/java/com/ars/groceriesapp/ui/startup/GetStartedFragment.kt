package com.ars.groceriesapp.ui.startup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.ars.groceriesapp.StartingGraphDirections
import com.ars.groceriesapp.databinding.FragmentGetStartedBinding


class GetStartedFragment : Fragment() {

    private val binding by lazy { FragmentGetStartedBinding.inflate(layoutInflater) }
    private val viewModel: StartingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getstartedBtn.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(StartingGraphDirections.toAuthGraph())
        }

    }


}