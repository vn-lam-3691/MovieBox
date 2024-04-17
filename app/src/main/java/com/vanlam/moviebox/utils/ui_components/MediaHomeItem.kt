package com.vanlam.moviebox.utils.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.gson.Gson
import com.vanlam.moviebox.main.data.remote.MediaApi
import com.vanlam.moviebox.main.domain.model.Media
import com.vanlam.moviebox.utils.Screen

@Composable
fun MediaHomeItem(
    media: Media,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val imageUrl = "${MediaApi.BAST_IMAGE_URL}/${media.poster_path}"
    val posterImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )
    val posterState = posterImage.state


    Box(
        modifier = Modifier
            .height(250.dp)
            .width(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("media", media)
                navController.navigate(Screen.DETAIL_SCREEN.route)
            }
            .background(color = MaterialTheme.colorScheme.onSurfaceVariant)
    ) {

        if (posterState is AsyncImagePainter.State.Success) {
            val posterBitmap = posterState.result.drawable.toBitmap()
            Image(
                bitmap = posterBitmap.asImageBitmap(),
                contentDescription = media.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        else if (posterState is AsyncImagePainter.State.Error) {
            Icon(
                modifier = Modifier
                    .size(height = 250.dp, width = 180.dp)
                    .align(Alignment.Center),
                imageVector = Icons.Rounded.ImageNotSupported,
                contentDescription = media.title,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        else if (posterState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .scale(0.5f)
            )
        }

    }
}
