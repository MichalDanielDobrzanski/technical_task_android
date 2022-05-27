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
        viewModel.usersViewState.observe(viewLifecycleOwner, ::render)
    }

    private fun render(usersViewState: UsersViewState) {
        binding.homeProgressBar.isVisible = usersViewState.loaderVisible
        binding.homeErrorLayout.root.isVisible = usersViewState.errorVisible
        binding.homeUsersRecyclerView.isVisible = usersViewState.contentVisible
        binding.homeEmptyLayout.root.isVisible = usersViewState.emptyContentVisible

        if (usersViewState is UsersViewState.Content) {
            adapter?.submitList(usersViewState.userItems)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}