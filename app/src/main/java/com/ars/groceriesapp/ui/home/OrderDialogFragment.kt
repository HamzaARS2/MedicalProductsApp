package com.ars.groceriesapp.ui.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentOrderDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class OrderDialogFragment : DialogFragment(R.layout.fragment_order_dialog) {

    private var _binding: FragmentOrderDialogBinding? = null
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
        _binding = FragmentOrderDialogBinding.bind(view)
        navController = findNavController()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}