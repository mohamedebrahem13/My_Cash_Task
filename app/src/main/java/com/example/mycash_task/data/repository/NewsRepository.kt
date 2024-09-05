package com.example.mycash_task.data.repository

import com.example.mycash_task.data.mapper.ArticleMapper
import com.example.mycash_task.domain.models.Article
import com.example.mycash_task.domain.repository.INewsRepository
import com.example.mycash_task.domain.repository.remote.INewsRemoteDs

class NewsRepository (private val newsRemoteDs: INewsRemoteDs): INewsRepository {
    override suspend fun getTopHeadlines(category: String): List<Article> {
        val responseDto = newsRemoteDs.getTopHeadlines(category)
        val articles = ArticleMapper.dtoListToDomain(responseDto.articles)
        return articles
    }


}