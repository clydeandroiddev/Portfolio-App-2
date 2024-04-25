package com.jczm.dataloader.di

import com.jczm.dataloader.data.database.CarDatabase
import com.jczm.dataloader.data.repository.CarRepository
import com.jczm.dataloader.data.repository.CarRepositoryImpl
import com.jczm.dataloader.util.helper.LocaleUtilHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCarRepository(
        carDatabase : CarDatabase,
        localeUtilHelper: LocaleUtilHelper
    ) : CarRepository = CarRepositoryImpl(carDatabase, localeUtilHelper)
}