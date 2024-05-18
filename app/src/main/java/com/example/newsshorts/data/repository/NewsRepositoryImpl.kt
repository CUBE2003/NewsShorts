package com.example.newsshorts.data.repository

import com.example.newsshorts.data.remote.NewsApi
import com.example.newsshorts.data.remote.dto.NewsResponseDto
import com.example.newsshorts.domain.respository.NewsRepository
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository {
    override suspend fun getNews(): NewsResponseDto {
        return newsApi.getNews()
    }

}