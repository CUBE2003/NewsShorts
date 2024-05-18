package com.example.newsshorts.data.remote

import com.example.newsshorts.common.Constants
import com.example.newsshorts.data.remote.dto.NewsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET(Constants.ENDPOINT)
    suspend fun getNews(
        @Query("country") country: String = Constants.COUNTRY,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): NewsResponseDto
}


//https://newsapi.org/v2/top-headlines?country=in&apiKey=0a7d2bbc7fc1414a91b04d9acaa7d17b