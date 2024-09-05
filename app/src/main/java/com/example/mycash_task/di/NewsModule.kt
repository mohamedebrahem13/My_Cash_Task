package com.example.mycash_task.di

import com.example.mycash_task.data.repository.NewsRepository
import com.example.mycash_task.data.repository.remote.NewsApiService
import com.example.mycash_task.data.repository.remote.NewsRemoteDs
import com.example.mycash_task.domain.intractor.GetTopHeadlinesUseCase
import com.example.mycash_task.domain.repository.INewsRepository
import com.example.mycash_task.domain.repository.remote.INewsRemoteDs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object NewsModule {

    @Provides
    fun provideNewsRemoteDs(newsApiService: NewsApiService): INewsRemoteDs {
        return NewsRemoteDs(newsApiService)
    }


    @Provides
    fun provideNewsRepository(
        newsRemoteDs: INewsRemoteDs
    ): INewsRepository {
        return NewsRepository(newsRemoteDs)
    }
    @Provides
    fun provideGetTopHeadlinesUseCase(newsRepository: INewsRepository): GetTopHeadlinesUseCase {
        return GetTopHeadlinesUseCase(newsRepository)
    }


}