package com.fermion.android.base.network

import retrofit2.HttpException
import retrofit2.Response

/**
 * Created by Bhavesh Auodichya.
 *
 * **Info** : Base Data Source provide result wrapper for network  or other common SST
 *
 *@since 1.0.0
 */
abstract class BaseDataSource {
    protected suspend fun <T> getNetworkResult(call: suspend () -> Response<T>): NetworkResult<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                NetworkResult.success(body)
            } else {
                NetworkResult.error("${response.code()} ${response.message()}")
            }
        } catch (httpException: HttpException) {
            httpException.getExceptionResponse()
        } catch (e: Exception) {
            val message = "Exception ${e.message ?: ""}\n"
            NetworkResult.errorWithException(message)
        }

    }


}