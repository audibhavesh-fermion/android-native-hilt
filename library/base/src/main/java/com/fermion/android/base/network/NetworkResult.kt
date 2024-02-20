package com.fermion.android.base.network

import com.fermion.android.base.constants.Status
import com.fermion.android.base.helper.JsonHelper
import com.fermion.android.base.models.network.HttpErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import timber.log.Timber

/**
 * Created by Bhavesh Auodichya.
 *
 * **Note** Network Result class provides success, error, and loading data events
 *
 * @property isSuccess if api call is successful or not (if http status code is 200)
 *@since 1.0.0
 */
class NetworkResult<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): NetworkResult<T> {
            return NetworkResult(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): NetworkResult<T> {
            return NetworkResult(Status.ERROR, data, message)

        }

        fun <T> errorWithException(message: String): NetworkResult<T> {
            Timber.e(message)
            return NetworkResult(
                Status.ERROR, null,
                "Network call has failed for a following reason: $message"
            )
        }

        fun <T> httpError(message: String, data: T? = null): NetworkResult<T> {
            return NetworkResult(Status.HTTP_ERROR, data, message)
        }


        fun <T> loading(data: T? = null): NetworkResult<T> {
            return NetworkResult(Status.LOADING, data, null)
        }
    }

    val isSuccess: Boolean = status == Status.SUCCESS

}

fun <T> HttpException.getExceptionResponse(): NetworkResult<T> {
    val code = this.code()
    val message = if (this.message().isNotEmpty()) "\n" + this.message else ""

    try {
        val responseStream = response()?.errorBody()!!.charStream().readText()
        return if (JsonHelper().isJsonValid(responseStream)) {

            /*
            * Here HttpErrorResponse class is consider standard http error response sent from server
            * If your backend api throws different Http exception response
            * then please change it with your respective response model
            *
            * */
            val errorResponse = Gson().fromJson(
                responseStream, HttpErrorResponse::class.java
            )

            NetworkResult.httpError(
                message = "Server Code $code\n${errorResponse.message}"
            )
        } else {
            NetworkResult.httpError(
                message = "Server Code $code ${message}\n Please try again"
            )
        }
    } catch (e: Exception) {
        return NetworkResult.httpError(
            message = "Server Code $code ${message}\n Please try again"
        )
    }
}