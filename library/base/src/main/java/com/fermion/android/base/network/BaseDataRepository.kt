package com.fermion.android.base.network

import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Bhavesh Auodichya.
 *
 *BaseDataRepository
 *
 * **Note** Extend this class to your repository
 *
 * **Info** : Implement common  apis or SST Strategy here
 *
 * @property apiService use this for calling base apis
 *
 *@since 1.0.0
 */
@Singleton
open class BaseDataRepository @Inject constructor(private val apiService: BaseApiService) :
    BaseDataSource() {
    @Suppress("unused")
    suspend fun testDomain() = getNetworkResult { apiService.testDomain() }
}