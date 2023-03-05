package com.ars.groceriesapp.ui.home.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ars.domain.model.CartItem
import com.ars.domain.model.Product
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentCartBinding
import java.math.BigDecimal


class CartFragment : Fragment(R.layout.fragment_cart) {


    private val binding by lazy { FragmentCartBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<CartViewModel>()


    private lateinit var cartManager: CartManager
    private lateinit var cartAdapter: CartAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartManager = CartManager(getCartItems())
        cartAdapter = CartAdapter(getCartItems(), ::onIncreaseQuantityClick, ::onDecreaseQuantityClick)

        binding.cartRv.apply {
            adapter = cartAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), LinearLayoutManager.VERTICAL
                )
            )
        }

        cartManager.apply {
            cartItem.observe(viewLifecycleOwner) { (cartItem, position) ->
                cartAdapter.updateCartItem(cartItem, position)
            }

            totalPrice.observe(viewLifecycleOwner) { totalPrice ->
                binding.cartTotalPriceTv.text = totalPrice
            }
        }

    }

    private fun onIncreaseQuantityClick(position: Int) {
        cartManager.increaseQuantity(position)
    }

    private fun onDecreaseQuantityClick(position: Int) {
        cartManager.decreaseQuantity(position)
    }


    private fun getCartItems() =
        listOf(
            CartItem(
                1, "eMmEGJKwVyO6hFRcz7RGGE9teEb2",
                Product(
                    1, 1, "Organic Bananas", BigDecimal.valueOf(4.99), "1kg, price",
                    "https://firebasestorage.googleapis.com/v0/b/autowallpaper-f2aa7.appspot.com/o/images%2Fbananas_product.png?alt=media&token=bd9fe017-7b7a-454c-88d2-711bc12f4d34"
                ),
                1,
            ),

            CartItem(
                4, "eMmEGJKwVyO6hFRcz7RGGE9teEb2",
                Product(
                    4, 4, "Chicken Eggs", BigDecimal.valueOf(2.97), "10pcs, price",
                    "https://firebasestorage.googleapis.com/v0/b/autowallpaper-f2aa7.appspot.com/o/images%2Fegg_checken_red_product.png?alt=media&token=c2d03d9c-d20e-4a9d-a0d0-bc37b300a351"
                ),
                1,
            ),

            CartItem(
                3, "eMmEGJKwVyO6hFRcz7RGGE9teEb2",
                Product(
                    3, 3, "Chicken Breast", BigDecimal.valueOf(10.99), "1kg, price",
                    "https://firebasestorage.googleapis.com/v0/b/autowallpaper-f2aa7.appspot.com/o/images%2Fchecken_product.png?alt=media&token=48f0f022-dc1b-4498-9ac2-cbad184090f7"
                ),
                1,
            ),

            CartItem(
                2, "eMmEGJKwVyO6hFRcz7RGGE9teEb2",
                Product(
                    2, 1, "Red Apples", BigDecimal.valueOf(3.90), "1kg, price",
                    "https://firebasestorage.googleapis.com/v0/b/autowallpaper-f2aa7.appspot.com/o/images%2Fapple_product.png?alt=media&token=d902df3f-b3cb-4e35-a38d-d06f8ef4e1dd"
                ),
                1,
            ),
            CartItem(
                2, "eMmEGJKwVyO6hFRcz7RGGE9teEb2",
                Product(
                    2, 1, "Red Apples", BigDecimal.valueOf(3.90), "1kg, price",
                    "https://firebasestorage.googleapis.com/v0/b/autowallpaper-f2aa7.appspot.com/o/images%2Fapple_product.png?alt=media&token=d902df3f-b3cb-4e35-a38d-d06f8ef4e1dd"
                ),
                1,
            )
        )


}