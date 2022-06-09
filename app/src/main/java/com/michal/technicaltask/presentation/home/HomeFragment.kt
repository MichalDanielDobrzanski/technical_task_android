package com.michal.technicaltask.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.michal.technicaltask.R
import com.michal.technicaltask.databinding.FragmentHomeBinding
import com.michal.technicaltask.presentation.home.adapter.UsersAdapter
import com.michal.technicaltask.presentation.home.adduser.ADD_USER_DIALOG_REQUEST_KEY
import com.michal.technicaltask.presentation.home.adduser.AddUserDialogFragment
import com.michal.technicaltask.presentation.home.adduser.USER_PARCEL
import com.michal.technicaltask.presentation.home.adduser.model.AddUserParcel
import com.michal.technicaltask.presentation.home.model.UsersViewState
import com.michal.technicaltask.presentation.home.removeuser.REMOVE_USER_DIALOG_BUNDLE_KEY
import com.michal.technicaltask.presentation.home.removeuser.REMOVE_USER_DIALOG_REQUEST_KEY
import com.michal.technicaltask.presentation.home.removeuser.RemoveUserDialogFragment
import com.michal.technicaltask.presentation.home.removeuser.RemoveUserParcel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var adapter: UsersAdapter? = null

    private var _binding: FragmentHomeBinding? = null
    val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(ADD_USER_DIALOG_REQUEST_KEY) { _, bundle ->
            val addUserParcel = bundle.getParcelable<AddUserParcel>(USER_PARCEL) ?: return@setFragmentResultListener
            val (name, email) = addUserParcel
            viewModel.addNewUser(name, email)
        }
        setFragmentResultListener(REMOVE_USER_DIALOG_REQUEST_KEY) { _, bundle ->
            val removeUserParcel =
                bundle.getParcelable<RemoveUserParcel>(REMOVE_USER_DIALOG_BUNDLE_KEY) ?: return@setFragmentResultListener
            val userItem = removeUserParcel.toUserItem()
            viewModel.onUserRemoved(userItem)
        }
    }

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
            AddUserDialogFragment().show(parentFragmentManager, null)
        }

        binding.homeErrorLayout.retryButton.setOnClickListener {
            viewModel.getAllUsers()
        }
    }

    private fun setupRecyclerView() {
        val adapter = UsersAdapter { userItem ->
            RemoveUserDialogFragment.create(userItem).show(parentFragmentManager, null)
        }
        binding.homeUsersRecyclerView.adapter = adapter
        this.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.usersViewState.observe(viewLifecycleOwner, ::render)

        viewModel.operationFailedSingleLiveEvent.observe(viewLifecycleOwner) {
            Toast.makeText(context, R.string.operation_failed_error, Toast.LENGTH_SHORT).show()
        }
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

    override fun onResume() {
        super.onResume()
        viewModel.listenToRefreshUserList()
    }

    override fun onPause() {
        super.onPause()
        viewModel.removeRefreshUserListListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}