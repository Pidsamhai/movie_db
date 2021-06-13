package com.github.psm.moviedb.di

import android.content.Context
import android.content.SharedPreferences
import com.github.psm.moviedb.db.BoxStore
import com.github.psm.moviedb.db.BoxStoreImpl
import com.github.psm.moviedb.repository.SettingRepository
import com.github.psm.moviedb.repository.TMDBApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
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

    @Provides
    @Singleton
    fun provideTMDBApiServices(
        settingRepository: SettingRepository
    ): HttpClient {
        return TMDBApiServices.getInstance(settingRepository)
    }

}