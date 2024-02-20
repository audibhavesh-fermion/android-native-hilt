package com.fermion.android.base.di

import android.app.Application
import com.fermion.android.base.BuildConfig
import com.fermion.android.base.helper.CrashReportingTree
import timber.log.Timber

abstract class LauncherApplication : Application() {
    init {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        else Timber.plant(CrashReportingTree())
    }
}