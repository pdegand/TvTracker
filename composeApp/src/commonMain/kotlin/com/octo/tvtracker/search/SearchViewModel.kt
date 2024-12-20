package com.octo.tvtracker.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.octo.tvtracker.data.tmdb.TmdbSearchResult
import com.octo.tvtracker.data.tmdb.Tmdb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val tmdb: Tmdb,
) : ViewModel() {

  private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
  val uiState = _uiState.asStateFlow()

  fun search(query: String) {
    viewModelScope.launch {
      _uiState.value = UiState.Loading
      delay(1000)
      val result =
          tmdb
              .search(query)
              .results
              .sortedByDescending { it.popularity }
              .filter { it.posterPath != null }

      _uiState.value = UiState.Loaded(result)
    }
  }

  sealed interface UiState {
    data object Empty : UiState

    data object Loading : UiState

    data class Loaded(val results: List<TmdbSearchResult>) : UiState
  }
}
