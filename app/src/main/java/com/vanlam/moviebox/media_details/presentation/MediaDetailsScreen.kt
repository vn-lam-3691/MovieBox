package com.vanlam.moviebox.media_details.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.vanlam.moviebox.R
import com.vanlam.moviebox.main.data.remote.MediaApi
import com.vanlam.moviebox.main.domain.model.Media
import com.vanlam.moviebox.main.presentation.main.MainUiEvent
import com.vanlam.moviebox.ui.theme.MyMaterialTheme
import com.vanlam.moviebox.utils.ui_components.RatingBar

@Composable
fun DetailsScreen(
    navController: NavHostController,
    media: Media,
    detailsUiState: DetailsUiState,
    onDetailsEvent: (DetailScreenEvent) -> Unit,
    onMainEvent: (MainUiEvent) -> Unit
) {

    val widthDevice = LocalConfiguration.current.screenWidthDp

    val backdropImageUrl = "${MediaApi.BAST_IMAGE_URL}/${media.backdrop_path}"
    val posterImageUrl = "${MediaApi.BAST_IMAGE_URL}/${media.poster_path}"

    val backdropImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(backdropImageUrl)
            .size(Size.ORIGINAL)
            .build()
    ).state

    val posterImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(posterImageUrl)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(widthDevice.dp)
            .background(color = MyMaterialTheme.appColor.backgroundColor)
            .verticalScroll(rememberScrollState())
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            val (backdropImg, posterImg, mediaInfo, backImg, favoriteImg) = createRefs()

            BackdropMediaSection(backdropImageState, modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .constrainAs(backdropImg) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "back",
                tint = MyMaterialTheme.appColor.textColor,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        navController.popBackStack()
                        onMainEvent(MainUiEvent.RefreshWatchList)
                    }
                    .constrainAs(backImg) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 20.dp)
                    }
            )


            val painterIcon = if (detailsUiState.isSavedWatchList) {
                painterResource(id = R.drawable.ic_favorite_active)
            } else {
                painterResource(id = R.drawable.ic_favorite)
            }

            Icon(
                painter = painterIcon,
                contentDescription = "favorite",
                tint = MyMaterialTheme.appColor.textColor,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        onDetailsEvent(DetailScreenEvent.HandleToWatchList(media))
                    }
                    .constrainAs(favoriteImg) {
                        top.linkTo(parent.top, margin = 16.dp)
                        end.linkTo(parent.end, margin = 20.dp)
                    }
            )

            PosterMediaSection(
                posterImageState,
                modifier = Modifier.constrainAs(posterImg) {
                    top.linkTo(backdropImg.top, margin = 160.dp)
                    start.linkTo(backdropImg.start, margin = 16.dp)
                }
            )

            Column(
                modifier = Modifier
                    .width((widthDevice - 30 - 160).dp)
                    .constrainAs(mediaInfo) {
                        top.linkTo(backdropImg.bottom, margin = 14.dp)
                        end.linkTo(parent.end, margin = 18.dp)
                    }
            ) {
                Text(
                    text = if (media!!.title.isEmpty()) media.name else media.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MyMaterialTheme.appColor.textColor,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingBar(
                        starsModifier = Modifier.size(22.dp),
                        rating = media.vote_average / 2
                    )

                    Text(
                        text = media.vote_average.toString().take(3),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MyMaterialTheme.appColor.labelColor,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Language:  ${transLanguage(media.original_language)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MyMaterialTheme.appColor.labelColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Release date:  ${if (media.release_date.isNotEmpty()) media.release_date else media.first_air_date}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MyMaterialTheme.appColor.labelColor
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Overview",
            style = MaterialTheme.typography.bodyMedium,
            color = MyMaterialTheme.appColor.textColor,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 20.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = media!!.overview,
            style = MaterialTheme.typography.bodyMedium,
            color = MyMaterialTheme.appColor.labelColor,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Genres",
            style = MaterialTheme.typography.bodyMedium,
            color = MyMaterialTheme.appColor.textColor,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 20.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        val genresList = detailsUiState.genres

        LazyRow(
            modifier = Modifier
                .padding(start = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(end = 20.dp)
        ) {
            items(genresList) { title ->
                GenreItem(name = title)
            }
        }
    }
}

@Composable
fun PosterMediaSection(
    imageState: AsyncImagePainter.State,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(width = 140.dp, height = 220.dp),
        contentAlignment = Alignment.Center
    ) {
        if (imageState is AsyncImagePainter.State.Success) {
            val imageBitmap = imageState.result.drawable.toBitmap()

            Image(
                bitmap = imageBitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
            )
        } else if (imageState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = modifier
                    .size(60.dp)
            )
        } else if (imageState is AsyncImagePainter.State.Error) {
            Icon(
                imageVector = Icons.Rounded.ImageNotSupported,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .size(50.dp)
            )
        }
    }
}

@Composable
fun BackdropMediaSection(
    imageState: AsyncImagePainter.State,
    modifier: Modifier
) {

    Box(
        modifier = modifier
    ) {
        if (imageState is AsyncImagePainter.State.Success) {
            val imageBitmap = imageState.result.drawable.toBitmap()

            Image(
                bitmap = imageBitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
        } else if (imageState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
            )
        } else if (imageState is AsyncImagePainter.State.Error) {
            Icon(
                imageVector = Icons.Rounded.ImageNotSupported,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .size(100.dp)
            )
        }
    }
}

@Composable
fun GenreItem(name: String) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MyMaterialTheme.appColor.labelColor,
                shape = RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = MyMaterialTheme.appColor.labelColor,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}

fun transLanguage(lang: String) = when (lang) {
    "fr" -> "French"
    "ko" -> "Korean"
    "vi" -> "Vietnamese"
    "de" -> "German"
    "ja" -> "Japanese"

    else -> "English"
}
