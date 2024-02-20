package com.fermion.android.base.models.network

data class HttpErrorResponse(
    var code: String,
    var message: String,
    var errorSource: String,
    var errorDescription: String,
    var extra: String
)
