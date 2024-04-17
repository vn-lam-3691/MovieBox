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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.vanlam.moviebox.search_media.presentation.SearchUiState
import com.vanlam.moviebox.ui.theme.MyMaterialTheme
import com.vanlam.moviebox.ui.theme.poppinsFontFamily
import com.vanlam.moviebox.utils.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchUiState: SearchUiState,
    onSearch: (String) -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }

    var text by rememberSaveable {
        mutableStateOf(searchUiState.searchQuery)
    }

    Box(
        modifier = Modifier
            .background(color = Color.Transparent)
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .height(50.dp)
    ) {

        TextField(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(18.dp))
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            leadingIcon = {
                Icon(
                    Icons.Rounded.Search,
                    "search",
                    tint = MyMaterialTheme.appColor.textColor,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(23.dp)
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    text = ""
                    onSearch(text)
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = MyMaterialTheme.appColor.textColor,
                        modifier = Modifier
                            .size(22.dp)
                    )
                }
            },
            placeholder = {
                Text(
                    color = MyMaterialTheme.appColor.tintTextBoxColor,
                    style = MaterialTheme.typography.bodyMedium,
                    text = "Search media"
                )
            },
            textStyle = TextStyle(
                color = MyMaterialTheme.appColor.tintTextBoxColor,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MyMaterialTheme.appColor.searchBoxColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

    }
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
                    navController.navigate(Screen.SEARCH_SCREEN.route)
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
                text = "Search media"
            )
        }

    }
}