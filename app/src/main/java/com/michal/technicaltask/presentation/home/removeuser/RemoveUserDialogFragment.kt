package com.michal.technicaltask.presentation.home.removeuser

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.michal.technicaltask.R
import com.michal.technicaltask.presentation.home.adapter.UserItem

class RemoveUserDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.remove_user_text)
            .setNegativeButton(R.string.cancel_text) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.ok_text) { dialog, _ ->
                val removeUserParcel = requireArguments().getParcelable<RemoveUserParcel>(REMOVE_USER_PARCEL_KEY)
                setFragmentResult(
                    REMOVE_USER_DIALOG_REQUEST_KEY, bundleOf(
                        REMOVE_USER_DIALOG_BUNDLE_KEY to removeUserParcel
                    )
                )
                dialog.dismiss()
            }
            .create()
    }

    companion object {

        fun create(userItem: UserItem): RemoveUserDialogFragment {
            return RemoveUserDialogFragment().apply {
                arguments = bundleOf(
                    REMOVE_USER_PARCEL_KEY to RemoveUserParcel.fromUserItem(userItem)
                )
            }
        }
    }
}

const val REMOVE_USER_DIALOG_REQUEST_KEY = "remove_user_dialog_request_key"
const val REMOVE_USER_DIALOG_BUNDLE_KEY = "remove_user_dialog_bundle_key"
private const val REMOVE_USER_PARCEL_KEY = "remove_user_parcel_key"