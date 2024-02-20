package com.fermion.android.base.helper


/**
 * Created by Bhavesh Auodichya.
 *
 * Interface for implementing custom progress bar provider
 *
 * **Note** extend this interface in your view class to implement custom progress bar.
 *
 *
 *@since 1.0.0
 */
interface BaseProgressBarProvider {
    fun showProgressBar()
    fun hideProgressBar()
}