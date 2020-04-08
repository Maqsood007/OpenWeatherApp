package com.maqsood007.weatherforecast.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by Muhammad Maqsood on 07/04/2020.
 */
object DateTimeUtility {


    val DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss"
    val DATE_WITH_DAY_NAME = "EEE, dd YYYY"
    val TIME_AM_PM_FORMATTER = "hh:mm"
    val DATE_ONLY_FORMATTER = "yyyy-MM-dd"
    val DAY_NAME_ONLY_FORMATTER = "EEEE"

    // Return only date of given date and time object
    fun getDateOnly(inputDate: String, parserFormatte: String): String {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localDateTime = LocalDateTime.parse(inputDate, DateTimeFormatter.ofPattern(parserFormatte))
            return localDateTime.format(DateTimeFormatter.ofPattern(DATE_ONLY_FORMATTER))
        }else{
            val parser = SimpleDateFormat(parserFormatte, Locale.getDefault())
            val outputFormatter = SimpleDateFormat(DATE_ONLY_FORMATTER, Locale.getDefault())
            val date = parser.parse(inputDate)
            return outputFormatter.format(date)
        }
    }

    fun getTodayFormattedDate(): String {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localDateTime = LocalDateTime.now()
            return localDateTime.format(DateTimeFormatter.ofPattern(DATE_WITH_DAY_NAME))
        }else{
            val today = Date()
            val outputFormatter = SimpleDateFormat(DATE_WITH_DAY_NAME, Locale.getDefault())
            return outputFormatter.format(today)
        }
    }

    fun getDayName(inputDate: String, parserFormatte: String) : String{

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localDateTime = LocalDateTime.parse(inputDate, DateTimeFormatter.ofPattern(parserFormatte))
            return localDateTime.format(DateTimeFormatter.ofPattern(DAY_NAME_ONLY_FORMATTER))
        }else{
            val parser = SimpleDateFormat(parserFormatte, Locale.getDefault())
            val outputFormatter = SimpleDateFormat(DAY_NAME_ONLY_FORMATTER, Locale.getDefault())
            val date = parser.parse(inputDate)
            return outputFormatter.format(date)
        }
    }


    fun getTime(inputDate: String, parserFormatte: String) : String{

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localDateTime = LocalDateTime.parse(inputDate, DateTimeFormatter.ofPattern(parserFormatte))
            return localDateTime.format(DateTimeFormatter.ofPattern(TIME_AM_PM_FORMATTER))
        }else{
            val parser = SimpleDateFormat(parserFormatte, Locale.getDefault())
            val outputFormatter = SimpleDateFormat(TIME_AM_PM_FORMATTER, Locale.getDefault())
            val date = parser.parse(inputDate)
            return outputFormatter.format(date)
        }
    }

}