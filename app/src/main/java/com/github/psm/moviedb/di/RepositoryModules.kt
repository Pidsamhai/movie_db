package com.github.psm.moviedb.di

import com.github.psm.moviedb.repository.*
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

    @Binds
    @Singleton
    abstract fun bindBookmarkRepository(repository: BookmarkRepositoryImpl): BookmarkRepository
}