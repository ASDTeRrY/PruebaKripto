package com.kripto.pruebakripto.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.kripto.pruebakripto.ui.fragment.common.BaseFragment
import com.kripto.pruebakripto.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: RecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObserver()
    }

    private fun initViews() {
        adapter = RecyclerAdapter(requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when(state){
                    is HomeState.Error -> {}
                    is HomeState.Loading -> {}
                    is HomeState.Success -> {
                        adapter.submitList(state.products)
                    }
                }
            }
        }
    }
}