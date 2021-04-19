package com.decagon.facilitymanagementapp_group_two.di

import android.content.SharedPreferences
import com.decagon.facilitymanagementapp_group_two.model.data.database.RequestDao
import com.decagon.facilitymanagementapp_group_two.model.data.database.CentralDatabase
import com.decagon.facilitymanagementapp_group_two.model.repository.auth.AuthRepository
import com.decagon.facilitymanagementapp_group_two.model.repository.auth.AuthRepositoryImpl
import com.decagon.facilitymanagementapp_group_two.model.repository.facility.FacilityRepository
import com.decagon.facilitymanagementapp_group_two.model.repository.facility.FacilityRepositoryImpl
import com.decagon.facilitymanagementapp_group_two.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * AuthRepositoryModule that provides the authRepository
 */
@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepository(apiService: ApiService, sharedPreferences: SharedPreferences,centralDatabase: CentralDatabase): AuthRepository {
        return AuthRepositoryImpl(apiService, sharedPreferences,centralDatabase)
    }

    /**
     * Also provides the facility repository :TODO rename to RepositoryModule
     */
    @Singleton
    @Provides
    fun provideFacilityRepository(apiService: ApiService, centralDatabase: CentralDatabase): FacilityRepository {
        return FacilityRepositoryImpl(apiService,centralDatabase)
    }


}
