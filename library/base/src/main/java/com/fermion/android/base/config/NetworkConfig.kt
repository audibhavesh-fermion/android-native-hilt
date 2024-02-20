package com.fermion.android.base.config

/**
 * Created by Bhavesh Auodichya.
 * Simple network config
 *
 * Add Common Config or constants which will be used by all module
 *
 * @since 1.0.0
 */


class NetworkConfig {
    companion object {
        const val apiReadTimeout: Long = 1
        const val apiConnectionTimeout: Long = 1
        const val apiWriteTimeout: Long = 1
        const val apiCallTimeout: Long = 1
        var accessToken = ""
    }
}