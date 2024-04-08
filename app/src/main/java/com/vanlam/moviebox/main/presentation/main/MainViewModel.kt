package com.vanlam.moviebox.main.presentation.main

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanlam.moviebox.main.domain.repository.MediaRepository
import com.vanlam.moviebox.main.utils.Category
import com.vanlam.moviebox.main.utils.Resource
import com.vanlam.moviebox.utils.BottomBarScreen
import com.vanlam.moviebox.utils.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
): ViewModel() {

    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState = _mainUiState.asStateFlow()

    val showSplashScreenState = mutableStateOf(true)

    init {
        loadAllData()
        viewModelScope.launch {
            delay(1500)
            showSplashScreenState.value = false
        }
    }

    fun onEvent(mainUiEvent: MainUiEvent) {
        if (mainUiEvent is MainUiEvent.LoadMore) {
            if (mainUiEvent.category == Category.POPULAR) {
                fetchPopularMovieList(true)
            }
            else if (mainUiEvent.category == Category.TV_SHOW) {
                fetchTvShowList(true)
            }
        }
    }

    fun loadAllData() {
        fetchTrendingAllList()
        fetchPopularMovieList(false)
        fetchTvShowList(false)
        fetchTopRatedMovieList()
    }

    fun fetchTrendingAllList(
        time: String = "day"
    ) {
        viewModelScope.launch {
            _mainUiState.update {
                it.copy(isLoading = true)
            }

            mediaRepository.getTrendingMediaList(time, page = 1).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _mainUiState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let {trendingList ->
                            _mainUiState.update {
                                it.copy(trendingAllList = trendingList)
                            }
                        }
                    }
                    is Resource.Loading -> {
                        _mainUiState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }

    fun fetchTopRatedMovieList() {
        viewModelScope.launch {
            _mainUiState.update {
                it.copy(isLoading = true)
            }

            mediaRepository.getMovieList(
                Category.TOP_RATED,
                page = 1
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _mainUiState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let {  topRatedList ->
                            _mainUiState.update {
                                it.copy(topRatedMovieList = topRatedList)
                            }
                        }
                    }
                    is Resource.Error -> {
                        _mainUiState.update {
                            it.copy(isLoading = false)
                        }
                    }
                }
            }
        }
    }

    fun fetchPopularMovieList(loadMore: Boolean) {
        viewModelScope.launch {
            _mainUiState.update { it.copy(isLoading = true) }

            if (loadMore) {
                _mainUiState.update { it.copy(popularMoviePage = mainUiState.value.popularMoviePage + 1) }

                mediaRepository.getMovieList(
                    Category.POPULAR,
                    mainUiState.value.popularMoviePage
                ).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _mainUiState.update {
                                it.copy(isLoading = false)
                            }
                        }
                        is Resource.Success -> {
                            result.data?.let { popularMovies ->
                                _mainUiState.update {
                                    it.copy(
                                        popularMovieList = mainUiState.value.popularMovieList + popularMovies
                                    )
                                }
                            }
                        }
                        is Resource.Loading -> {
                            _mainUiState.update {
                                it.copy(isLoading = true)
                            }
                        }
                    }
                }
            }
            else {
                mediaRepository.getMovieList(
                    Category.POPULAR,
                    mainUiState.value.popularMoviePage
                ).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _mainUiState.update {
                                it.copy(isLoading = false)
                            }
                        }
                        is Resource.Success -> {
                            result.data?.let {  popularMovies ->
                                _mainUiState.update {
                                    it.copy(
                                        popularMovieList = popularMovies
                                    )
                                }
                            }
                        }
                        is Resource.Loading -> {
                            _mainUiState.update {
                                it.copy(isLoading = true)
                            }
                        }
                    }
                }
            }
        }
    }

    fun fetchTvShowList(loadMore: Boolean) {
        viewModelScope.launch {
            _mainUiState.update { it.copy(isLoading = true) }

            if (loadMore) {
                _mainUiState.update { it.copy(tvShowPage = mainUiState.value.tvShowPage + 1) }

                mediaRepository.getTvShowList(
                    mainUiState.value.tvShowPage
                ).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _mainUiState.update {
                                it.copy(isLoading = false)
                            }
                        }
                        is Resource.Success -> {
                            result.data?.let { tvShowList ->
                                _mainUiState.update {
                                    it.copy(
                                        discoverTvShowList = mainUiState.value.discoverTvShowList + tvShowList
                                    )
                                }
                            }
                        }
                        is Resource.Loading -> {
                            _mainUiState.update {
                                it.copy(isLoading = result.isLoading)
                            }
                        }
                    }
                }
            }
            else {
                mediaRepository.getTvShowList(
                    mainUiState.value.tvShowPage
                ).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _mainUiState.update {
                                it.copy(isLoading = false)
                            }
                        }
                        is Resource.Success -> {
                            result.data?.let { tvShowList ->
                                _mainUiState.update {
                                    it.copy(
                                        discoverTvShowList = tvShowList
                                    )
                                }
                            }
                        }
                        is Resource.Loading -> {
                            _mainUiState.update {
                                it.copy(isLoading = result.isLoading)
                            }
                        }
                    }
                }
            }
        }
    }
}
