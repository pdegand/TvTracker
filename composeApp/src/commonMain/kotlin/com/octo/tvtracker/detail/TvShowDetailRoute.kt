package com.octo.tvtracker.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.octo.tvtracker.data.tmdb.Tmdb
import com.octo.tvtracker.navigation.TvShowDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinNavViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun TvShowDetailRoute(
  navigateUp: () -> Unit,
  viewModel: TvShowDetailViewModel = koinNavViewModel(),
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  TvShowDetailScreen(
      uiState = uiState,
      navigateUp = navigateUp,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowDetailScreen(
  uiState: TvShowDetailViewModel.UiState,
  navigateUp: () -> Unit,
) {
  Scaffold(
      topBar = {
        TopAppBar(
            title = {},
            navigationIcon = {
              IconButton(onClick = navigateUp) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
              }
            }
        )
      }) { insets ->
        Column(modifier = Modifier.padding(insets)) {
          if (uiState is TvShowDetailViewModel.UiState.Loaded) {
            Text(uiState.title)
          }
        }
      }
}

class TvShowDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val tmdb: Tmdb,
) : ViewModel() {

  private val showId = savedStateHandle.toRoute<TvShowDetail>().showId

  private val _uiState = MutableStateFlow<UiState>(UiState.Loaded(showId.toString()))
  val uiState = _uiState.asStateFlow()

  init {
    viewModelScope.launch {

    }
  }

  sealed interface UiState {
    data object Empty : UiState

    data object Loading : UiState

    data class Loaded(val title: String) : UiState
  }
}
