package com.decagon.facilitymanagementapp_group_two.di

import android.content.Context
import androidx.room.Room
import com.decagon.facilitymanagementapp_group_two.model.data.database.CentralDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    // Provides an in-memory database because the information stored here disappears when the
    // process is killed.
    @Provides
    @Named("test-db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, CentralDatabase::class.java)
            .allowMainThreadQueries().build()
}
