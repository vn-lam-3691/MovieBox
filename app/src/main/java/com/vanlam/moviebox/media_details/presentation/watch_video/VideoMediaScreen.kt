package com.vanlam.moviebox.media_details.presentation.watch_video

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.vanlam.moviebox.ui.theme.MyMaterialTheme

@Composable
fun VideoMediaScreen(
    navController: NavHostController,
    lifecycleOwner: LifecycleOwner,
    videoId: String
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MyMaterialTheme.appColor.backgroundColor)
    ) {
        AndroidView(factory = { context ->

            YouTubePlayerView(context = context).apply {
                layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(
                            videoId, 0f
                        )
                    }
                })
            }

        })
    }
}
