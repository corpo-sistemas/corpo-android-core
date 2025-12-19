package com.corposistemas.android_core.helpers.converters

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Locale

object NumTransformer {

    // DOUBLES
    fun Double.toDecimals(format: String = "%05.2f"): String {
        return String.format(Locale.US, format, this)
    }

    fun Double.roundTo(decimals: Int = 6): Double {
        return BigDecimal(this).setScale(decimals, RoundingMode.HALF_EVEN).toDouble()
    }

    // INTEGERS
    fun Int.toDecimals(format: String = "%05.2f"): String {
        return String.format(Locale.US, format, this.toDouble())
    }

    fun Int.toBoolean(): Boolean = when (this) {
        0 -> false
        1 -> true
        else -> throw IllegalArgumentException("Int value must be 0 or 1 to convert to Boolean")
    }

    fun Boolean.toInt(): Int = if (this) 1 else 0
}