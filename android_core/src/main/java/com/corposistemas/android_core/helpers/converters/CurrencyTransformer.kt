package com.corposistemas.android_core.helpers.converters

import com.ibm.icu.text.RuleBasedNumberFormat
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Locale

object CurrencyTransformer {

    /**
     * Convierte un monto numérico a su representación en palabras en español (Guatemala).
     *
     * @param amount Monto numérico a convertir.
     * @return Representación en palabras del monto.
     */
    fun amountToCurrencyWords(amount: Double): String {
        val amount = BigDecimal(amount.toString())
        val money = amount.toInt()
        val cents = amount.subtract(BigDecimal(money))
            .multiply(BigDecimal(100))
            .setScale(0, RoundingMode.HALF_EVEN)
            .toInt()

        val locale = Locale.Builder().setLanguage("es").setRegion("GT").build()
        val format = RuleBasedNumberFormat(locale, RuleBasedNumberFormat.SPELLOUT)

        val result = format.format(money)
        val centsText = format.format(cents)

        return "$result quetzales con $centsText centavos"
    }
}