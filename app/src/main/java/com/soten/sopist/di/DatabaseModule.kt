package com.soten.sopist.di

import android.content.Context
import android.content.SharedPreferences
import com.soten.sopist.data.db.preference.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val SHARED_PREFERENCE_NAME = "SHARED PREFERENCE"

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE
        )

    @Provides
    @Singleton
    fun providePreferenceManager(sharedPreferences: SharedPreferences) =
        SharedPreferenceManager(sharedPreferences)

}