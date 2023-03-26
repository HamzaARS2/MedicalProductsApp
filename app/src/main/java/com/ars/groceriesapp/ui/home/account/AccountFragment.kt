package com.ars.groceriesapp.ui.home.account

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.ars.groceriesapp.HomeGraphDirections
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentAccountBinding
import com.ars.groceriesapp.ui.home.HomeViewModel


class AccountFragment : Fragment(), View.OnClickListener {


    private lateinit var binding: FragmentAccountBinding
    private val viewModel by activityViewModels<AccountViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private lateinit var navController: NavController
    private val customer by lazy { homeViewModel.getCustomer() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())
        setListeners()
        displayCustomerInfo()


    }

    @SuppressLint("SetTextI18n")
    private fun displayCustomerInfo() {
        binding.apply {
            if (customer == null) {
                accountCustomerInfoGroup.visibility = View.INVISIBLE
                accountLoginBtn.isVisible = true
            } else {
                accountCustomerInfoGroup.visibility = View.VISIBLE
                accountLoginBtn.isVisible = false
                accountCustomerNameTv.text = "Welcome, ${customer!!.name}"
            }
        }
    }

    private fun setListeners() {
        binding.run {
            accountOrdersBtn.setOnClickListener(this@AccountFragment)
            accountDetailsBtn.setOnClickListener(this@AccountFragment)
            accountAddressBtn.setOnClickListener(this@AccountFragment)
//            accountPaymentBtn.setOnClickListener(this@AccountFragment)
            accountPromoBtn.setOnClickListener(this@AccountFragment)
            accountNotificationsBtn.setOnClickListener(this@AccountFragment)
            accountHelpBtn.setOnClickListener(this@AccountFragment)
            accountAboutBtn.setOnClickListener(this@AccountFragment)
            accountLoginBtn.setOnClickListener(this@AccountFragment)
            accountLogoutBtn.setOnClickListener(this@AccountFragment)
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.account_orders_btn -> {
                Toast.makeText(requireContext(), "account_orders_btn", Toast.LENGTH_SHORT).show()
            }
            R.id.account_details_btn -> {
                Toast.makeText(requireContext(), "account_details_btn", Toast.LENGTH_SHORT).show()
            }
            R.id.account_address_btn -> {
                Toast.makeText(requireContext(), "account_address_btn", Toast.LENGTH_SHORT).show()
            }
//            R.id.account_payment_btn -> {
//                Toast.makeText(requireContext(), "account_payment_btn", Toast.LENGTH_SHORT).show()
//            }
            R.id.account_promo_btn -> {
                Toast.makeText(requireContext(), "account_promo_btn", Toast.LENGTH_SHORT).show()
            }
            R.id.account_notifications_btn -> {
                Toast.makeText(requireContext(), "account_notifications_btn", Toast.LENGTH_SHORT).show()
            }
            R.id.account_help_btn -> {
                Toast.makeText(requireContext(), "account_help_btn", Toast.LENGTH_SHORT).show()
            }
            R.id.account_about_btn -> {
                Toast.makeText(requireContext(), "account_about_btn", Toast.LENGTH_SHORT).show()
            }
            R.id.account_login_btn -> {
                navController.navigate(R.id.auth_graph)
            }
            R.id.account_logout_btn -> {
                viewModel.logout()
                navController.navigate(HomeGraphDirections.actionGlobalAuthGraph())
            }
        }
    }

}