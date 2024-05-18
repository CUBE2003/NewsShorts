package com.example.newsshorts.data.remote.dto

import com.example.newsshorts.domain.model.ArticleData
import com.example.newsshorts.domain.model.NewsResponse



data class NewsResponseDto(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)

data class Article(
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,

    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)

data class Source(
    val id: String,
    val name: String
)


fun NewsResponseDto.toNewsResponse(): NewsResponse {
    return NewsResponse(
        articles = articles.map { it.toArticle() },
        status = status,
        totalResults = totalResults
    )
}

fun Article.toArticle(): ArticleData { // Note the full package reference here
    return ArticleData(
        author = author?: "",
        content = content ?: "",
        description = description?: "",
        publishedAt = publishedAt?: "",

        title = title?: "",
        url = url?: "",
        urlToImage = urlToImage?: ""
    )
}