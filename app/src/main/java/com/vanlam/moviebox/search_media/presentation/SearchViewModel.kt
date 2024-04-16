package com.vanlam.moviebox.search_media.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanlam.moviebox.main.utils.Resource
import com.vanlam.moviebox.search_media.domain.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchState = MutableStateFlow(SearchUiState())
    val searchState = _searchState.asStateFlow()

    private var searchJob: Job? = null

    fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OnSearchQueryChanged -> {
                searchJob?.cancel()

                searchJob = viewModelScope.launch {
                    delay(500L)
                    _searchState.update {
                        it.copy(
                            searchQuery = event.query,
                            searchList = emptyList()
                        )
                    }

                    fetchSearchList()
                }
            }
            is SearchUiEvent.LoadMore -> {
                viewModelScope.launch {
                    _searchState.update {
                        it.copy(searchPage = searchState.value.searchPage + 1)
                    }

                    fetchSearchList()
                }
            }
        }
    }

    private fun fetchSearchList() {
        viewModelScope.launch {
            _searchState.update {
                it.copy(isLoading = true)
            }

            searchRepository.getSearchList(
                searchState.value.searchQuery,
                searchState.value.searchPage
            ).collect { result ->

                when (result) {
                    is Resource.Success -> {
                        result.data?.let { searchResult ->
                            _searchState.update {
                                it.copy(searchList = searchState.value.searchList + searchResult)
                            }
                        }
                    }
                    is Resource.Loading -> {
                        _searchState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Error -> Unit
                }

            }
        }
    }
}
