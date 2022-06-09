package com.michal.technicaltask.utils

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun matchesTextOnRecyclerView(@IdRes textViewId: Int, expectedText: String): Matcher<View> {
    return object : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description) {
            description.appendText("with class name: ")
        }

        public override fun matchesSafely(view: View): Boolean {
            if (view !is RecyclerView) {
                return false
            } else {
                val adapter = view.adapter!!
                for (i in 0 until adapter.itemCount) {
                    val adapterView = view.layoutManager!!.findViewByPosition(i)!!
                    if (adapterView.findViewById<TextView>(textViewId).text == expectedText) {
                        return true
                    }
                }
                return false
            }
        }
    }
}