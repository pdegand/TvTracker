package com.octo.tvtracker.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.octo.tvtracker.search.SearchResults
import com.octo.tvtracker.search.SearchViewModel
import org.koin.compose.viewmodel.koinNavViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun HomeRoute(
  navigateToShowDetail: (Int) -> Unit,
  viewModel: SearchViewModel = koinNavViewModel(),
) {
  val focusManager = LocalFocusManager.current
  var searchQuery by rememberSaveable { mutableStateOf("") }
  var searchExpanded by rememberSaveable { mutableStateOf(false) }

  val searchPadding by animateDpAsState(if (searchExpanded) 0.dp else 8.dp)

  Scaffold { insets ->
    Column(modifier = Modifier.padding(horizontal = searchPadding)) {
      SearchBar(
          inputField = {
            SearchBarDefaults.InputField(
                modifier = Modifier.fillMaxWidth(),
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = {
                  viewModel.search(it)
                  focusManager.clearFocus(true)
                },
                expanded = searchExpanded,
                onExpandedChange = { searchExpanded = it },
                placeholder = { Text("Search for a TV show") },
                leadingIcon = {
                  if (searchExpanded) {
                    IconButton(onClick = { searchExpanded = false }) {
                      Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                  } else {
                    Icon(Icons.Default.Search, contentDescription = null)
                  }
                },
                trailingIcon = {
                  if (searchExpanded) {
                    IconButton(onClick = { searchQuery = "" }) {
                      Icon(Icons.Default.Clear, contentDescription = null)
                    }
                  }
                },
            )
          },
          expanded = searchExpanded,
          onExpandedChange = { searchExpanded = it },
      ) {
        SearchResults(
            state = viewModel.uiState.collectAsStateWithLifecycle().value,
            navigateToShowDetail = navigateToShowDetail,
            bottomInset = insets.calculateBottomPadding(),
        )
      }
      LazyColumn(
          modifier = Modifier.fillMaxWidth(),
      ) {
        items(50) { Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) { Text("Cell #$it") } }
      }
    }
  }
}
