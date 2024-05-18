package com.example.newsshorts.domain.use_cases

import com.example.newsshorts.common.Resource
import com.example.newsshorts.data.remote.dto.toNewsResponse
import com.example.newsshorts.domain.model.NewsResponse
import com.example.newsshorts.domain.respository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(): Flow<Resource<NewsResponse>> = flow {
        try {
            emit(Resource.Loading())
            val newsResponse = newsRepository.getNews()
            if (newsResponse.status == "ok" && newsResponse.articles.isNotEmpty()) {
                emit(Resource.Success(newsResponse.toNewsResponse()))
            } else {
                emit(Resource.Error(ERROR_MESSAGE))
            }

        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Network error: ${e.localizedMessage}",
                    data = null // You could potentially return cached data if available
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server. Check your internet connection.",
                    data = null
                )
            )
        }
    }

    companion object {
        const val ERROR_MESSAGE = "Invalid or empty response from API"
    }

}