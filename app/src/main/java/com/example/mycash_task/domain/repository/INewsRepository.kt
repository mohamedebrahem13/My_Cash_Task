package com.example.mycash_task.domain.repository

import com.example.mycash_task.domain.models.Article


interface INewsRepository{
    suspend fun getTopHeadlines(category: String):List<Article>
}