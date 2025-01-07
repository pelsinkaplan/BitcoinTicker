package com.perpeer.bitcointicker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */
@Composable
fun FavouriteButton(
    isFavourite: Boolean,
    onFavouriteClick: () -> Unit
) {
    IconButton(onClick = onFavouriteClick) {
        if (isFavourite) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Remove from Favourites",
                tint = MaterialTheme.colorScheme.primary
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "Add to Favourites",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
