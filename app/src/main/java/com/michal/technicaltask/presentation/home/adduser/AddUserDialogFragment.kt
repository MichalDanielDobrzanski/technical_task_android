package com.michal.technicaltask.presentation.home.adduser

import android.os.Bundle
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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddUserDialogFragment : DialogFragment() {

    @Inject
    lateinit var addUserDelegate: AddUserDelegate

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
            addUserDelegate.validateNameAndEmail(
                currentName,
                currentEmail,
                onInvalidName = { showNameError() },
                onInvalidEmail = { showEmailError() },
                onValidData = { name, email ->
                    setFragmentResult(
                        ADD_USER_DIALOG_REQUEST_KEY, bundleOf(
                            USER_PARCEL to AddUserParcel(
                                name = name,
                                email = email
                            )
                        )
                    )
                    dismiss()
                }
            )
        }

        binding.addUserNameEditText.addTextChangedListener { text ->
            val name = text.toString()
            if (addUserDelegate.isValidName(name)) {
                currentName = name
            } else {
                showNameError()
            }
        }

        binding.addUserEmailEditText.addTextChangedListener { text ->
            val email = text.toString()
            if (addUserDelegate.isValidEmail(email)) {
                currentEmail = email
            }
        }
    }

    private fun showEmailError() {
        binding.addUserEmailEditText.error = getString(R.string.invalid_email_error)
    }

    private fun showNameError() {
        binding.addUserNameEditText.error = getString(R.string.empty_name_error)
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