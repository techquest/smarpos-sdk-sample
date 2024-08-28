package com.interswitchng.smartpossdksample.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.interswitchng.smartpossdksample.R

object ViewUtils {

    fun Activity.showSnackBar(
        message: String,
        duration: Int = Snackbar.LENGTH_SHORT
    ) {
        if (!PrefUtils.isFeedbackDialogDisabled(this)) {
            val rootView = this.findViewById<View>(android.R.id.content)
            Snackbar.make(rootView, message, duration).show()
        }
    }
}

/**
 * Utility to set the title of the toolBar with a custom font
 */
fun setToolBarTitle(toolBar: MaterialToolbar, title: String, context: Context) {

    val customTitle = TextView(context).apply {
        text = title
        textSize = 16f
        typeface = ResourcesCompat.getFont(context, R.font.cera_pro_medium)
        setTextColor(ContextCompat.getColor(context, R.color.black))
    }

    toolBar.addView(customTitle)

    val layoutParams = customTitle.layoutParams as Toolbar.LayoutParams
    layoutParams.gravity = Gravity.CENTER
    customTitle.layoutParams = layoutParams
}

fun vectorDrawableToBitmap(context: Context, drawableResId: Int): Bitmap? {

    val vectorDrawable = AppCompatResources.getDrawable(context, drawableResId)

    return vectorDrawable?.let {
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)

        bitmap
    }
}

/**
 * This is simply an interface for getting the root view from the binding
 * (Activity or Fragment). Nothing more
 */
interface ViewBindingProvider {
    fun getRootView(): View
}