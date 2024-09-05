package com.example.mycash_task.domain.intractor

import com.example.mycash_task.common.Resource
import com.example.mycash_task.domain.models.Article
import com.example.mycash_task.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTopHeadlinesUseCase (
    private val newsRepository: INewsRepository
) {

    operator fun invoke(category: String): Flow<Resource<List<Article>>> = flow {
        emit(Resource.loading(loading = true))
        try {
            val response = newsRepository.getTopHeadlines(category)
            emit(Resource.success(response))
        } catch (e: Exception) {
            emit(Resource.error(e))
        }
    }
}