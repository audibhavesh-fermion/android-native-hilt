@file:Suppress("unused")

package com.fermion.android.base.network

import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.io.IOException

/**
 * Created by Bhavesh Auodichya.
 *
 *Extension method to convert response body to string
 *@since 1.0.0
 */
fun Response.responseBodyToString(): String? {
    return try {
        if (this.body != null) {
            val reader = this.body?.charStream()
            val bufferSize = 1024
            val buffer = CharArray(bufferSize)
            val out = StringBuilder()
            var numRead: Int?
            while ((reader?.read(buffer, 0, buffer.size).also {
                    numRead = it
                } ?: 0) > 0) {
                out.append(buffer, 0, numRead)
            }
            out.toString()
        } else {
            ""
        }
    } catch (e: java.lang.Exception) {
        ""
    }
}


/**
 * Created by Bhavesh Auodichya.
 *
 *Extension method to convert request body to string
 *
 *@since 1.0.0
 *@throws IOException
 */
@Throws(IOException::class)
fun RequestBody.requestBodyToString(): String? {
    return try {
        val copy: RequestBody = this
        val buffer = Buffer()
        copy.writeTo(buffer)
        val requestString: String = buffer.readUtf8()
        buffer.close()
        requestString
    } catch (e: IOException) {
        ""
    }
}