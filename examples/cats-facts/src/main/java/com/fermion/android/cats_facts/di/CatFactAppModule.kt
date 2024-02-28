package com.fermion.android.cats_facts.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class CatFactAppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): CatApplication {
        return app as CatApplication
    }

    @Singleton
    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }
}