package com.vanlam.moviebox.utils.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.vanlam.moviebox.ui.theme.MyMaterialTheme

@Composable
fun SearchBar() {

}

@Composable
fun SearchBarNonFocus(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .background(color = Color.Transparent)
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .height(50.dp)
    ) {

        Row(
            modifier = Modifier
                .height(50.dp)
                .clickable {
                    // Navigate to Search Screen
                }
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(MyMaterialTheme.appColor.searchBoxColor)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.Search,
                null,
                tint = MyMaterialTheme.appColor.textColor,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(23.dp)
            )

            Text(
                modifier = Modifier
                    .padding(start = 14.dp),
                color = MyMaterialTheme.appColor.tintTextBoxColor,
                style = MaterialTheme.typography.bodyMedium,
                text = "Search movie"
            )
        }

    }
}