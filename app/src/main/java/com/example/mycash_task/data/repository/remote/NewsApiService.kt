package  com.example.mycash_task.data.repository.remote

import com.example.mycash_task.data.models.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApiService {

    @GET("top-headlines/category/{category}/in.json")
    suspend fun getTopHeadlines(
        @Path("category") category: String
    ): NewsResponseDto

}