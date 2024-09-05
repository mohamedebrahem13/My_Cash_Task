package  com.example.mycash_task.data.mapper

import com.example.mycash_task.common.Mapper
import com.example.mycash_task.data.models.ArticleDto
import com.example.mycash_task.domain.models.Article
import com.example.mycash_task.domain.models.Source


object ArticleMapper: Mapper<ArticleDto, Article, Unit>() {

    override fun dtoToDomain(model: ArticleDto): Article {
        return Article(
            source = Source(
                id = model.source?.id.orEmpty(),
                name = model.source?.name.orEmpty()
            ),
            author = model.author.orEmpty(),
            title = model.title.orEmpty(),
            description = model.description.orEmpty(),
            url = model.url.orEmpty(),
            imageUrl = model.urlToImage.orEmpty(),
            publishedAt = model.publishedAt.orEmpty(),
            content = model.content.orEmpty()
        )
    }


    fun dtoListToDomain(list: List<ArticleDto>?): List<Article> {
        return list?.map { dtoToDomain(it) } ?: emptyList()
    }
}