package com.github.psm.movie.review.di

import android.content.Context
import android.content.SharedPreferences
import com.github.psm.movie.review.db.BoxStore
import com.github.psm.movie.review.db.BoxStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppPreference(
        @ApplicationContext
        context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("pref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideBoxStore(): BoxStore {
        return BoxStoreImpl()
    }

}