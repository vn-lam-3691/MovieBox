package com.vanlam.moviebox.media_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanlam.moviebox.main.utils.Resource
import com.vanlam.moviebox.media_details.domain.model.Genre
import com.vanlam.moviebox.media_details.domain.repository.GenreRepository
import com.vanlam.moviebox.utils.Type
import com.vanlam.moviebox.watch_list.domain.repository.WatchListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val genreRepository: GenreRepository,
    private val watchListRepository: WatchListRepository
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailsUiState())
    val detailState: StateFlow<DetailsUiState> = _detailState.asStateFlow()

    fun onEvent(event: DetailScreenEvent) {
        if (event is DetailScreenEvent.SetDataAndLoad) {
            viewModelScope.launch {
                _detailState.update { it.copy(isLoading = true) }

                _detailState.update {
                    it.copy(
                        mediaItem = event.media,
                        typeMedia = if (event.media.title.isNotEmpty()) Type.MOVIE else Type.TV_SHOW,
                        isLoading = false
                    )
                }

                fetchMediaGenre(detailState.value.typeMedia)

                checkMediaInWatchList()
            }
        }
        else if (event is DetailScreenEvent.HandleToWatchList) {
            handleToWatchList()
            checkMediaInWatchList()
        }
    }

    private fun handleToWatchList() {
        viewModelScope.launch {
            _detailState.update {
                it.copy(isSaving = true)
            }

            detailState.value.mediaItem?.let {

                if (detailState.value.isSavedWatchList) {   // Handle insert to watch list when media not in database
                    watchListRepository.deleteMedia(it).collectLatest { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _detailState.update {
                                    it.copy(isSaving = result.isLoading)
                                }
                            }
                            is Resource.Success -> {
                                result.data?.let { mediaId ->
                                    _detailState.update {
                                        if (mediaId == -1) {
                                            it.copy(isSavedWatchList = true)
                                        }
                                        else {
                                            it.copy(isSavedWatchList = false)
                                        }
                                    }
                                }
                            }
                            is Resource.Error -> {
                                _detailState.update {
                                    it.copy(isSaving = false)
                                }
                            }
                        }
                    }
                }
                else {  // Handle insert to watch list when media not in database
                    watchListRepository.insertMedia(it).collectLatest { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _detailState.update {
                                    it.copy(isSaving = result.isLoading)
                                }
                            }
                            is Resource.Success -> {
                                result.data?.let { isSaved ->
                                    _detailState.update {
                                        it.copy(isSavedWatchList = isSaved)
                                    }
                                }
                            }
                            is Resource.Error -> {
                                _detailState.update {
                                    it.copy(isSaving = false)
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    private fun checkMediaInWatchList() {
        viewModelScope.launch {
            _detailState.update {
                it.copy(isChecking = true)
            }

            detailState.value.mediaItem?.id?.let {
                watchListRepository.getMediaById(it).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _detailState.update {
                                it.copy(isChecking = false)
                            }
                        }
                        is Resource.Success -> {
                            result.data?.let { saved ->
                                _detailState.update {
                                    it.copy(isSavedWatchList = saved)
                                }
                            }
                        }
                        is Resource.Loading -> {
                            _detailState.update {
                                it.copy(isChecking = result.isLoading)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun fetchMediaGenre(type: String) {
        viewModelScope.launch {
            _detailState.update { it.copy(isLoading = true) }

            genreRepository.getAllGenres(type).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _detailState.update {
                            it.copy(isLoading = detailState.value.isLoading)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { listGenre ->
                            _detailState.update {
                                it.copy(genres = mapGenreId(listGenre))
                            }
                        }
                    }
                    is Resource.Error -> {
                        _detailState.update {
                            it.copy(isLoading = false, genres = emptyList())
                        }
                    }
                }
            }
        }
    }

    private fun mapGenreId(genres: List<Genre>): List<String> {
        val result = ArrayList<String>()
        for (i in detailState.value.mediaItem?.genre_ids!!) {
            for (j in genres) {
                if (i == j.id) {
                    result.add(j.name)
                }
            }
        }
        return result
    }
}
