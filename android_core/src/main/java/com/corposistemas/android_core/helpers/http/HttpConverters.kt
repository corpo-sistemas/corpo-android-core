package com.corposistemas.android_core.helpers.http

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object HttpConverters {

    fun String.toXMLBody(): RequestBody {
        val mediaType = "text/xml; charset=utf-8".toMediaType()
        val body = this.toRequestBody(mediaType)
        return body
    }

    fun String.toJsonBody(): RequestBody {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = this.toRequestBody(mediaType)
        return body
    }
}