package com.github.psm.movie.review.di

import com.github.psm.movie.review.repository.TMDBRepository
import com.github.psm.movie.review.ui.popular.PopularDataSource
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