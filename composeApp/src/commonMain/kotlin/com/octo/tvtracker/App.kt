package com.octo.tvtracker

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.octo.tvtracker.data.tmdb.tmdbModule
import com.octo.tvtracker.detail.tvShowDetailModule
import com.octo.tvtracker.navigation.AppNavHost
import com.octo.tvtracker.search.searchModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.compose.KoinApplication

@Composable
fun App() {
  val darkColorScheme = darkColorScheme(primary = Color(0xFF66ffc7))

  LaunchedEffect(Unit) {
    Napier.base(DebugAntilog())
  }

  KoinApplication(
      application = {
        modules(
            tmdbModule,
            searchModule,
            tvShowDetailModule,
        )
      }) {
        MaterialTheme(
            colorScheme = darkColorScheme,
        ) {
          AppNavHost()
        }
      }
}
