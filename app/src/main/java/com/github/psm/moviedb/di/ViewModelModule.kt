package com.github.psm.moviedb.di

import com.github.psm.moviedb.repository.TMDBRepository
import com.github.psm.moviedb.ui.popular.PopularDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    @Singleton
    fun providePopularDataSource(repository: TMDBRepository) = PopularDataSource(repository)
}