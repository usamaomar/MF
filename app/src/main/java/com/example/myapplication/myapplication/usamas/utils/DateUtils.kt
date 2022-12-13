package com.example.myapplication.myapplication.usamas.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun dateIoFormat(dateString: String?): String? {
        try {
            val zonedFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CANADA)
            val yourDesiredFormat = SimpleDateFormat("dd/MM/yyyy", Locale.CANADA)
            var dateStringToReturn = ""
            val date = zonedFormat.parse(dateString)
            dateStringToReturn = yourDesiredFormat.format(date)
            return dateStringToReturn
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    fun dateOrgFormat(dateString: String?): String? {
        try {
            val zonedFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CANADA)
            val yourDesiredFormat = SimpleDateFormat("dd/MM/yyyy", Locale.CANADA)
            zonedFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            var dateStringToReturn = ""
            val date = zonedFormat.parse(dateString)
            dateStringToReturn = yourDesiredFormat.format(date)
            return dateStringToReturn
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }
}