package com.interswitchng.smartpossdksample.utils

import android.content.Context
import android.content.SharedPreferences

object PrefUtils {
    private const val PREF_NAME = "SmartPosSdkSamplePrefs"
    private const val KEY_FEEDBACK_DIALOG = "feedback_dialog_enabled"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun toggleFeedbackDialog(context: Context, isOn: Boolean) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(KEY_FEEDBACK_DIALOG, isOn)
        editor.apply()
    }

    fun isFeedbackDialogTurnedOn(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_FEEDBACK_DIALOG, false)
    }
}