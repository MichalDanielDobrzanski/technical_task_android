package com.michal.technicaltask.presentation.utils

import android.content.res.Resources
import android.graphics.Rect
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

/**
 * Call this method (in [DialogFragment.onActivityCreated] or later) to
 * set the width of the dialog to a percentage of the current screen width.
 */
fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val displayMetrics = Resources.getSystem().displayMetrics
    val rect = displayMetrics.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * percent
    dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
}
