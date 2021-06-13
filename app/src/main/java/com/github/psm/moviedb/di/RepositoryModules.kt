package com.github.psm.moviedb.di

import com.github.psm.moviedb.repository.SettingRepository
import com.github.psm.moviedb.repository.SettingRepositoryImpl
import com.github.psm.moviedb.repository.TMDBRepository
import com.github.psm.moviedb.repository.TMDBRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModules {
    @Binds
    @Singleton
    abstract fun bindTMDBRepository(repository: TMDBRepositoryImpl): TMDBRepository

    @Binds
    @Singleton
    abstract fun bindSettingRepository(repository: SettingRepositoryImpl): SettingRepository
}