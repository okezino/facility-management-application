package com.decagon.facilitymanagementapp_group_two.di

import android.content.SharedPreferences
import com.decagon.facilitymanagementapp_group_two.model.data.database.CentralDatabase
import com.decagon.facilitymanagementapp_group_two.model.repository.feeds.FeedsRepository
import com.decagon.facilitymanagementapp_group_two.model.repository.feeds.FeedsRepositoryImpl
import com.decagon.facilitymanagementapp_group_two.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * FeedsRepositoryModule that provides the FeedsRepository
 */
@Module
@InstallIn(SingletonComponent::class)
object FeedsRepositoryModule {
    @Singleton
    @Provides
    fun provideFeedsRepository(apiService: ApiService, cd: CentralDatabase, sharedPref: SharedPreferences)
    : FeedsRepository {
       return FeedsRepositoryImpl(apiService, cd, sharedPref)
    }
}