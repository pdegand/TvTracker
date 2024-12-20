package com.octo.tvtracker.data.tmdb

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class Tmdb(
    private val httpClient: HttpClient,
) {

  suspend fun search(query: String, page: Int = 1): TmdbPagedResult<TmdbSearchResult> =
      httpClient
          .get("search/tv") {
            url {
              parameters.append("query", query)
              parameters.append("page", page.toString())
            }
          }
          .body()

  suspend fun getTvShowDetail(showId: Int) {
    httpClient.get("tv") { url { appendPathSegments(showId.toString()) } }
  }
}

@Serializable
data class TmdbPagedResult<T>(
    @SerialName("results") val results: List<T>,
    @SerialName("page") val page: Int,
    @SerialName("total_pages") val totalPage: Int,
    @SerialName("total_results") val totalResults: Int,
)

@Serializable
data class TmdbSearchResult(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("popularity") val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("first_air_date") val firstAirDate: String,
)

@Serializable
data class TmdbTvShow(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("tagline") val tagline: String,
    @SerialName("overview") val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("first_air_date") val firstAirDate: String,
    @SerialName("number_of_seasons") val seasonCount: Int,
    @SerialName("number_of_episodes") val episodeCount: Int,

) {
  data class Season(
      @SerialName("id") val id: Int,
      @SerialName("name") val name: String,
      @SerialName("season_number") val seasonNumber: Int,
      @SerialName("episode_count") val episodeCount: Int,
      @SerialName("poster_path") val posterPath: String?,
  )
}
