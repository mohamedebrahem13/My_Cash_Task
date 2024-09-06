package com.example.mycash_task.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycash_task.common.Resource
import com.example.mycash_task.domain.intractor.GetTopHeadlinesUseCase
import com.example.mycash_task.domain.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel() {

    private val _articlesState = MutableStateFlow<Resource<List<Article>>>(Resource.loading(loading = false))
    val articlesState: StateFlow<Resource<List<Article>>> = _articlesState

    fun getTopHeadlines(category: String) {
        viewModelScope.launch {
            getTopHeadlinesUseCase(category).collect { result ->
                _articlesState.value = result
            }
        }
    }
}