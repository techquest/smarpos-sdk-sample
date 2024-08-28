package com.interswitchng.smartpossdksample.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {

    fun formatToUserFriendlyDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        val outputFormat = SimpleDateFormat("MMMM dd, yyyy, hh:mm a", Locale.getDefault())

        return try {
            val date: Date = inputFormat.parse(dateString)!!
            outputFormat.format(date)
        } catch (e: Exception) {
            dateString
        }
    }
}