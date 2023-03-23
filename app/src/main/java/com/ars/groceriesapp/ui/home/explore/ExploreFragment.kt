package com.ars.groceriesapp.ui.home.explore

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ars.domain.model.Category
import com.ars.domain.model.Product
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.FragmentExploreBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ExploreFragment : Fragment() {


    private val binding by lazy { FragmentExploreBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<ExploreViewModel>()

    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var navController: NavController



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        categoriesAdapter = CategoriesAdapter(::onCategoryClick)
        binding.exploreRv.run {
            adapter = categoriesAdapter
            setHasFixedSize(true)
        }
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { response ->
            categoriesAdapter.differ.submitList(response.data)
            when(response) {
                is Response.Success -> {
                }
                is Response.Error -> {
                }
                is Response.Loading -> {

                }
            }
       }

        binding.exploreSearchEdtContainer.setOnClickListener {
            navController.navigate(ExploreFragmentDirections.exploreToSearch())
        }

    }


    private fun onCategoryClick(category: Category) {
        navController.navigate(ExploreFragmentDirections.exploreToSearch(category.id))
    }


}