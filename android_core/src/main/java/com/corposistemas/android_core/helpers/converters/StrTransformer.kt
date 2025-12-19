package com.corposistemas.android_core.helpers.converters

import okio.ByteString.Companion.decodeBase64
import okio.ByteString.Companion.encodeUtf8
import java.text.Normalizer

object StrTransformer {

    fun String.toBase64(): String {
        return this.encodeUtf8().base64()
    }

    fun String.fromBase64(): String {
        return this.decodeBase64()?.utf8()
            ?: throw IllegalArgumentException("Invalid Base64 string")
    }

    fun String.normalized(): String =
        Normalizer.normalize(this, Normalizer.Form.NFD)
            .replace("\\p{M}".toRegex(), "")
}