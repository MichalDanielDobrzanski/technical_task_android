package com.michal.technicaltask.presentation.home.adduser

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.michal.technicaltask.R
import com.michal.technicaltask.databinding.FragmentAddUserBinding
import com.michal.technicaltask.presentation.home.adduser.model.AddUserParcel
import com.michal.technicaltask.presentation.utils.setWidthPercent

class AddUserDialogFragment : DialogFragment() {

    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var currentName: String? = null
    private var currentEmail: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.addUserCancelButton.setOnClickListener {
            dismiss()
        }

        binding.addUserOkButton.setOnClickListener {
            if (currentName.isNullOrEmpty()) {
                binding.addUserNameEditText.error = getString(R.string.empty_name_error)
            } else if (!isValidEmail(currentEmail)) {
                binding.addUserEmailEditText.error = getString(R.string.invalid_email_error)
            } else {
                setFragmentResult(
                    ADD_USER_DIALOG_REQUEST_KEY, bundleOf(
                        USER_PARCEL to AddUserParcel(
                            name = currentName!!,
                            email = currentEmail!!
                        )
                    )
                )
                dismiss()
            }
        }

        binding.addUserNameEditText.addTextChangedListener { text ->
            val name = text.toString()
            if (name.isEmpty()) {
                binding.addUserNameEditText.error = getString(R.string.empty_name_error)
            } else {
                currentName = text.toString()
            }
        }

        binding.addUserEmailEditText.addTextChangedListener { text ->
            val email = text.toString()
            if (isValidEmail(email)) {
                currentEmail = text.toString()
            } else {
                binding.addUserEmailEditText.error = getString(R.string.invalid_email_error)
            }
        }
    }

    private fun isValidEmail(email: String?): Boolean {
        return !email.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onResume() {
        super.onResume()
        setWidthPercent(DIALOG_WIDTH_PERCENT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

const val ADD_USER_DIALOG_REQUEST_KEY = "add_user_dialog_request_key"
const val USER_PARCEL = "user_parcel"
const val DIALOG_WIDTH_PERCENT = 90