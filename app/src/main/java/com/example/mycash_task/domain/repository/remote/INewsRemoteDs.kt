package com.example.mycash_task.domain.repository.remote

import com.example.mycash_task.data.models.NewsResponseDto

interface INewsRemoteDs {
    suspend fun getTopHeadlines(category: String): NewsResponseDto
}