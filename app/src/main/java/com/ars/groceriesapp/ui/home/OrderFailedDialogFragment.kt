package com.ars.groceriesapp.ui.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentOrderFailedDialogBinding


class OrderFailedDialogFragment : DialogFragment(R.layout.fragment_order_failed_dialog) {
    private var _binding: FragmentOrderFailedDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
            .apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderFailedDialogBinding.bind(view)
        navController = findNavController()

        binding.goBackBtn.setOnClickListener {
            dismiss()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}