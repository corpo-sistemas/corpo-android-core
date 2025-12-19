package com.corposistemas.android_core.helpers.printer.formats

class PrintFormatBuilder(private val maxCharactersPerLine: Int) {

    fun fillRemaining(text: String, filler: String): String {
        require(filler.isNotEmpty()) { "Filler must not be empty" }
        if (text.length >= maxCharactersPerLine) return text.take(maxCharactersPerLine)

        val remaining = maxCharactersPerLine - text.length
        val repeated = filler.repeat(remaining / filler.length + 1).take(remaining)
        return text + repeated
    }

    fun centerText(text: String): String {
        if (text.length >= maxCharactersPerLine) return text.take(maxCharactersPerLine) // truncate if too long

        val padding = maxCharactersPerLine - text.length
        val padStart = padding / 2
        val padEnd = padding - padStart

        return " ".repeat(padStart) + text + " ".repeat(padEnd)
    }

    fun alignTextRight(text: String): String {
        if (text.length >= maxCharactersPerLine) return text.take(maxCharactersPerLine) // truncate if too long
        val padding = maxCharactersPerLine - text.length
        return " ".repeat(padding) + text
    }

    fun fillChars(char: String = "-"): String {
        return char.repeat(maxCharactersPerLine)
    }
}