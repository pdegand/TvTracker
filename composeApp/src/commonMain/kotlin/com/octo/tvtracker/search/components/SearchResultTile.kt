package com.octo.tvtracker.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.octo.tvtracker.data.tmdb.TmdbSearchResult
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SearchResultTile(
  result: TmdbSearchResult,
  onClick: (Int) -> Unit,
  modifier: Modifier = Modifier,
  aspectRation: Float = 0.667f
) {
  Card(
      modifier = modifier.fillMaxWidth().aspectRatio(aspectRation),
      onClick = { onClick(result.id) },
      shape = RoundedCornerShape(8.dp),
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      CoilImage(
          modifier = Modifier.fillMaxSize(),
          imageModel = { "https://image.tmdb.org/t/p/w500${result.posterPath}" }, // loading a network image or local resource using an URL.
          imageOptions = ImageOptions(
              contentScale = ContentScale.Crop,
              alignment = Alignment.Center
          )
      )
      val scrimGradient =
          Brush.verticalGradient(
              listOf(
                  Color.Transparent,
                  Color.Black.copy(alpha = .8f),
              ),
          )
      Box(
          modifier =
              Modifier.align(Alignment.BottomCenter)
                  .fillMaxWidth()
                  .background(scrimGradient)
                  .padding(
                      top = 48.dp,
                      start = 8.dp,
                      end = 8.dp,
                      bottom = 8.dp,
                  ),
      ) {
        Text(
            modifier = Modifier.align(Alignment.BottomCenter),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            text = result.name,
        )
      }
    }
  }
}

@Composable
@Preview
fun SearchResultTilePreview() {
  val searchResult = TmdbSearchResult(
      id = 123,
      name = "Game Of Thrones",
      popularity = 1.0,
      posterPath = "/1XS1oqL89opfnbLl8WnZY1O1uJx.jpg",
      firstAirDate = "",
  )
  SearchResultTile(
      result = searchResult,
      onClick = {}
  )
}
