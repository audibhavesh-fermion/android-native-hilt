@file:Suppress("unused")

package com.fermion.android.base.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.fermion.android.base.constants.Status
import kotlinx.coroutines.Dispatchers

/**
 * Created by Bhavesh Auodichya.
 *
 * **Note** modify this function to implement SST strategy
 *
 *@since 1.0.0
 */
fun <T, A> getResponseWithNetwork(
    networkCall: suspend () -> NetworkResult<A>, saveCallResult: suspend (A) -> Unit
): LiveData<NetworkResult<T>> = liveData(Dispatchers.IO) {
    emit(NetworkResult.loading<T>())
    val responseStatus = networkCall.invoke()
    if (responseStatus.status == Status.SUCCESS) {
        saveCallResult(responseStatus.data!!)
    } else if (responseStatus.status == Status.ERROR) {
        emit(NetworkResult.error<T>(responseStatus.message!!))
    }
}

fun <T, A> getResponseWithLocalSource(
    networkCall: suspend () -> NetworkResult<A>,
    localSourceResultCall: suspend () -> NetworkResult<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<NetworkResult<T>> = liveData(Dispatchers.IO) {
    emit(NetworkResult.loading<T>())
    val responseStatus = networkCall.invoke()
    if (responseStatus.status == Status.SUCCESS) {
        saveCallResult(responseStatus.data!!)
    } else if (responseStatus.status == Status.ERROR) {
        val localSourceResultResponse = localSourceResultCall.invoke()
        if (localSourceResultResponse.status == Status.SUCCESS) {
            saveCallResult(localSourceResultResponse.data!!)
        } else {
            emit(NetworkResult.error<T>(responseStatus.message!!))
        }
    }
}
