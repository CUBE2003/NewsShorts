package com.example.newsshorts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsshorts.common.Resource
import com.example.newsshorts.domain.model.NewsResponse
import com.example.newsshorts.domain.use_cases.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsAppViewModel @Inject constructor(
    private val useCase: GetNewsUseCase
) : ViewModel() {


    private val _newsState = MutableStateFlow<Resource<NewsResponse>>(Resource.Loading())
    val newsState: StateFlow<Resource<NewsResponse>> = _newsState.asStateFlow()

    init {
        getNews()
    }



     fun getNews() {
        viewModelScope.launch {
            useCase().collectLatest { news ->
                _newsState.value = news
            }
        }
    }


}

