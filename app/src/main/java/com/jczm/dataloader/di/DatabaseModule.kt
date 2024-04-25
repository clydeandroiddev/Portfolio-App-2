package com.jczm.dataloader.di

import android.content.Context
import com.jczm.dataloader.data.database.CarDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCarDatabase(@ApplicationContext context : Context) = CarDatabase.initWith(context)

}