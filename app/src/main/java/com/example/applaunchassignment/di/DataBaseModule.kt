package com.example.applaunchassignment.di

import android.content.Context
import androidx.room.Room
import com.example.applaunchassignment.data.db.UserDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideEmployeeDB(@ApplicationContext context: Context): UserDB {
        return Room.databaseBuilder(context, UserDB::class.java, "UserDB").build()
    }
}