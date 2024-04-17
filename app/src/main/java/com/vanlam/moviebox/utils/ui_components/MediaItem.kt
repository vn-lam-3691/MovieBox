package com.vanlam.moviebox.utils.ui_components

import android.widget.RatingBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.gson.Gson
import com.vanlam.moviebox.main.data.remote.MediaApi
import com.vanlam.moviebox.main.domain.model.Media
import com.vanlam.moviebox.ui.theme.MyMaterialTheme
import com.vanlam.moviebox.utils.Screen

@Composable
fun MediaItem(
    media: Media,
    navController: NavHostController
) {

    val imageUrl = "${MediaApi.BAST_IMAGE_URL}/${media.poster_path}"
    val posterImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )
    val posterState = posterImage.state

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = MyMaterialTheme.appColor.bottomNavColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("media", media)
                navController.navigate(Screen.DETAIL_SCREEN.route)
            }
    ) {
        Box(
            modifier = Modifier
                .height(240.dp)
                .fillMaxSize()
                .padding(6.dp)
        ) {
            if (posterState is AsyncImagePainter.State.Success) {
                val posterBitmap = posterState.result.drawable.toBitmap()
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(18.dp)),
                    bitmap = posterBitmap.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            } else if (posterState is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(0.5f)
                )
            } else if (posterState is AsyncImagePainter.State.Error) {
                Icon(
                    modifier = Modifier
                        .size(250.dp),
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = media.title,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (media.title.isNotEmpty()) {
            Text(
                text = media.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MyMaterialTheme.appColor.textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
        else {
            Text(
                text = media.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MyMaterialTheme.appColor.textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .padding(start = 10.dp, top = 4.dp, bottom = 12.dp)
        ) {
            RatingBar(
                starsModifier = Modifier.size(18.dp),
                rating = media.vote_average / 2
            )

            Text(
                text = media.vote_average.toString().take(3),
                fontSize = 12.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}