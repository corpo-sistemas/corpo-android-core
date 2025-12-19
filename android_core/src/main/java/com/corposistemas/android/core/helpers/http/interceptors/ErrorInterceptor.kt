package com.corposistemas.android.core.helpers.http.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            val result = response.body.string()
            response.close()
            throw IOException("HTTP ${response.code}/${response.message}: $result")
        }
        return response
    }
}