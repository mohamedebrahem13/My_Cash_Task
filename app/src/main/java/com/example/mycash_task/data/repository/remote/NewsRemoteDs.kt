package  com.example.mycash_task.data.repository.remote

import com.example.mycash_task.data.models.NewsResponseDto
import com.example.mycash_task.domain.repository.remote.INewsRemoteDs


class NewsRemoteDs (private val newsApiService: NewsApiService): INewsRemoteDs {
    override suspend fun getTopHeadlines(category: String): NewsResponseDto {
        return newsApiService.getTopHeadlines(category)
    }

}