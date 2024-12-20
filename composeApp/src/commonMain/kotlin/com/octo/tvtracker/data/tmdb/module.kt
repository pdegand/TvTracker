package com.octo.tvtracker.data.tmdb

import TvTracker.composeApp.BuildConfig
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val tmdbModule = module {
  single { tmdbHttpClient() }
  singleOf(::Tmdb)
}

private fun tmdbHttpClient() = HttpClient {
  install(ContentNegotiation) {
    json(
        Json {
          ignoreUnknownKeys = true
          explicitNulls = false
          prettyPrint = true
        },
    )
  }
  install(DefaultRequest) { url("https://api.themoviedb.org/3/") }
  install(Auth) {
    bearer {
      loadTokens {
        BearerTokens(
            BuildConfig.TMDB_API_KEY,
            null,
        )
      }
      sendWithoutRequest { true }
    }
  }
  install(Logging) {
    logger =
        object : Logger {
          override fun log(message: String) {
            Napier.d { message }
          }
        }
    level = LogLevel.BODY
  }
}
