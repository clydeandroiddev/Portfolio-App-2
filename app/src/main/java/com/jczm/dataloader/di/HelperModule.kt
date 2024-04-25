package com.jczm.dataloader.di

import android.content.Context
import com.jczm.dataloader.util.helper.LocaleUtilHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object HelperModule {

    @Provides
    @Singleton
    fun provideLocaleUtilHelper(@ApplicationContext context : Context) = LocaleUtilHelper(context)

}