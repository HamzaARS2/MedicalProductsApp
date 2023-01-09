package com.reddevx.groceriesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.reddevx.groceriesapp.R
import com.reddevx.groceriesapp.databinding.FragmentShopBinding
import com.reddevx.groceriesapp.model.Category
import com.reddevx.groceriesapp.model.Product
import com.reddevx.groceriesapp.ui.epoxy.ShopEpoxyController

class ShopFragment : Fragment() {

    private val binding by lazy { FragmentShopBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = ShopEpoxyController()
        binding.epoxyRv.setController(controller)
        controller.setData(getProducts(),getCategories())

    }

    private fun getCategories(): List<Category> =
        listOf()

    private fun getProducts(): List<Product> =
        listOf(
            Product(),
            Product(),
            Product(),
            Product(),
            Product(),
            Product(),
            Product(),
            Product()
        )

}