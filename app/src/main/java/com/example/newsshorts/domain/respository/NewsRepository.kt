package com.example.newsshorts.domain.respository

import com.example.newsshorts.data.remote.dto.NewsResponseDto
import retrofit2.Response

interface NewsRepository {
    suspend fun getNews(): NewsResponseDto
}