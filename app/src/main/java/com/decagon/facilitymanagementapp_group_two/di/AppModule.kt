package com.decagon.facilitymanagementapp_group_two.di

import android.content.Context
import androidx.room.Room
import com.decagon.facilitymanagementapp_group_two.data.database.CentralDatabase
import com.decagon.facilitymanagementapp_group_two.network.ApiService
import com.decagon.facilitymanagementapp_group_two.utils.BASE_URL
import com.decagon.facilitymanagementapp_group_two.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Serves as a manual for creating dependencies used within the application. This module prepares
 * and provides dependencies to where ever it is required
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Creates a single instance of the database and its Dao's and provides the dependency
     * when needed
     */
    @Singleton
    @Provides
    fun provideFacilityManagementDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context,
        CentralDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideFacilityManagementCommentDao(cd : CentralDatabase) = cd.commentDao

    @Singleton
    @Provides
    fun provideFacilityManagementRequestDao(cd : CentralDatabase) = cd.requestDao

    @Singleton
    @Provides
    fun provideFacilityManagementUserDao(cd : CentralDatabase) = cd.userDao

    // Provides dependency for the retrofit
    @Singleton
    @Provides
    fun provideApiServiceEndPoint() : ApiService{
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        // Creates an implementation of the ApiService
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(logging).build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
