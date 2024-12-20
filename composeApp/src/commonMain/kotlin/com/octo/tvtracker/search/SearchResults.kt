package com.octo.tvtracker.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.octo.tvtracker.search.components.SearchResultTile

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SearchResults(
    state: SearchViewModel.UiState,
    navigateToShowDetail: (Int) -> Unit,
    bottomInset: Dp,
) {
  when (state) {
    SearchViewModel.UiState.Empty ->
        Text(
            text = "Empty",
        )

    SearchViewModel.UiState.Loading ->
        Box(
            modifier = Modifier.fillMaxSize().padding(48.dp),
            contentAlignment = Alignment.Center,
        ) {
          LoadingIndicator()
        }
    is SearchViewModel.UiState.Loaded -> {
      LazyVerticalGrid(
          modifier = Modifier.fillMaxSize(),
          columns = GridCells.Fixed(2),
          verticalArrangement = Arrangement.spacedBy(8.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          contentPadding =
              PaddingValues(
                  top = 8.dp,
                  start = 8.dp,
                  end = 8.dp,
                  bottom = bottomInset + 8.dp,
              ),
      ) {
        items(state.results, key = { it.id }) { result ->
          SearchResultTile(
              result,
              onClick = { navigateToShowDetail(result.id) },
          )
        }
      }
    }
  }
}
