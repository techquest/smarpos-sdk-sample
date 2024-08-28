package com.interswitchng.smartpossdksample.utils

import android.content.Context
import android.content.SharedPreferences
import com.interswitchng.smartpossdksample.CompanyOption

object PrefUtils {
    private const val PREF_NAME = "SmartPosSdkSamplePrefs"
    private const val KEY_FEEDBACK_DIALOG = "KEY_FEEDBACK_DIALOG"
    private const val KEY_SAVED_COMPANY_LOGO_OPTION = "KEY_SAVED_COMPANY_LOGO_OPTION"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun toggleFeedbackDialog(context: Context, isDisabled: Boolean) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(KEY_FEEDBACK_DIALOG, isDisabled)
        editor.apply()
    }

    fun isFeedbackDialogDisabled(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_FEEDBACK_DIALOG, false)
    }

    fun saveCompanyLogoOption(companyOption: CompanyOption, context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.putInt(KEY_SAVED_COMPANY_LOGO_OPTION, companyOption.ordinal)
        editor.apply()
    }

    fun getSavedCompanyLogoOption(context: Context): CompanyOption {
        return when(getSharedPreferences(context).getInt(KEY_SAVED_COMPANY_LOGO_OPTION, -1)) {
            CompanyOption.INTERSWITCH.ordinal -> CompanyOption.INTERSWITCH
            CompanyOption.GOOGLE.ordinal -> CompanyOption.GOOGLE
            CompanyOption.AMAZON.ordinal -> CompanyOption.AMAZON
            CompanyOption.APPLE.ordinal -> CompanyOption.APPLE
            else -> CompanyOption.NONE
        }
    }
}