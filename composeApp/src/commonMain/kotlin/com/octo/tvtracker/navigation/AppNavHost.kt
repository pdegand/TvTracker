package com.octo.tvtracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.octo.tvtracker.detail.TvShowDetailRoute
import com.octo.tvtracker.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable object Home

@Serializable data class TvShowDetail(val showId: Int)

@Composable
fun AppNavHost() {
  val navController = rememberNavController()

  NavHost(navController, startDestination = Home) {
    composable<Home> {
      HomeRoute(navigateToShowDetail = { showId -> navController.navigate(TvShowDetail(showId)) })
    }
    composable<TvShowDetail> { backStackEntry ->
      TvShowDetailRoute(navigateUp = { navController.navigateUp() })
    }
  }
}
