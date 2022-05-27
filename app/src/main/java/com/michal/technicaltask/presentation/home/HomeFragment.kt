package com.michal.technicaltask.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.michal.technicaltask.databinding.FragmentHomeBinding
import com.michal.technicaltask.presentation.home.adapter.UsersAdapter
import com.michal.technicaltask.presentation.home.model.UsersViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var adapter: UsersAdapter? = null

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
        setupRecyclerView()

        binding.addUserButton.setOnClickListener {
            // TODO: launch dialog
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.homeUsersRecyclerView
        val adapter = UsersAdapter()
        recyclerView.adapter = adapter
        this.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.usersViewState.observe(viewLifecycleOwner, ::renderList)
    }

    private fun renderList(usersViewState: UsersViewState) {
        when (usersViewState) {
            UsersViewState.Loading -> {
                binding.homeProgressBar.isVisible = true
                binding.homeErrorLayout.root.isVisible = false
                binding.homeUsersRecyclerView.isVisible = false
                binding.homeEmptyLayout.root.isVisible = false
            }
            is UsersViewState.Content -> {
                binding.homeProgressBar.isVisible = false
                binding.homeErrorLayout.root.isVisible = false
                if (usersViewState.userItems.isEmpty()) {
                    binding.homeEmptyLayout.root.isVisible = true
                } else {
                    binding.homeUsersRecyclerView.isVisible = true
                    adapter?.submitList(usersViewState.userItems)
                }
            }
            UsersViewState.Error -> {
                binding.homeProgressBar.isVisible = false
                binding.homeErrorLayout.root.isVisible = true
                binding.homeUsersRecyclerView.isVisible = false
                binding.homeEmptyLayout.root.isVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}