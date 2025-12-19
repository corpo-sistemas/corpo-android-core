package com.corposistemas.android_core.helpers.converters

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateTransformer {

    const val ISO_8601_SHORT_DATE = "yyyy-MM-dd"
    const val ISO_8601_SHORT_DATE_TIME = "yyyy-MM-dd - HH:mm:ss"
    const val ISO_8601_LONG_DATE = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"

    fun dateToFormat(
        date: Date = Date(),
        format: String = ISO_8601_SHORT_DATE,
        timeZone: TimeZone?
    ): String {
        val sdf = SimpleDateFormat(format, Locale.US)
        if (timeZone != null) {
            sdf.timeZone = timeZone
        }
        return sdf.format(date)
    }
}