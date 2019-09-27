package ru.goodibunakov.prodvtest.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val SERVER_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val UI_DATE_FORMAT = "dd MMM"
    private const val DATE_FORMAT = "yyyy-MM-dd"

    fun convertDateForUI(dateIn: String): String {
        val inputFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.getDefault())
        val outputFormat = SimpleDateFormat(UI_DATE_FORMAT, Locale.getDefault())
        var date: Date? = null
        try {
            date = inputFormat.parse(dateIn)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    }

    fun convertDate(dateIn: String): String {
        val inputFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.getDefault())
        val outputFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        var date: Date? = null
        try {
            date = inputFormat.parse(dateIn)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    }
}