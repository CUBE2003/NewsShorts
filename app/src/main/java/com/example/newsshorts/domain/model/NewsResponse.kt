package com.example.newsshorts.domain.model

data class NewsResponse(
    val articles: List<ArticleData>,
    val status: String,
    val totalResults: Int
)
data class ArticleData(
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: String? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)
data class Source(
    val id: String,
    val name: String
)
