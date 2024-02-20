package com.fermion.android.base.helper

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.fermion.android.base.R

/**
 * Created by Bhavesh Auodichya.
 *
 * BaseProgressBar with normal circular progress bar
 *
 *@since 1.0.0
 */
class BaseProgressBar {

    fun showLoading(context: Context): Dialog {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.base_progress_bar)

        // Start the animation
        val gifImageView = dialog.findViewById<ProgressBar>(R.id.base_progress_bar_loader)
        gifImageView.visibility = View.VISIBLE

        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawableResource(android.R.color.transparent)
        }

        // Show the dialog
        dialog.show()
        return dialog
    }
}