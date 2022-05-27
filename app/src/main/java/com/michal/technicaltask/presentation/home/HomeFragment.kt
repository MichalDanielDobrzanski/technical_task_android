package com.michal.technicaltask.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.michal.technicaltask.databinding.FragmentHomeBinding
import com.michal.technicaltask.presentation.home.model.ListViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

        binding.addUserButton.setOnClickListener {
            // TODO: launch dialog
        }
    }

    private fun observeViewModel() {
        viewModel.listViewState.observe(viewLifecycleOwner, ::renderList)
    }

    private fun renderList(listViewState: ListViewState) {
        when (listViewState) {
            ListViewState.Loading -> {
                // TODO
            }
            is ListViewState.Content -> {
                // TODO
            }
            ListViewState.Error -> {
                // TODO
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}