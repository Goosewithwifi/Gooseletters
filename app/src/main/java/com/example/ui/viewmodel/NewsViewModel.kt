package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.NewsArticle
import com.example.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface NewsUiState {
    object Loading : NewsUiState
    data class Success(val articles: List<NewsArticle>) : NewsUiState
    data class Error(val message: String) : NewsUiState
}

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsRepository()
    private val prefs = application.getSharedPreferences("news_prefs", Context.MODE_PRIVATE)

    private val _apiKey = MutableStateFlow(prefs.getString("api_key", "c4c33472-1205-4c46-b16a-9cc0a2989551") ?: "c4c33472-1205-4c46-b16a-9cc0a2989551")
    val apiKey: StateFlow<String> = _apiKey.asStateFlow()

    private val _isDarkMode = MutableStateFlow<Boolean?>(
        if (prefs.contains("is_dark_mode")) prefs.getBoolean("is_dark_mode", false) else null
    )
    val isDarkMode: StateFlow<Boolean?> = _isDarkMode.asStateFlow()

    fun setDarkMode(enabled: Boolean?) {
        _isDarkMode.value = enabled
        if (enabled == null) {
            prefs.edit().remove("is_dark_mode").apply()
        } else {
            prefs.edit().putBoolean("is_dark_mode", enabled).apply()
        }
    }

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Home")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private val _selectedArticle = MutableStateFlow<NewsArticle?>(null)
    val selectedArticle: StateFlow<NewsArticle?> = _selectedArticle.asStateFlow()

    init {
        loadArticles()
    }

    fun setApiKey(key: String) {
        _apiKey.value = key
        prefs.edit().putString("api_key", key).apply()
        loadArticles()
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        loadArticles()
    }

    fun searchArticles(query: String) {
        _searchQuery.value = query
        loadArticles()
    }

    fun selectArticle(article: NewsArticle?) {
        _selectedArticle.value = article
    }

    fun loadArticles() {
        viewModelScope.launch {
            val hasExistingArticles = (_uiState.value as? NewsUiState.Success)?.articles?.isNotEmpty() == true
            if (hasExistingArticles) {
                _isRefreshing.value = true
            } else {
                _uiState.value = NewsUiState.Loading
            }
            try {
                val section = when (_selectedCategory.value) {
                    "Technology" -> "technology"
                    "World" -> "world"
                    "Business" -> "business"
                    else -> null
                }
                val query = _searchQuery.value.trim().ifEmpty { null }
                val response = repository.fetchArticles(
                    apiKey = _apiKey.value,
                    section = section,
                    query = query
                )
                val results = response.response.results
                _uiState.value = NewsUiState.Success(results)
            } catch (e: Exception) {
                if (!hasExistingArticles) {
                    _uiState.value = NewsUiState.Error(e.localizedMessage ?: "An error occurred while fetching news")
                }
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}
